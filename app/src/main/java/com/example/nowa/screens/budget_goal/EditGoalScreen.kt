package com.example.nowa.screens.budget_goal

import android.app.DatePickerDialog
import android.widget.DatePicker
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nowa.data.model.GoalModel
import com.example.nowa.data.repository.GoalRepository
import com.example.nowa.ui.theme.*
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun EditGoalScreen(navController: NavHostController, goalId: String) {
    val repository = remember { GoalRepository() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    
    var goal by remember { mutableStateOf<GoalModel?>(null) }
    var goalName by remember { mutableStateOf("") }
    var targetAmount by remember { mutableStateOf("") }
    var savedAmount by remember { mutableStateOf("") }
    var targetDate by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val result = repository.getGoals()
        if (result.isSuccess) {
            val foundGoal = result.getOrNull()?.find { it.id == goalId }
            if (foundGoal != null) {
                goal = foundGoal
                goalName = foundGoal.name
                targetAmount = foundGoal.targetAmount.toString()
                savedAmount = foundGoal.savedAmount.toString()
                targetDate = foundGoal.targetDate
            }
        }
        isLoading = false
    }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            targetDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        },
        calendar[Calendar.YEAR],
        calendar[Calendar.MONTH],
        calendar[Calendar.DAY_OF_MONTH]
    )

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
                    Text("Edit Goal", color = White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
                
                if (goal != null) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                val res = repository.deleteGoal(goal!!.id)
                                if (res.isSuccess) {
                                    Toast.makeText(context, "Goal dihapus", Toast.LENGTH_SHORT).show()
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
                    Text("NAMA GOAL", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                    OutlinedTextField(
                        value = goalName,
                        onValueChange = { goalName = it },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NowaPrimary,
                            unfocusedBorderColor = NowaBackground
                        )
                    )


                    Spacer(modifier = Modifier.height(16.dp))
                    Text("TARGET JUMLAH (IDR)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                    OutlinedTextField(
                        value = targetAmount,
                        onValueChange = { if (it.all { char -> char.isDigit() }) targetAmount = it },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NowaPrimary,
                            unfocusedBorderColor = NowaBackground
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("TARGET TANGGAL", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                            .clickable { datePickerDialog.show() }
                    ) {
                        Text(text = targetDate, fontSize = 16.sp, color = TextBlack)
                    }
                    HorizontalDivider(color = NowaBackground)

                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            if (goal != null) {
                                val updatedGoal = goal!!.copy(
                                    name = goalName,
                                    targetAmount = targetAmount.toLongOrNull() ?: 0L,
                                    savedAmount = savedAmount.toLongOrNull() ?: 0L,
                                    targetDate = targetDate
                                )
                                
                                scope.launch {
                                    val res = repository.updateGoal(updatedGoal)
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
