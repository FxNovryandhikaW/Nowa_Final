package com.example.nowa.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nowa.ui.theme.*

@Composable
fun EditAkunScreen(navController: NavHostController) {
    val currentAccount = remember { globalAccounts.find { it.type == "Cash" } }
    
    var accountName by remember { mutableStateOf(currentAccount?.name ?: "Kas / Tunai") }
    var accountType by remember { mutableStateOf(currentAccount?.type ?: "Cash") }
    var balance by remember { mutableStateOf((currentAccount?.balance ?: "550000").replace("Rp", "").replace(".", "")) }
    var bankNumber by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    val accountTypes = listOf("Cash", "Bank", "E-Wallet", "Kartu Kredit")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        Column(modifier = Modifier.padding(24.dp).padding(top = 24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.background(White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = White)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Edit Akun", color = White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
                IconButton(
                    onClick = {
                        globalAccounts.removeAll { it.type == "Cash" }
                        navController.popBackStack()
                    },
                    modifier = Modifier.background(RedExpense.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = RedExpense)
                }
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = White,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp).fillMaxWidth()) {
                Text("NAMA AKUN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                OutlinedTextField(
                    value = accountName,
                    onValueChange = { accountName = it },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NowaPrimary,
                        unfocusedBorderColor = NowaLightBlue.copy(alpha = 0.5f)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("JENIS AKUN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    accountTypes.forEach { type ->
                        val isSelected = accountType == type
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) NowaSecondary else NowaBackground)
                                .clickable { accountType = type }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = type,
                                color = if (isSelected) DarkBlue else NowaPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                if (accountType == "Bank" || accountType == "Kartu Kredit") {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("NOMOR REKENING / KARTU", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                    OutlinedTextField(
                        value = bankNumber,
                        onValueChange = { bankNumber = it },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NowaPrimary,
                            unfocusedBorderColor = NowaLightBlue.copy(alpha = 0.5f)
                        )
                    )
                }

                if (accountType == "E-Wallet") {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("NOMOR HP", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NowaPrimary,
                            unfocusedBorderColor = NowaLightBlue.copy(alpha = 0.5f)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("SALDO (IDR)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                OutlinedTextField(
                    value = balance,
                    onValueChange = { balance = it },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NowaPrimary,
                        unfocusedBorderColor = NowaLightBlue.copy(alpha = 0.5f)
                    )
                )

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        val index = globalAccounts.indexOfFirst { it.type == "Cash" }
                        if (index != -1) {
                            val emoji = when (accountType) {
                                "Bank" -> "🏦"
                                "E-Wallet" -> "💳"
                                "Kartu Kredit" -> "💳"
                                else -> "💵"
                            }
                            globalAccounts[index] = AccountData(
                                name = accountName,
                                type = accountType,
                                balance = "Rp$balance",
                                emoji = emoji
                            )
                        }
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NowaPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Simpan Perubahan", fontWeight = FontWeight.Bold)
                    }
                }
                TextButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Batal", color = TextGray)
                }
            }
        }
    }
}
