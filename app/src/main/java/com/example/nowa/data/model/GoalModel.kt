package com.example.nowa.data.model

data class GoalModel(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val targetAmount: Long = 0,
    val savedAmount: Long = 0,
    val targetDate: String = "",
    val emoji: String = "🎯"
)
