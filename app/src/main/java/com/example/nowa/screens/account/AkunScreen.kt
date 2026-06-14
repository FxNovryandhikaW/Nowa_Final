package com.example.nowa.screens.account

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.util.Locale
import com.example.nowa.*
import com.example.nowa.data.*
import com.example.nowa.data.model.AccountModel
import com.example.nowa.data.repository.AccountRepository
import com.example.nowa.ui.theme.*

@Composable
fun AkunScreen(navController: NavHostController) {
    val repository = remember { AccountRepository() }
    var accounts by remember { mutableStateOf<List<AccountModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val result = repository.getAccounts()
        if (result.isSuccess) {
            accounts = result.getOrDefault(emptyList())
        }
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NowaPrimaryDark)
            .statusBarsPadding()
    ) {
        Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 20.dp, bottom = 24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.background(White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Daftar Akun", color = White, fontSize = 28.sp, fontWeight = FontWeight.Black)
            }
            Spacer(modifier = Modifier.height(16.dp))
            val totalBalance = accounts.sumOf { it.balance }
            val formattedTotal = "Rp${String.format(Locale("id", "ID"), "%,d", totalBalance).replace(',', '.')}"
            
            Text("Total saldo: $formattedTotal", color = White.copy(alpha = 0.7f), fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = NowaBackground,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = NowaPrimary)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(accounts) { account ->
                        val formattedBalance = "Rp${String.format(Locale("id", "ID"), "%,d", account.balance).replace(',', '.')}"
                        AccountListItem(account.name, account.type, formattedBalance, account.emoji, White) {
                            val encodedName = android.net.Uri.encode(account.name)
                            navController.navigate("account_detail?accountName=$encodedName")
                        }
                    }
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .border(2.dp, NowaPrimary.copy(alpha = 0.2f), RoundedCornerShape(24.dp))
                                .background(NowaPrimary.copy(alpha = 0.05f), RoundedCornerShape(24.dp))
                                .clickable { navController.navigate("add_account") },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(NowaPrimary.copy(alpha = 0.15f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = null, tint = NowaPrimary, modifier = Modifier.size(20.dp))
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Text("Tambah Akun Baru", color = NowaPrimary, fontWeight = FontWeight.Black, fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AccountListItem(name: String, type: String, balance: String, emoji: String, bgColor: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = White),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(48.dp).background(BackgroundGray, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Text(emoji, fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(name, fontWeight = FontWeight.Bold, color = TextBlack)
                Text(type, fontSize = 10.sp, color = TextGray)
            }
            Text(balance, fontWeight = FontWeight.ExtraBold, color = DarkBlue, fontSize = 16.sp)
        }
    }
}
