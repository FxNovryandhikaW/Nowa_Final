package com.example.nowa.screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nowa.ui.theme.*
import java.util.*

@Composable
fun TambahTargetScreen(navController: NavHostController) {
    var goalName by remember { mutableStateOf("") }
    var targetAmount by remember { mutableStateOf("") }
    var targetDate by remember { mutableStateOf("Pilih Tanggal") }
    
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            targetDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        },
        year, month, day
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        Column(modifier = Modifier.padding(24.dp).padding(top = 24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.background(White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Tambah Goal Baru", color = White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = White,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp).fillMaxWidth()) {
                Text("NAMA GOAL", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                OutlinedTextField(
                    value = goalName,
                    onValueChange = { goalName = it },
                    placeholder = { Text("mis. Beli Laptop, Liburan...", color = TextGray.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NowaPrimary,
                        unfocusedBorderColor = NowaLightBlue.copy(alpha = 0.5f)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("TARGET JUMLAH (IDR)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                OutlinedTextField(
                    value = targetAmount,
                    onValueChange = { targetAmount = it },
                    placeholder = { Text("0", color = TextGray.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NowaPrimary,
                        unfocusedBorderColor = NowaLightBlue.copy(alpha = 0.5f)
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
                    Text(
                        text = targetDate,
                        fontSize = 16.sp,
                        color = if (targetDate == "Pilih Tanggal") TextGray.copy(alpha = 0.5f) else TextBlack
                    )
                }
                HorizontalDivider(color = NowaLightBlue.copy(alpha = 0.5f))

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        if (goalName.isNotEmpty() && targetAmount.isNotEmpty()) {
                            globalGoals.add(
                                GoalData(
                                    name = goalName,
                                    targetAmount = "Rp$targetAmount",
                                    savedAmount = "Rp0",
                                    remainingAmount = targetDate,
                                    progress = 0f,
                                    percentage = "0%",
                                    emoji = "🎯"
                                )
                            )
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NowaPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🎯 ", fontSize = 16.sp)
                        Text("Simpan Goal", fontWeight = FontWeight.Bold)
                    }
                }
                TextButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Batal", color = TextGray)
                }
            }
        }
    }
}
