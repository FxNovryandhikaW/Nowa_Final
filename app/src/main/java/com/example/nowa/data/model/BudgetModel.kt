package com.example.nowa.data.model

data class BudgetModel(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val limitAmount: Long = 0,
    val spentAmount: Long = 0,
    val emoji: String = "💰",
    val colorHex: String = "#5C6BC0"
)
