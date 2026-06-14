package com.example.nowa.data.model

import com.google.firebase.Timestamp

enum class TransactionType {
    INCOME, EXPENSE
}

data class TransactionModel(
    val id: String = "",
    val userId: String = "",
    val accountId: String = "",
    val accountName: String = "",
    val amount: Long = 0,
    val category: String = "",
    val note: String = "",
    val type: TransactionType = TransactionType.EXPENSE,
    val date: Timestamp = Timestamp.now(),
    val goalId: String? = null,
    val goalAmount: Long = 0
)
