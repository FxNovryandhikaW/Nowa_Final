package com.example.nowa.data.model

data class AccountModel(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val type: String = "",
    val balance: Long = 0,
    val emoji: String = "",
    val accountNumber: String = "",
    val phoneNumber: String = ""
)
