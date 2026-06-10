package com.example.nowa.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nowa.ui.theme.NowaLightBlue

import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import com.example.nowa.data.model.BudgetModel
import com.example.nowa.data.repository.BudgetRepository
import com.example.nowa.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahAnggaranScreen(navController: NavHostController) {
    var categoryName by remember { mutableStateOf("") }
    var budgetLimit by remember { mutableStateOf("") }
    var selectedEmoji by remember { mutableStateOf("💰") }
    var showEmojiPicker by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val repository = remember { BudgetRepository() }
    val context = LocalContext.current

    val emojis = listOf("💰", "🍔", "🚌", "🎮", "👕", "🏠", "🏥", "🎓", "🛒", "🔌", "🎬", "✈️")

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
                Text("Tambah Budget Bulanan", color = White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = White,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp).fillMaxWidth()) {
                Text("KATEGORI BUDGET", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    placeholder = { Text("mis. Makanan, Transport, Hiburan...", color = TextGray.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NowaPrimary,
                        unfocusedBorderColor = NowaLightBlue.copy(alpha = 0.5f)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("LIMIT ANGGARAN (IDR)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                OutlinedTextField(
                    value = budgetLimit,
                    onValueChange = { budgetLimit = it },
                    placeholder = { Text("0", color = TextGray.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NowaPrimary,
                        unfocusedBorderColor = NowaLightBlue.copy(alpha = 0.5f)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("ICON / EMOJI", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)

                Card(
                    onClick = { showEmojiPicker = true },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = BackgroundGray)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(selectedEmoji, fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Pilih Emoji", color = TextBlack)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        if (categoryName.isNotEmpty() && budgetLimit.isNotEmpty()) {
                            isLoading = true
                            val budget = BudgetModel(
                                name = categoryName,
                                limitAmount = budgetLimit.toLongOrNull() ?: 0L,
                                spentAmount = 0L,
                                emoji = selectedEmoji,
                                colorHex = "#5C6BC0" // Default or pick from theme
                            )

                            scope.launch {
                                val result = repository.addBudget(budget)
                                if (result.isSuccess) {
                                    Toast.makeText(context, "Budget berhasil disimpan!", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                } else {
                                    Toast.makeText(context, "Gagal: ${result.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
                                }
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NowaPrimary),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = White, modifier = Modifier.size(24.dp))
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("💰 ", fontSize = 16.sp)
                            Text("Simpan Budget", fontWeight = FontWeight.Bold)
                        }
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

    if (showEmojiPicker) {
        AlertDialog(
            onDismissRequest = { showEmojiPicker = false },
            title = { Text("Pilih Emoji") },
            text = {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(emojis) { emoji ->
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (selectedEmoji == emoji) NowaSecondary else Color.Transparent)
                                .clickable {
                                    selectedEmoji = emoji
                                    showEmojiPicker = false
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(emoji, fontSize = 24.sp)
                        }
                    }
                }
            },
            confirmButton = {}
        )
    }
}
