package com.example.nowa.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nowa.component.BudgetCard
import com.example.nowa.component.GoalCard
import com.example.nowa.ui.theme.*

data class GoalData(
    val name: String,
    val targetAmount: String,
    val savedAmount: String,
    val remainingAmount: String,
    val progress: Float,
    val percentage: String,
    val emoji: String
)

data class BudgetData(
    val name: String,
    val spentAmount: String,
    val totalAmount: String,
    val progress: Float,
    val usageText: String,
    val remainingText: String,
    val color: androidx.compose.ui.graphics.Color,
    val emoji: String
)

val globalGoals = mutableStateListOf(
    GoalData("Dana Darurat", "Rp6.000.000", "Rp3.480.000", "Rp2.520.000", 0.58f, "58%", "🏠"),
    GoalData("Liburan Bali", "Rp2.000.000", "Rp660.000", "Des 2026", 0.33f, "33%", "✈️")
)

val globalBudgets = mutableStateListOf(
    BudgetData("Makanan & Minum", "Rp420.000", "Rp600.000", 0.7f, "70% terpakai", "Sisa Rp180.000", DarkBlue, "🍔"),
    BudgetData("Transportasi", "Rp120.000", "Rp300.000", 0.4f, "40% terpakai", "Sisa Rp180.000", GreenIncome, "🚌"),
    BudgetData("Hiburan", "Rp195.000", "Rp150.000", 1.0f, "⚠️ Melebihi batas!", "+Rp45.000", RedExpense, "🎮")
)

@Composable
fun TargetScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        Column(modifier = Modifier.padding(24.dp).padding(top = 24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Goals & Budget", color = White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.TrackChanges, contentDescription = null, tint = Pink40, modifier = Modifier.size(32.dp))
            }
            Text("Rencanakan masa depan finansialmu", color = White.copy(alpha = 0.7f), fontSize = 14.sp)
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = BackgroundGray,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                item {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Target Tabungan", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(
                            "+ Tambah",
                            color = DarkBlue,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { navController.navigate("add_goal") }
                        )
                    }
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }
                items(globalGoals) { goal ->
                    GoalCard(goal.name, goal.targetAmount, goal.savedAmount, goal.remainingAmount, goal.progress, goal.percentage, goal.emoji)
                    Spacer(modifier = Modifier.height(12.dp))
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }
                item {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("Budget Bulanan", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(
                            "+ Tambah",
                            color = DarkBlue,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { navController.navigate("add_budget") }
                        )
                    }
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }
                items(globalBudgets) { budget ->
                    BudgetCard(budget.name, budget.spentAmount, budget.totalAmount, budget.progress, budget.usageText, budget.remainingText, budget.color, budget.emoji)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}
