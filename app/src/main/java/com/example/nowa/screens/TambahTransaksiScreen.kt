package com.example.nowa.screens

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
                Text("Tambah Transaksi", color = White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = White,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp).fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(BackgroundGray)
                        .padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isPemasukan) GreenIncome else Color.Transparent)
                            .clickable { isPemasukan = true }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🍏 Pemasukan",
                            color = if (isPemasukan) White else TextGray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (!isPemasukan) RedExpense else Color.Transparent)
                            .clickable { isPemasukan = false }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "❤️ Pengeluaran",
                            color = if (!isPemasukan) White else TextGray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Rp ${if (nominal.isEmpty()) "0" else nominal}",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = DarkBlue
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
                Text("NOMINAL (IDR)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                OutlinedTextField(
                    value = nominal,
                    onValueChange = { nominal = it },
                    placeholder = { Text("0", color = TextGray.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NowaPrimary,
                        unfocusedBorderColor = NowaLightBlue.copy(alpha = 0.5f)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("KETERANGAN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                OutlinedTextField(
                    value = keterangan,
                    onValueChange = { keterangan = it },
                    placeholder = { Text("mis. Makan siang, Gaji...", color = TextGray.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NowaPrimary,
                        unfocusedBorderColor = NowaLightBlue.copy(alpha = 0.5f)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("KATEGORI", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
                OutlinedTextField(
                    value = kategori,
                    onValueChange = { kategori = it },
                    placeholder = { Text("Makanan / Gaji / Transport...", color = TextGray.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NowaPrimary,
                        unfocusedBorderColor = NowaLightBlue.copy(alpha = 0.5f)
                    )
                )

                Spacer(modifier = Modifier.weight(1f))
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
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NowaPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("💾 ", fontSize = 16.sp)
                        Text("Simpan Transaksi", fontWeight = FontWeight.Bold)
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