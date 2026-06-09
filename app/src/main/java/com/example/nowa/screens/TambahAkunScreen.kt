package com.example.nowa.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.nowa.ui.theme.NowaLightBlue

import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import com.example.nowa.data.*
import com.example.nowa.data.model.AccountModel
import com.example.nowa.data.repository.AccountRepository
import com.example.nowa.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun TambahAkunScreen(navController: NavHostController) {
    var accountName by remember { mutableStateOf("") }
    var accountType by remember { mutableStateOf("Cash") }
    var initialBalance by remember { mutableStateOf("") }
    var bankNumber by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val repository = remember { AccountRepository() }
    val context = LocalContext.current

    val accountTypes = listOf("Cash", "Bank", "E-Wallet", "Kartu Kredit")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding()
    ) {
        Column(modifier = Modifier.padding(24.dp).padding(top = 16.dp)) {
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
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
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

                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        if (accountName.isNotEmpty()) {
                            isLoading = true
                            val emoji = when(accountType) {
                                "Bank" -> "🏦"
                                "E-Wallet" -> "💳"
                                "Kartu Kredit" -> "💳"
                                else -> "💵"
                            }
                            
                            val account = AccountModel(
                                name = accountName,
                                type = accountType,
                                balance = initialBalance.toLongOrNull() ?: 0L,
                                emoji = emoji,
                                accountNumber = bankNumber,
                                phoneNumber = phoneNumber
                            )

                            scope.launch {
                                val result = repository.addAccount(account)
                                if (result.isSuccess) {
                                    Toast.makeText(context, "Akun berhasil ditambah!", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                } else {
                                    val errorMsg = result.exceptionOrNull()?.message ?: "Gagal menambah akun"
                                    Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                                }
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NowaPrimary),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = White, modifier = Modifier.size(24.dp))
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("💾 ", fontSize = 16.sp)
                            Text("Tambah Akun", fontWeight = FontWeight.Bold)
                        }
                    }
                }
                TextButton(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
                    Text("Batal", color = TextGray)
                }
            }
        }
    }
}
