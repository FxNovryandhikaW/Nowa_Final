package com.example.nowa.util

import com.example.nowa.data.model.TransactionModel
import com.example.nowa.data.model.TransactionType

object FinancialHealthScorer {

    fun calculateScore(transactions: List<TransactionModel>): Int {
        if (transactions.isEmpty()) return 0

        var totalIncome = 0L
        var totalExpense = 0L

        transactions.forEach {
            if (it.type == TransactionType.INCOME) {
                totalIncome += it.amount
            } else {
                totalExpense += it.amount
            }
        }

        if (totalIncome == 0L) return 0

        // 1. Savings Rate (40 points)
        // Target: Save 20% or more
        val savings = totalIncome - totalExpense
        val savingsRate = if (savings > 0) (savings.toFloat() / totalIncome) else 0f
        val savingsScore = (savingsRate * 5 * 40).coerceAtMost(40f)

        // 2. Expense Control (40 points)
        // Target: Expenses < 80% of income
        val expenseRatio = totalExpense.toFloat() / totalIncome
        val controlScore = if (expenseRatio <= 0.5f) 40f 
                          else if (expenseRatio >= 1.0f) 0f
                          else (1.0f - expenseRatio) * 2 * 40f
        
        // 3. Activity (20 points)
        // Target: At least 5 transactions
        val activityScore = (transactions.size * 4).coerceAtMost(20)

        return (savingsScore + controlScore + activityScore).toInt().coerceIn(0, 100)
    }

    fun getStatus(score: Int): String {
        return when {
            score >= 80 -> "Sangat Sehat"
            score >= 60 -> "Sehat"
            score >= 40 -> "Cukup"
            else -> "Perlu Perhatian"
        }
    }
}
