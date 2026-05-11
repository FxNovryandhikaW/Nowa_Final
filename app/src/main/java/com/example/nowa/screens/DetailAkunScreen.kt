package com.example.nowa.screens

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
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nowa.component.Transaction
import com.example.nowa.component.TransactionItem
import com.example.nowa.data.*
import com.example.nowa.ui.theme.*

@Composable
fun DetailAkunScreen(navController: NavHostController, accountName: String) {
    val account = globalAccounts.find { it.name == accountName }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NowaPrimaryDark)
            .statusBarsPadding()
    ) {
        Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 20.dp, bottom = 24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.background(White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = White)
                }
                Text("Detail Akun", color = White, fontSize = 24.sp, fontWeight = FontWeight.Black)
                IconButton(
                    onClick = { 
                        val encodedName = android.net.Uri.encode(account?.name ?: "")
                        navController.navigate("edit_account?accountName=$encodedName")
                    },
                    modifier = Modifier.background(NowaSecondary, RoundedCornerShape(12.dp))
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = NowaPrimaryDark)
                }
            }
            Spacer(modifier = Modifier.height(28.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.12f)),
                shape = RoundedCornerShape(28.dp),
                border = BorderStroke(1.dp, White.copy(alpha = 0.1f))
            ) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(64.dp).background(White.copy(alpha = 0.15f), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
                        Text(account?.emoji ?: "💵", fontSize = 36.sp)
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Column {
                        Text(account?.name ?: "Kas / Tunai", color = White, fontSize = 26.sp, fontWeight = FontWeight.Black)
                        Text("${account?.type ?: "Cash"} · IDR", color = White.copy(alpha = 0.7f), fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                }
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                    Text("Saldo Hari Ini", color = White.copy(alpha = 0.7f), fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    Text(account?.balance ?: "Rp0", color = NowaSecondary, fontSize = 42.sp, fontWeight = FontWeight.Black)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("▼ -18% dari 30 hari lalu", color = Color(0xFFFF8A80), fontSize = 12.sp, fontWeight = FontWeight.Black)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = NowaBackground,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = White),
                        shape = RoundedCornerShape(24.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("📈 TREN SALDO 30 HARI", fontSize = 11.sp, fontWeight = FontWeight.Black, color = TextGray, letterSpacing = 1.sp)
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Canvas(modifier = Modifier.fillMaxWidth().height(120.dp)) {
                                val path = Path().apply {
                                    moveTo(0f, 20f)
                                    cubicTo(size.width * 0.3f, 20f, size.width * 0.6f, 60f, size.width, size.height * 0.8f)
                                }
                                drawPath(path, color = NowaPrimary, style = Stroke(width = 6f, cap = StrokeCap.Round))
                                
                                // Gradient below the line
                                val fillPath = Path().apply {
                                    addPath(path)
                                    lineTo(size.width, size.height)
                                    lineTo(0f, size.height)
                                    close()
                                }
                                drawPath(
                                    fillPath,
                                    brush = Brush.verticalGradient(
                                        colors = listOf(NowaPrimary.copy(alpha = 0.15f), Color.Transparent)
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("24M", fontSize = 10.sp, color = TextGray, fontWeight = FontWeight.Bold)
                                Text("6A", fontSize = 10.sp, color = TextGray, fontWeight = FontWeight.Bold)
                                Text("Hari ini", fontSize = 10.sp, color = TextGray, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(28.dp)) }
                item { Text("Riwayat Akun Ini", fontWeight = FontWeight.Black, fontSize = 20.sp, color = NowaPrimaryDark) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item { TransactionItem(Transaction("Transfer Keluar", "${account?.name ?: "Cash"} → Luar Wallet", "-Rp200.000", RedExpense, "↗️", "Kemarin")) }
                item { TransactionItem(Transaction("Free time", account?.name ?: "Cash", "+Rp80.000", GreenIncome, "😊", "Kemarin")) }
                item { TransactionItem(Transaction("Makan Siang", "Pengeluaran · Makanan", "-Rp45.000", RedExpense, "🍔", "21 Apr")) }
            }
        }
    }
}
