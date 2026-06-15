package com.example.nowa.screens.budget_goal

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nowa.data.model.BudgetModel
import com.example.nowa.data.repository.BudgetRepository
import com.example.nowa.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun EditBudgetScreen(navController: NavHostController, budgetId: String) {
    val repository = remember { BudgetRepository() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    
    var budget by remember { mutableStateOf<BudgetModel?>(null) }
    var budgetName by remember { mutableStateOf("") }
    var limitAmount by remember { mutableStateOf("") }
    var spentAmount by remember { mutableStateOf("") }
    var colorHex by remember { mutableStateOf("#5C6BC0") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val result = repository.getBudgets()
        if (result.isSuccess) {
            val foundBudget = result.getOrNull()?.find { it.id == budgetId }
            if (foundBudget != null) {
                budget = foundBudget
                budgetName = foundBudget.name
                limitAmount = foundBudget.limitAmount.toString()
                spentAmount = foundBudget.spentAmount.toString()
                colorHex = foundBudget.colorHex
            }
        }
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding()
    ) {
        Column(modifier = Modifier.padding(24.dp).padding(top = 24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.background(White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = White)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Edit Budget", color = White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
                
                if (budget != null) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                val res = repository.deleteBudget(budget!!.id)
                                if (res.isSuccess) {
                                    Toast.makeText(context, "Budget dihapus", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                }
                            }
                        },
                        modifier = Modifier.background(RedExpense.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = RedExpense)
                    }
                }
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = White,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {
                    Text("NAMA BUDGET", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                    OutlinedTextField(
                        value = budgetName,
                        onValueChange = { budgetName = it },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NowaPrimary,
                            unfocusedBorderColor = NowaBackground,
                            focusedTextColor = TextBlack,
                            unfocusedTextColor = TextBlack
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("LIMIT BUDGET (IDR)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                    OutlinedTextField(
                        value = limitAmount,
                        onValueChange = { if (it.all { char -> char.isDigit() }) limitAmount = it },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NowaPrimary,
                            unfocusedBorderColor = NowaBackground,
                            focusedTextColor = TextBlack,
                            unfocusedTextColor = TextBlack
                        )
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            if (budget != null) {
                                val updatedBudget = budget!!.copy(
                                    name = budgetName,
                                    limitAmount = limitAmount.toLongOrNull() ?: 0L,
                                    spentAmount = spentAmount.toLongOrNull() ?: 0L
                                )
                                
                                scope.launch {
                                    val res = repository.updateBudget(updatedBudget)
                                    if (res.isSuccess) {
                                        Toast.makeText(context, "Perubahan disimpan", Toast.LENGTH_SHORT).show()
                                        navController.popBackStack()
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NowaPrimary),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("💾 ", fontSize = 16.sp)
                            Text("Simpan Perubahan", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
