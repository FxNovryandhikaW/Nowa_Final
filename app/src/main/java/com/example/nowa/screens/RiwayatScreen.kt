package com.example.nowa.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.nowa.component.SectionHeader
import com.example.nowa.component.TransactionItem
import com.example.nowa.component.recentTransactions
import androidx.navigation.NavHostController
import com.example.nowa.ui.theme.*

@Composable
fun RiwayatScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        Column(modifier = Modifier.padding(24.dp).padding(top = 24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Riwayat Transaksi", color = White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.BarChart, contentDescription = null, tint = White, modifier = Modifier.size(32.dp))
            }
            Text("Semua catatan pemasukan & pengeluaran", color = White.copy(alpha = 0.7f), fontSize = 14.sp)
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = BackgroundGray,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                item { SectionHeader("SEMUA TRANSAKSI") }
                items(recentTransactions) { transaction ->
                    TransactionItem(transaction)
                }
            }
        }
    }
}
