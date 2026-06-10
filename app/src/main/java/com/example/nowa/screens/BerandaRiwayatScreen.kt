package com.example.nowa.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.nowa.data.model.TransactionModel
import com.example.nowa.data.model.TransactionType
import com.example.nowa.data.repository.AccountRepository
import com.example.nowa.data.repository.GoalRepository
import com.example.nowa.data.repository.TransactionRepository
import com.example.nowa.util.FinancialHealthScorer
import com.example.nowa.component.Transaction as TransactionUI
import com.example.nowa.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

@Composable
fun BerandaRiwayatScreen(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val repository = remember { TransactionRepository() }
    val accountRepo = remember { AccountRepository() }
    val goalRepo = remember { GoalRepository() }
    
    var userName by remember { mutableStateOf("User") }
    var transactions by remember { mutableStateOf<List<TransactionModel>>(emptyList()) }
    var accountCount by remember { mutableIntStateOf(0) }
    var goalCount by remember { mutableIntStateOf(0) }
    var isLoading by remember { mutableStateOf(value = true) }

    // Fetch all data
    LaunchedEffect(Unit) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            // Get Name
            firestore.collection("users").document(uid).get()
                .addOnSuccessListener { doc ->
                    userName = doc.getString("nama") ?: "User"
                }
            
            // Get Transactions
            val result = repository.getTransactions()
            if (result.isSuccess) {
                transactions = result.getOrDefault(emptyList())
            }

            // Get Account count
            val accountResult = accountRepo.getAccounts()
            if (accountResult.isSuccess) {
                accountCount = accountResult.getOrDefault(emptyList()).size
            }

            // Get Goal count
            val goalResult = goalRepo.getGoals()
            if (goalResult.isSuccess) {
                goalCount = goalResult.getOrDefault(emptyList()).size
            }
        }
        isLoading = false
    }

    val totalIncome = transactions.asSequence().filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    val totalExpense = transactions.asSequence().filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
    
    val healthScore = FinancialHealthScorer.calculateScore(transactions)
    val healthStatus = FinancialHealthScorer.getStatus(healthScore)

    // Helper to format currency
    fun formatRp(amount: Long): String {
        return "Rp${String.format(Locale("id", "ID"), "%,d", amount).replace(',', '.')}"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NowaBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(NowaPrimaryDark, NowaPrimary)
                    )
                )
                .padding(start = 24.dp, end = 24.dp, top = 60.dp, bottom = 32.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Halo,", color = White.copy(alpha = 0.7f), fontSize = 14.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(userName, color = White, fontSize = 24.sp, fontWeight = FontWeight.Black)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("👋", fontSize = 20.sp)
                        }
                    }
                    Row {
                        IconButton(
                            onClick = { navController.navigate("profile") },
                            modifier = Modifier
                                .size(40.dp)
                                .background(White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                        ) {
                            Icon(Icons.Default.Person, contentDescription = "Profile", tint = White, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        IconButton(
                            onClick = { navController.navigate("notifications") },
                            modifier = Modifier
                                .size(40.dp)
                                .background(White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                        ) {
                            Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = NowaSecondary, modifier = Modifier.size(20.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.12f)),
                    shape = RoundedCornerShape(28.dp),
                    border = BorderStroke(1.dp, White.copy(alpha = 0.1f))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Bolt, contentDescription = null, tint = NowaSecondary, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("FINANCIAL HEALTH SCORE", color = White.copy(alpha = 0.9f), fontSize = 11.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(healthScore.toString(), fontSize = 56.sp, fontWeight = FontWeight.Black, color = White)
                                Surface(
                                    color = NowaSecondary,
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Text(
                                        healthStatus,
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Black,
                                        color = NowaPrimaryDark
                                    )
                                }
                            }
                            Canvas(modifier = Modifier.size(120.dp, 70.dp)) {
                                val path = Path().apply {
                                    moveTo(0f, size.height * (1f - (healthScore / 100f)))
                                    lineTo(size.width * 0.2f, size.height * 0.75f)
                                    lineTo(size.width * 0.4f, size.height * 0.8f)
                                    lineTo(size.width * 0.6f, size.height * 0.5f)
                                    lineTo(size.width * 0.8f, size.height * 0.6f)
                                    lineTo(size.width, size.height * (1f - (healthScore / 100f)))
                                }
                                drawPath(path, color = NowaSecondary, style = Stroke(width = 5f, cap = StrokeCap.Round, join = StrokeJoin.Round))
                                drawCircle(color = NowaSecondary, radius = 6f, center = Offset(size.width, size.height * (1f - (healthScore / 100f))))
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        LinearProgressIndicator(
                            progress = { healthScore / 100f },
                            modifier = Modifier.fillMaxWidth().height(10.dp).clip(CircleShape),
                            color = NowaSecondary,
                            trackColor = White.copy(alpha = 0.15f)
                        )
                    }
                }
            }
        }

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = NowaPrimary)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SummaryCard("PEMASUKAN", formatRp(totalIncome), Icons.Default.Favorite, GreenIncome, Modifier.weight(1f)) {
                            navController.navigate("add_transaction")
                        }
                        SummaryCard("PENGELUARAN", formatRp(totalExpense), Icons.Default.Favorite, RedExpense, Modifier.weight(1f)) {
                            navController.navigate("add_transaction")
                        }
                        SummaryCard("AKUN", "$accountCount Akun", Icons.Default.AccountBalance, NowaPrimary, Modifier.weight(1f)) {
                            navController.navigate("accounts")
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp, end = 24.dp, bottom = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Button(
                            onClick = { navController.navigate("add_transaction") },
                            modifier = Modifier.weight(1f).height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = GreenIncome.copy(alpha = 0.15f)),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, GreenIncome.copy(alpha = 0.3f))
                        ) {
                            Text("🍏 +Pemasukan", color = GreenIncome, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Button(
                            onClick = { navController.navigate("add_transaction") },
                            modifier = Modifier.weight(1f).height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = RedExpense.copy(alpha = 0.15f)),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, RedExpense.copy(alpha = 0.3f))
                        ) {
                            Text("❤️ +Pengeluaran", color = RedExpense, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MenuIcon("Akun", Icons.Default.AccountBalance, Color.White) { navController.navigate("accounts") }
                        MenuIcon("Riwayat", Icons.AutoMirrored.Filled.Assignment, Color.White) { navController.navigate("history") }
                        MenuIcon("Goals", Icons.Default.TrackChanges, Color.White) { navController.navigate("goals") }
                        MenuIcon("Profil", Icons.Default.Person, Color.White) { navController.navigate("profile") }
                    }
                }
                item { Spacer(modifier = Modifier.height(24.dp)) }
                item {
                    Card(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = NowaSecondary.copy(alpha = 0.3f)),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, NowaSecondary.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("💡 Saran Keuangan", fontWeight = FontWeight.Black, color = NowaPrimaryDark, fontSize = 14.sp)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            val advice = when {
                                healthScore >= 80 -> "Luar biasa! Pertahankan gaya hidup hematmu dan teruslah berinvestasi."
                                healthScore >= 60 -> "Keuanganmu sehat. Coba alokasikan sedikit lebih banyak untuk dana darurat."
                                else -> "Waspada! Pengeluaranmu hampir atau sudah melebihi pemasukan. Kurangi belanja non-esensial."
                            }
                            Text(
                                advice,
                                fontSize = 12.sp,
                                color = NowaPrimaryDark.copy(alpha = 0.8f),
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(28.dp)) }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Transaksi Terkini", fontWeight = FontWeight.Black, fontSize = 18.sp, color = NowaPrimaryDark)
                        Text(
                            "Lihat semua",
                            color = NowaPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { navController.navigate("history") }
                        )
                    }
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }
                
                if (transactions.isEmpty()) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                            Text("Belum ada transaksi", color = TextGray)
                        }
                    }
                } else {
                    items(transactions.take(5)) { transaction ->
                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            val uiTransaction = TransactionUI(
                                name = transaction.note,
                                category = transaction.category,
                                amount = "${if (transaction.type == TransactionType.INCOME) "+" else "-"}Rp${String.format("%,d", transaction.amount).replace(',', '.')}",
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
