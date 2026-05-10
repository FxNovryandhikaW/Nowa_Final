package com.example.nowa.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nowa.component.MenuIcon
import com.example.nowa.component.SummaryCard
import com.example.nowa.component.TransactionItem
import com.example.nowa.component.recentTransactions
import com.example.nowa.ui.theme.*

@Composable
fun BerandaRiwayatScreen(navController: NavHostController) {
    val totalPemasukan = recentTransactions.filter { it.amount.startsWith("+") }
        .sumOf { it.amount.replace("+Rp", "").replace(".", "").toLongOrNull() ?: 0L }
    
    val totalPengeluaran = recentTransactions.filter { it.amount.startsWith("-") }
        .sumOf { it.amount.replace("-Rp", "").replace(".", "").toLongOrNull() ?: 0L }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkBlue)
                .padding(24.dp)
                .padding(top = 24.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Halo,", color = White.copy(alpha = 0.7f), fontSize = 14.sp)
                        Text("KitaPastiBisa 👋", color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                    Row {
                        IconButton(onClick = { navController.navigate("profile") }, modifier = Modifier.background(White.copy(alpha = 0.1f), CircleShape)) {
                            Icon(Icons.Default.Person, contentDescription = "Profile", tint = White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { navController.navigate("notifications") }, modifier = Modifier.background(White.copy(alpha = 0.1f), CircleShape)) {
                            Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = NowaSecondary)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Bolt, contentDescription = null, tint = NowaSecondary, modifier = Modifier.size(16.dp))
                            Text(" FINANCIAL HEALTH SCORE", color = White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("72", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = White)
                                Surface(
                                    color = NowaSecondary,
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Text("Cukup Sehat", modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = DarkBlue)
                                }
                            }
                            Canvas(modifier = Modifier.size(100.dp, 60.dp)) {
                                val path = Path().apply {
                                    moveTo(0f, size.height * 0.8f)
                                    lineTo(size.width * 0.2f, size.height * 0.6f)
                                    lineTo(size.width * 0.4f, size.height * 0.7f)
                                    lineTo(size.width * 0.6f, size.height * 0.4f)
                                    lineTo(size.width * 0.8f, size.height * 0.5f)
                                    lineTo(size.width, 0f)
                                }
                                drawPath(path, color = NowaSecondary, style = Stroke(width = 4f))
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        LinearProgressIndicator(
                            progress = { 0.72f },
                            modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                            color = NowaSecondary,
                            trackColor = White.copy(alpha = 0.2f)
                        )
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SummaryCard("PEMASUKAN", "Rp${String.format("%,d", totalPemasukan).replace(",", ".")}", Icons.Default.Favorite, GreenIncome, Modifier.weight(1f)) {}
                    SummaryCard("PENGELUARAN", "Rp${String.format("%,d", totalPengeluaran).replace(",", ".")}", Icons.Default.Favorite, RedExpense, Modifier.weight(1f)) {}
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    MenuIcon("Akun", Icons.Default.AccountBalance, Color(0xFFE8EAF6)) { navController.navigate("accounts") }
                    MenuIcon("Riwayat", Icons.Default.Assignment, Color(0xFFFFF3E0)) { navController.navigate("history") }
                    MenuIcon("Goals", Icons.Default.TrackChanges, Color(0xFFFCE4EC)) { navController.navigate("goals") }
                    MenuIcon("Profil", Icons.Default.Person, Color(0xFFE0F2F1)) { navController.navigate("profile") }
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("💡 Saran Keuangan", fontWeight = FontWeight.Bold, color = DarkBlue)
                        }
                        Text(
                            "Pengeluaran makan bulan ini melebihi 30% pendapatan. Batasi hingga Rp500.000 untuk menjaga skor kamu.",
                            fontSize = 12.sp,
                            color = TextGray
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Transaksi Terkini", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("Lihat semua", color = DarkBlue, fontSize = 12.sp, modifier = Modifier.clickable { navController.navigate("history") })
                }
            }
            items(recentTransactions) { transaction ->
                TransactionItem(transaction)
            }
        }
    }
}
