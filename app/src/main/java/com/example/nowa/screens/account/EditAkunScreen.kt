package com.example.nowa.screens.account

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nowa.data.model.AccountModel
import com.example.nowa.data.repository.AccountRepository
import com.example.nowa.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun EditAkunScreen(navController: NavHostController, initialAccountName: String) {
    val repository = remember { AccountRepository() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    
    var account by remember { mutableStateOf<AccountModel?>(null) }
    var accountName by remember { mutableStateOf("") }
    var accountType by remember { mutableStateOf("Cash") }
    var balance by remember { mutableStateOf("") }
    var bankNumber by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val result = repository.getAccounts()
        if (result.isSuccess) {
            val foundAccount = result.getOrNull()?.find { it.name == initialAccountName }
            if (foundAccount != null) {
                account = foundAccount
                accountName = foundAccount.name
                accountType = foundAccount.type
                balance = foundAccount.balance.toString()
                bankNumber = foundAccount.accountNumber
                phoneNumber = foundAccount.phoneNumber
            }
        }
        isLoading = false
    }

    val accountTypes = listOf("Cash", "Bank", "E-Wallet", "Kartu Kredit")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding()
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
                
                if (account != null) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                val res = repository.deleteAccount(account!!.id)
                                if (res.isSuccess) {
                                    Toast.makeText(context, "Akun dihapus", Toast.LENGTH_SHORT).show()
                                    navController.navigate("accounts") {
                                        popUpTo("accounts") { inclusive = true }
                                    }
                                }
                            }
                        },
                        modifier = Modifier.background(RedExpense.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = RedExpense)
                    }
                }
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = White,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
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
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NowaPrimary,
                            unfocusedBorderColor = NowaBackground,
                            focusedTextColor = TextBlack,
                            unfocusedTextColor = TextBlack
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
                                unfocusedBorderColor = NowaBackground,
                                focusedTextColor = TextBlack,
                                unfocusedTextColor = TextBlack
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
                                unfocusedBorderColor = NowaBackground,
                                focusedTextColor = TextBlack,
                                unfocusedTextColor = TextBlack
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
                            unfocusedBorderColor = NowaBackground,
                            focusedTextColor = TextBlack,
                            unfocusedTextColor = TextBlack
                        )
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            if (account != null) {
                                val emoji = when (accountType) {
                                    "Bank" -> "🏦"
                                    "E-Wallet" -> "💳"
                                    "Kartu Kredit" -> "💳"
                                    else -> "💵"
                                }
                                val updatedAccount = account!!.copy(
                                    name = accountName,
                                    type = accountType,
                                    balance = balance.toLongOrNull() ?: 0L,
                                    emoji = emoji,
                                    accountNumber = bankNumber,
                                    phoneNumber = phoneNumber
                                )
                                
                                scope.launch {
                                    val res = repository.updateAccount(updatedAccount)
                                    if (res.isSuccess) {
                                        Toast.makeText(context, "Perubahan disimpan", Toast.LENGTH_SHORT).show()
                                        navController.popBackStack()
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NowaPrimary),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("💾 ", fontSize = 16.sp)
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
}
