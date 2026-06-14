package com.example.nowa.screens.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nowa.component.TransactionItem
import com.example.nowa.component.Transaction as TransactionUI
import com.example.nowa.data.model.AccountModel
import com.example.nowa.data.model.TransactionModel
import com.example.nowa.data.model.TransactionType
import com.example.nowa.data.repository.AccountRepository
import com.example.nowa.data.repository.TransactionRepository
import com.example.nowa.ui.theme.*
import java.util.Locale

@Composable
fun DetailAkunScreen(navController: NavHostController, accountName: String) {
    val accountRepo = remember { AccountRepository() }
    val transRepo = remember { TransactionRepository() }
    
    var account by remember { mutableStateOf<AccountModel?>(null) }
    var transactions by remember { mutableStateOf<List<TransactionModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val aResult = accountRepo.getAccounts()
        if (aResult.isSuccess) {
            account = aResult.getOrNull()?.find { it.name == accountName }
        }
        
        val tResult = transRepo.getTransactions()
        if (tResult.isSuccess) {
            // Filter transactions for this specific account
            transactions = tResult.getOrDefault(emptyList()).filter { it.accountName == accountName }
        }
        isLoading = false
    }

    fun formatRp(amount: Long): String {
        return "Rp${String.format(Locale("id", "ID"), "%,d", amount).replace(',', '.')}"
    }

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
            
            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = NowaSecondary)
                }
            } else {
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
                            Text(account?.name ?: "Akun Tidak Ditemukan", color = White, fontSize = 26.sp, fontWeight = FontWeight.Black)
                            Text("${account?.type ?: "-"} · IDR", color = White.copy(alpha = 0.7f), fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                        Text("Saldo Hari Ini", color = White.copy(alpha = 0.7f), fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        Text(formatRp(account?.balance ?: 0L), color = NowaSecondary, fontSize = 42.sp, fontWeight = FontWeight.Black)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val trendText = if (transactions.isEmpty()) "Belum ada aktivitas" else "Terintegrasi dengan Cloud"
                            Text(trendText, color = Color(0xFFB2FF59), fontSize = 12.sp, fontWeight = FontWeight.Black)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
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
            } else {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                            colors = CardDefaults.cardColors(containerColor = White),
                            shape = RoundedCornerShape(24.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("📈 TREN SALDO", fontSize = 11.sp, fontWeight = FontWeight.Black, color = TextGray, letterSpacing = 1.sp)
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                                Canvas(modifier = Modifier.fillMaxWidth().height(100.dp)) {
                                    val path = Path().apply {
                                        moveTo(0f, size.height * 0.5f)
                                        cubicTo(size.width * 0.4f, size.height * 0.2f, size.width * 0.7f, size.height * 0.8f, size.width, size.height * 0.3f)
                                    }
                                    drawPath(path, color = NowaPrimary, style = Stroke(width = 6f, cap = StrokeCap.Round))
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("Awal", fontSize = 10.sp, color = TextGray, fontWeight = FontWeight.Bold)
                                    Text("Akhir", fontSize = 10.sp, color = TextGray, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                    item { 
                        Spacer(modifier = Modifier.height(28.dp))
                        Text("Riwayat Akun Ini", fontWeight = FontWeight.Black, fontSize = 20.sp, color = NowaPrimaryDark)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    
                    if (transactions.isEmpty()) {
                        item {
                            Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                                Text("Tidak ada riwayat untuk akun ini", color = TextGray)
                            }
                        }
                    } else {
                        items(transactions) { trans ->
                            val uiTrans = TransactionUI(
                                name = trans.note,
                                category = trans.category,
                                amount = "${if (trans.type == TransactionType.INCOME) "+" else "-"}Rp${String.format(Locale("id", "ID"), "%,d", trans.amount).replace(',', '.')}",
                                color = if (trans.type == TransactionType.INCOME) GreenIncome else RedExpense,
                                emoji = if (trans.type == TransactionType.INCOME) "💰" else "🛒"
                            )
                            TransactionItem(uiTrans)
                        }
                    }
                }
            }
        }
    }
}
