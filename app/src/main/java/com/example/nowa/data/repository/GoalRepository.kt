package com.example.nowa.data.repository

import com.example.nowa.data.model.GoalModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class GoalRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val userId get() = auth.currentUser?.uid

    suspend fun addGoal(goal: GoalModel): Result<Unit> {
        return try {
            val uid = userId ?: return Result.failure(Exception("User not logged in"))
            val docRef = firestore.collection("users").document(uid)
                .collection("goals").document()
            
            val newGoal = goal.copy(id = docRef.id, userId = uid)
            docRef.set(newGoal).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getGoals(): Result<List<GoalModel>> {
        return try {
            val uid = userId ?: return Result.failure(Exception("User not logged in"))
            val snapshot = firestore.collection("users").document(uid)
                .collection("goals")
                .get()
                .await()
            
            val goals = snapshot.toObjects(GoalModel::class.java)
            Result.success(goals)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
