package com.example.nowa.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.nowa.component.Transaction
import com.example.nowa.component.recentTransactions
import com.example.nowa.ui.theme.*

@Composable
fun TambahTransaksiScreen(navController: NavHostController) {
    var isPemasukan by remember { mutableStateOf(true) }
    var nominal by remember { mutableStateOf("") }
    var keterangan by remember { mutableStateOf("") }
    var kategori by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NowaPrimaryDark.copy(alpha = 0.5f))
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = White,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp).fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .clip(CircleShape)
                        .background(TextGray.copy(alpha = 0.3f))
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Tambah Transaksi",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = NowaPrimaryDark,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(NowaBackground)
                        .padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isPemasukan) Color(0xFF52B68C) else Color.Transparent)
                            .clickable { isPemasukan = true }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🍏 ", fontSize = 14.sp)
                            Text(
                                text = "Pemasukan",
                                color = if (isPemasukan) White else TextGray,
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (!isPemasukan) Color(0xFFE57373) else Color.Transparent)
                            .clickable { isPemasukan = false }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("❤️ ", fontSize = 14.sp)
                            Text(
                                text = "Pengeluaran",
                                color = if (!isPemasukan) White else TextGray,
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Rp ${if (nominal.isEmpty()) "0" else nominal}",
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Black,
                        color = NowaPrimaryDark
                    )
                }
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(NowaBackground))

                Spacer(modifier = Modifier.height(24.dp))
                Text("NOMINAL (IDR)", fontSize = 11.sp, fontWeight = FontWeight.Black, color = TextGray, letterSpacing = 1.sp)
                OutlinedTextField(
                    value = nominal,
                    onValueChange = { if (it.all { char -> char.isDigit() }) nominal = it },
                    placeholder = { Text("0", color = TextGray.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NowaPrimary,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("KETERANGAN", fontSize = 11.sp, fontWeight = FontWeight.Black, color = TextGray, letterSpacing = 1.sp)
                OutlinedTextField(
                    value = keterangan,
                    onValueChange = { keterangan = it },
                    placeholder = { Text("mis. Makan siang, Gaji...", color = TextGray.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NowaPrimary,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("KATEGORI", fontSize = 11.sp, fontWeight = FontWeight.Black, color = TextGray, letterSpacing = 1.sp)
                OutlinedTextField(
                    value = kategori,
                    onValueChange = { kategori = it },
                    placeholder = { Text("Makanan / Gaji / Transport...", color = TextGray.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NowaPrimary,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        if (nominal.isNotEmpty() && keterangan.isNotEmpty()) {
                            val newTransaction = Transaction(
                                name = keterangan,
                                category = "${if (isPemasukan) "Pemasukan" else "Pengeluaran"} · $kategori",
                                amount = "${if (isPemasukan) "+" else "-"}Rp$nominal",
                                color = if (isPemasukan) GreenIncome else RedExpense,
                                emoji = if (isPemasukan) "💰" else "🛒"
                            )
                            recentTransactions.add(0, newTransaction)
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NowaPrimary),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("💾 ", fontSize = 16.sp)
                        Text("Simpan Transaksi", fontWeight = FontWeight.Black, fontSize = 16.sp)
                    }
                }
                TextButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                ) {
                    Text("Batal", color = TextGray, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
