package com.example.nowa.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nowa.data.*
import com.example.nowa.ui.theme.*

@Composable
fun TambahAkunScreen(navController: NavHostController) {
    var accountName by remember { mutableStateOf("") }
    var accountType by remember { mutableStateOf("Cash") }
    var initialBalance by remember { mutableStateOf("") }
    var bankNumber by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    val accountTypes = listOf("Cash", "Bank", "E-Wallet", "Kartu Kredit")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        Column(modifier = Modifier.padding(24.dp).padding(top = 24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.background(White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Tambah Akun Baru", color = White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
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
                    placeholder = { Text("mis. BCA, GoPay, Kas...", color = TextGray.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NowaPrimary,
                        unfocusedBorderColor = NowaLightBlue.copy(alpha = 0.5f)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("JENIS AKUN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
                            Text(type, color = if (isSelected) DarkBlue else NowaPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                if (accountType == "Bank" || accountType == "Kartu Kredit") {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("NOMOR REKENING / KARTU", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                    OutlinedTextField(
                        value = bankNumber,
                        onValueChange = { bankNumber = it },
                        placeholder = { Text("Masukkan nomor...", color = TextGray.copy(alpha = 0.5f)) },
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
                        placeholder = { Text("08xx...", color = TextGray.copy(alpha = 0.5f)) },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NowaPrimary,
                            unfocusedBorderColor = NowaLightBlue.copy(alpha = 0.5f)
                    )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("SALDO AWAL (IDR)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                OutlinedTextField(
                    value = initialBalance,
                    onValueChange = { initialBalance = it },
                    placeholder = { Text("0", color = TextGray.copy(alpha = 0.5f)) },
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
                        if (accountName.isNotEmpty()) {
                            val emoji = when(accountType) {
                                "Bank" -> "🏦"
                                "E-Wallet" -> "💳"
                                "Kartu Kredit" -> "💳"
                                else -> "💵"
                            }
                            globalAccounts.add(AccountData(accountName, accountType, "Rp$initialBalance", emoji))
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NowaPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("💾 ", fontSize = 16.sp)
                        Text("Tambah Akun", fontWeight = FontWeight.Bold)
                    }
                }
                TextButton(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
                    Text("Batal", color = TextGray)
                }
            }
        }
    }
}
