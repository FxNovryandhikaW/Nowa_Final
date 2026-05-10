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

val globalAccounts = mutableStateListOf(
    AccountData("Kas / Tunai", "Cash", "Rp550.000", "💵"),
    AccountData("BRI Tabungan", "Bank Account", "Rp2.900.000", "🏦"),
    AccountData("GoPay", "E-Wallet", "Rp200.000", "💳")
)

val globalGoals = mutableStateListOf(
    GoalData("Dana Darurat", "Rp6.000.000", "Rp3.480.000", "Rp2.520.000", 0.58f, "58%", "🏠"),
    GoalData("Liburan Bali", "Rp2.000.000", "Rp660.000", "Des 2026", 0.33f, "33%", "✈️")
)

val globalBudgets = mutableStateListOf(
    BudgetData("Makanan & Minum", "Rp420.000", "Rp600.000", 0.7f, "70% terpakai", "Sisa Rp180.000", NowaPrimary, "🍔"),
    BudgetData("Transportasi", "Rp120.000", "Rp300.000", 0.4f, "40% terpakai", "Sisa Rp180.000", GreenIncome, "🚌"),
    BudgetData("Hiburan", "Rp195.000", "Rp150.000", 1.0f, "⚠️ Melebihi batas!", "+Rp45.000", RedExpense, "🎮")
)
