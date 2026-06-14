package com.example.nowa.data.repository

import com.example.nowa.data.model.BudgetModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class BudgetRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val userId get() = auth.currentUser?.uid

    suspend fun addBudget(budget: BudgetModel): Result<Unit> {
        return try {
            val uid = userId ?: return Result.failure(Exception("User not logged in"))
            val docRef = firestore.collection("users").document(uid)
                .collection("budgets").document()
            
            val newBudget = budget.copy(id = docRef.id, userId = uid)
            docRef.set(newBudget).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getBudgets(): Result<List<BudgetModel>> {
        return try {
            val uid = userId ?: return Result.failure(Exception("User not logged in"))
            val snapshot = firestore.collection("users").document(uid)
                .collection("budgets")
                .get()
                .await()
            
            val budgets = snapshot.toObjects(BudgetModel::class.java)
            Result.success(budgets)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateBudget(budget: BudgetModel): Result<Unit> {
        return try {
            val uid = userId ?: return Result.failure(Exception("User not logged in"))
            firestore.collection("users").document(uid)
                .collection("budgets").document(budget.id)
                .set(budget).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteBudget(budgetId: String): Result<Unit> {
        return try {
            val uid = userId ?: return Result.failure(Exception("User not logged in"))
            firestore.collection("users").document(uid)
                .collection("budgets").document(budgetId)
                .delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
