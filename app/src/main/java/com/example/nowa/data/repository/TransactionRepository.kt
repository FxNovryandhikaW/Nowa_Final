package com.example.nowa.data.repository

import com.example.nowa.data.model.TransactionModel
import com.example.nowa.data.model.TransactionType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class TransactionRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val userId get() = auth.currentUser?.uid

    suspend fun addTransaction(transaction: TransactionModel): Result<Unit> {
        return try {
            val uid = userId ?: return Result.failure(Exception("User not logged in"))
            
            firestore.runTransaction { firestoreTransaction ->
                // --- 1. UPDATE SALDO AKUN ---
                val accountRef = firestore.collection("users").document(uid)
                    .collection("accounts").document(transaction.accountId)
                
                val accountSnapshot = firestoreTransaction.get(accountRef)
                if (!accountSnapshot.exists()) throw Exception("Akun tidak ditemukan")
                
                val currentBalance = accountSnapshot.getLong("balance") ?: 0L
                val newBalance = if (transaction.type == TransactionType.INCOME) {
                    currentBalance + transaction.amount
                } else {
                    currentBalance - transaction.amount
                }
                firestoreTransaction.update(accountRef, "balance", newBalance)

                // --- 2. UPDATE BUDGET (Jika Pengeluaran) ---
                if (transaction.type == TransactionType.EXPENSE) {
                    val budgetQuery = firestore.collection("users").document(uid)
                        .collection("budgets").whereEqualTo("name", transaction.category).get()
                    
                    // Karena kita di dalam runTransaction, kita harus menggunakan pemanggilan yang sinkron atau cara lain.
                    // Namun karena get() di atas asinkron, kita akan melakukan trik increment Firestore.
                }
                
                // --- 3. SIMPAN TRANSAKSI ---
                val transRef = firestore.collection("users").document(uid)
                    .collection("transactions").document()
                val finalTransaction = transaction.copy(id = transRef.id, userId = uid)
                firestoreTransaction.set(transRef, finalTransaction)
            }.await()

            // --- LOGIKA PENYEMPURNAAN DI LUAR TRANSACTION BLOCK (Untuk Budget & Goal) ---
            // Menggunakan Firestore Increment agar lebih efisien dan tidak bentrok
            if (transaction.type == TransactionType.EXPENSE) {
                updateBudgetAccumulation(uid, transaction.category, transaction.amount)
            } else {
                // Alokasi Goal dari Pemasukan (Jika ada)
                if (transaction.goalId != null && transaction.goalAmount > 0) {
                    firestore.collection("users").document(uid)
                        .collection("goals").document(transaction.goalId)
                        .update("savedAmount", com.google.firebase.firestore.FieldValue.increment(transaction.goalAmount))
                        .await()
                }
                // Tetap cek akumulasi goal berdasarkan kategori (jika diperlukan)
                updateGoalAccumulation(uid, transaction.category, transaction.amount)
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun updateBudgetAccumulation(uid: String, category: String, amount: Long) {
        val budgets = firestore.collection("users").document(uid)
            .collection("budgets").whereEqualTo("name", category).get().await()
        
        for (doc in budgets.documents) {
            firestore.collection("users").document(uid)
                .collection("budgets").document(doc.id)
                .update("spentAmount", com.google.firebase.firestore.FieldValue.increment(amount))
        }
    }

    private suspend fun updateGoalAccumulation(uid: String, category: String, amount: Long) {
        val goals = firestore.collection("users").document(uid)
            .collection("goals").whereEqualTo("name", category).get().await()
        
        for (doc in goals.documents) {
            firestore.collection("users").document(uid)
                .collection("goals").document(doc.id)
                .update("savedAmount", com.google.firebase.firestore.FieldValue.increment(amount))
        }
    }

    suspend fun getTransactions(): Result<List<TransactionModel>> {
        return try {
            val uid = userId ?: return Result.failure(Exception("User not logged in"))
            val snapshot = firestore.collection("users").document(uid)
                .collection("transactions")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()
            
            val transactions = snapshot.toObjects(TransactionModel::class.java)
            Result.success(transactions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
