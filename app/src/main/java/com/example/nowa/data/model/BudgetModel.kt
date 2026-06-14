package com.example.nowa.data.model

data class BudgetModel(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val limitAmount: Long = 0,
    val spentAmount: Long = 0,
    val lastResetMonth: Int = -1, // -1 means never reset
    val emoji: String = "💰",
    val colorHex: String = "#5C6BC0"
)
