package com.example.nowa.data.repository

import com.example.nowa.data.model.AccountModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AccountRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val userId get() = auth.currentUser?.uid

    suspend fun addAccount(account: AccountModel): Result<Unit> {
        return try {
            val uid = userId ?: return Result.failure(Exception("User not logged in"))
            val docRef = firestore.collection("users").document(uid)
                .collection("accounts").document()
            
            val newAccount = account.copy(id = docRef.id, userId = uid)
            docRef.set(newAccount).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateAccount(account: AccountModel): Result<Unit> {
        return try {
            val uid = userId ?: return Result.failure(Exception("User not logged in"))
            firestore.collection("users").document(uid)
                .collection("accounts").document(account.id)
                .set(account).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteAccount(accountId: String): Result<Unit> {
        return try {
            val uid = userId ?: return Result.failure(Exception("User not logged in"))
            firestore.collection("users").document(uid)
                .collection("accounts").document(accountId)
                .delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAccounts(): Result<List<AccountModel>> {
        return try {
            val uid = userId ?: return Result.failure(Exception("User not logged in"))
            val snapshot = firestore.collection("users").document(uid)
                .collection("accounts")
                .get()
                .await()
            
            val accounts = snapshot.toObjects(AccountModel::class.java)
            Result.success(accounts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
