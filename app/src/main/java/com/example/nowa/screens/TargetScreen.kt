package com.example.nowa.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.graphics.toColorInt
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nowa.component.BudgetCard
import com.example.nowa.component.GoalCard
import com.example.nowa.data.model.BudgetModel
import com.example.nowa.data.model.GoalModel
import com.example.nowa.data.repository.BudgetRepository
import com.example.nowa.data.repository.GoalRepository
import com.example.nowa.ui.theme.*
import java.util.Locale

@Composable
fun TargetScreen(navController: NavHostController) {
    val goalRepo = remember { GoalRepository() }
    val budgetRepo = remember { BudgetRepository() }
    
    var goals by remember { mutableStateOf<List<GoalModel>>(emptyList()) }
    var budgets by remember { mutableStateOf<List<BudgetModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val gResult = goalRepo.getGoals()
        val bResult = budgetRepo.getBudgets()
        
        if (gResult.isSuccess) goals = gResult.getOrDefault(emptyList())
        if (bResult.isSuccess) budgets = bResult.getOrDefault(emptyList())
        
        isLoading = false
    }

    // Helper to format currency
    fun formatRp(amount: Long): String {
        return "Rp${String.format(Locale("id", "ID"), "%,d", amount).replace(',', '.')}"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NowaPrimaryDark)
    ) {
        Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 60.dp, bottom = 24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Goals & Budget", color = White, fontSize = 28.sp, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.width(12.dp))
                Text("🎯", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Rencanakan masa depan finansialmu", color = White.copy(alpha = 0.7f), fontSize = 14.sp)
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
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Target Tabungan", fontWeight = FontWeight.Black, fontSize = 18.sp, color = NowaPrimaryDark)
                            Text(
                                "+ Tambah",
                                color = NowaPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable { navController.navigate("add_goal") }
                            )
                        }
                    }
                    items(goals) { goal ->
                        val progress = if (goal.targetAmount > 0) (goal.savedAmount.toFloat() / goal.targetAmount).coerceAtMost(1f) else 0f
                        val percent = "${(progress * 100).toInt()}%"
                        Box(modifier = Modifier.clickable { navController.navigate("edit_goal/${goal.id}") }) {
                            GoalCard(
                                goal.name, 
                                formatRp(goal.targetAmount), 
                                formatRp(goal.savedAmount), 
                                if (progress >= 1f) "🎯 Tercapai!" else goal.targetDate, 
                                progress, 
                                percent, 
                                goal.emoji
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Budget Bulanan", fontWeight = FontWeight.Black, fontSize = 18.sp, color = NowaPrimaryDark)
                            Text(
                                "+ Tambah",
                                color = NowaPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable { navController.navigate("add_budget") }
                            )
                        }
                    }
                    items(budgets) { budget ->
                        val progress = if (budget.limitAmount > 0) (budget.spentAmount.toFloat() / budget.limitAmount).coerceAtMost(1f) else 0f
                        val usageText = if (progress >= 1f) "⚠️ Limit Tercapai" else "${(progress * 100).toInt()}% terpakai"
                        val remaining = budget.limitAmount - budget.spentAmount
                        Box(modifier = Modifier.clickable { navController.navigate("edit_budget/${budget.id}") }) {
                            BudgetCard(
                                budget.name, 
                                formatRp(budget.spentAmount), 
                                formatRp(budget.limitAmount), 
                                progress, 
                                usageText, 
                                if (remaining > 0) "Sisa ${formatRp(remaining)}" else "Over Budget!",
                                Color(budget.colorHex.toColorInt()),
                                budget.emoji
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
