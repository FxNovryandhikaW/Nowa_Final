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

import androidx.compose.foundation.lazy.LazyRow
import com.example.nowa.component.FilterChip

@Composable
fun RiwayatScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NowaPrimaryDark)
    ) {
        Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 60.dp, bottom = 24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Riwayat Transaksi", color = White, fontSize = 28.sp, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.width(12.dp))
                Text("📊", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Semua catatan pemasukan & pengeluaran", color = White.copy(alpha = 0.7f), fontSize = 14.sp)
            
            Spacer(modifier = Modifier.height(24.dp))
            LazyRow {
                item { FilterChip(true, "Semua") }
                item { FilterChip(false, "Pemasukan") }
                item { FilterChip(false, "Pengeluaran") }
                item { FilterChip(false, "Makanan") }
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = NowaBackground,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                item { SectionHeader("HARI INI · 23 APRIL 2026") }
                items(recentTransactions.take(1)) { transaction ->
                    TransactionItem(transaction)
                }
                
                item { SectionHeader("KEMARIN · 22 APRIL") }
                items(recentTransactions.subList(1, 4)) { transaction ->
                    TransactionItem(transaction)
                }
                
                item { SectionHeader("21 APRIL 2026") }
                items(recentTransactions.subList(4, 6)) { transaction ->
                    TransactionItem(transaction)
                }
            }
        }
    }
}
