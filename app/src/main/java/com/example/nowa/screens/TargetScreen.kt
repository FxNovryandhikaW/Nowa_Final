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
import com.example.nowa.data.*
import com.example.nowa.ui.theme.*

@Composable
fun TargetScreen(navController: NavHostController) {
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
                items(globalGoals) { goal ->
                    GoalCard(goal.name, goal.targetAmount, goal.savedAmount, goal.remainingAmount, goal.progress, goal.percentage, goal.emoji)
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
                items(globalBudgets) { budget ->
                    BudgetCard(budget.name, budget.spentAmount, budget.totalAmount, budget.progress, budget.usageText, budget.remainingText, budget.color, budget.emoji)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
