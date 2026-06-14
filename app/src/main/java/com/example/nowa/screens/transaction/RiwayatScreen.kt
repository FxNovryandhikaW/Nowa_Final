package com.example.nowa.screens.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nowa.component.FilterChip
import com.example.nowa.component.SectionHeader
import com.example.nowa.component.TransactionItem
import com.example.nowa.component.Transaction as TransactionUI
import com.example.nowa.data.model.TransactionModel
import com.example.nowa.data.model.TransactionType
import com.example.nowa.data.repository.TransactionRepository
import com.example.nowa.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RiwayatScreen(@Suppress("UNUSED_PARAMETER") navController: NavHostController) {
    val repository = remember { TransactionRepository() }
    var transactions by remember { mutableStateOf<List<TransactionModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(value = true) }
    var selectedFilter by remember { mutableStateOf("Semua") }

    LaunchedEffect(Unit) {
        val result = repository.getTransactions()
        if (result.isSuccess) {
            transactions = result.getOrDefault(emptyList()).sortedByDescending { it.date }
        }
        isLoading = false
    }

    val filteredTransactions = when (selectedFilter) {
        "Pemasukan" -> transactions.filter { it.type == TransactionType.INCOME }
        "Pengeluaran" -> transactions.filter { it.type == TransactionType.EXPENSE }
        else -> transactions
    }

    // Grouping by date safely
    val groupedTransactions = filteredTransactions.groupBy { 
        try {
            val sdf = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
            sdf.format(it.date.toDate())
        } catch (e: Exception) {
            @Suppress("UNUSED_VARIABLE") val dummy = e
            "Tanggal Tidak Diketahui"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NowaPrimaryDark),
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
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val filters = listOf("Semua", "Pemasukan", "Pengeluaran")
                items(filters) { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        text = filter,
                        onClick = { selectedFilter = filter },
                    )
                }
            }
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
            } else if (filteredTransactions.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Tidak ada transaksi", color = TextGray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    groupedTransactions.forEach { (date, transactionsInDate) ->
                        item { SectionHeader(date.uppercase()) }
                        items(transactionsInDate) { transaction ->
                            val formattedAmount = try {
                                String.format(Locale("id", "ID"), "%,d", transaction.amount).replace(',', '.')
                            } catch (e: Exception) {
                                transaction.amount.toString()
                            }
                            
                            val uiTransaction = TransactionUI(
                                name = transaction.note,
                                category = transaction.category,
                                amount = "${if (transaction.type == TransactionType.INCOME) "+" else "-"}Rp$formattedAmount",
                                color = if (transaction.type == TransactionType.INCOME) GreenIncome else RedExpense,
                                emoji = if (transaction.type == TransactionType.INCOME) "💰" else "🛒"
                            )
                            TransactionItem(uiTransaction)
                        }
                    }
                }
            }
        }
    }
}
