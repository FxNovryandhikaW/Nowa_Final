package com.example.nowa.data.repository

import com.example.nowa.data.model.TransactionModel
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
            val docRef = firestore.collection("users").document(uid)
                .collection("transactions").document()
            
            val newTransaction = transaction.copy(id = docRef.id, userId = uid)
            docRef.set(newTransaction).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
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
