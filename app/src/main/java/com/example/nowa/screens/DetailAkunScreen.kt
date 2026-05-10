package com.example.kelompok_nokonteks_tam_nowa.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kelompok_nokonteks_tam_nowa.component.Transaction
import com.example.kelompok_nokonteks_tam_nowa.component.TransactionItem
import com.example.kelompok_nokonteks_tam_nowa.ui.theme.*

@Composable
fun DetailAkunScreen(navController: NavHostController) {
    val cashAccount = globalAccounts.find { it.type == "Cash" }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        Column(modifier = Modifier.padding(24.dp).padding(top = 24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.background(White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = White)
                }
                Text("Detail Akun", color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = { navController.navigate("edit_account") }, modifier = Modifier.background(NowaSecondary, RoundedCornerShape(12.dp))) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = DarkBlue)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(56.dp).background(White.copy(alpha = 0.1f), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
                        Text(cashAccount?.emoji ?: "💵", fontSize = 32.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(cashAccount?.name ?: "Kas / Tunai", color = White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text("${cashAccount?.type ?: "Cash"} · IDR", color = White.copy(alpha = 0.7f), fontSize = 12.sp)
                    }
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Saldo Hari Ini", color = White.copy(alpha = 0.7f), fontSize = 12.sp)
                    Text(cashAccount?.balance ?: "Rp0", color = NowaSecondary, fontSize = 36.sp, fontWeight = FontWeight.ExtraBold)
                    Text("▼ -18% dari 30 hari lalu", color = RedExpense, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = BackgroundGray,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                item {
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = White), shape = RoundedCornerShape(24.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("📈 TREN SALDO 30 HARI", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                            Spacer(modifier = Modifier.height(16.dp))
                            Canvas(modifier = Modifier.fillMaxWidth().height(100.dp)) {
                                val path = Path().apply {
                                    moveTo(0f, 20f)
                                    lineTo(size.width * 0.5f, 40f)
                                    lineTo(size.width, 80f)
                                }
                                drawPath(path, color = DarkBlue, style = Stroke(width = 4f))
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("24M", fontSize = 10.sp, color = TextGray)
                                Text("6A", fontSize = 10.sp, color = TextGray)
                                Text("Hari ini", fontSize = 10.sp, color = TextGray)
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(24.dp)) }
                item { Text("Riwayat Akun Ini", fontWeight = FontWeight.Bold, fontSize = 18.sp) }
                item { Spacer(modifier = Modifier.height(12.dp)) }
                item { TransactionItem(Transaction("Transfer Keluar", "${cashAccount?.name ?: "Cash"} → Luar Wallet", "-Rp200.000", RedExpense, "↗️", "Kemarin")) }
                item { TransactionItem(Transaction("Free time", cashAccount?.name ?: "Cash", "+Rp80.000", GreenIncome, "😊", "Kemarin")) }
                item { TransactionItem(Transaction("Makan Siang", "Pengeluaran · Makanan", "-Rp45.000", RedExpense, "🍔", "21 Apr")) }
            }
        }
    }
}
