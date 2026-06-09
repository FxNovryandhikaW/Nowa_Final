package com.example.nowa.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import com.example.nowa.ui.theme.*

data class AccountData(
    val name: String,
    val type: String,
    val balance: String,
    val emoji: String,
    val detail: String = ""
)

data class GoalData(
    val name: String,
    val targetAmount: String,
    val savedAmount: String,
    val remainingAmount: String,
    val progress: Float,
    val percentage: String,
    val emoji: String
)

data class BudgetData(
    val name: String,
    val spentAmount: String,
    val totalAmount: String,
    val progress: Float,
    val usageText: String,
    val remainingText: String,
    val color: Color,
    val emoji: String
)

val globalAccounts = mutableStateListOf<AccountData>()

val globalGoals = mutableStateListOf<GoalData>()

val globalBudgets = mutableStateListOf<BudgetData>()
