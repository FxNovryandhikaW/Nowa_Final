package com.example.nowa.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nowa.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionBottomSheet(onDismiss: () -> Unit) {
    ModalBottomSheet(onDismissRequest = onDismiss, containerColor = White) {
        Column(modifier = Modifier.padding(24.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Tambah Transaksi", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = DarkBlue)
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth().background(BackgroundGray, RoundedCornerShape(12.dp)).padding(4.dp)) {
                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenIncome),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("💚 Pemasukan", fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.width(4.dp))
                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = White),
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Text("❤️ Pengeluaran", color = TextGray, fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text("Rp 0", fontSize = 48.sp, fontWeight = FontWeight.ExtraBold, color = DarkBlue)
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            TransactionInputField("NOMINAL (IDR)", "0")
            TransactionInputField("KETERANGAN", "mis. Makan siang, Gaji...")
            TransactionInputField("KATEGORI", "Makanan / Gaji / Transport...")

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5C6BC0)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("💾 Simpan Transaksi", fontWeight = FontWeight.Bold)
            }
            TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                Text("Batal", color = TextGray)
            }
        }
    }
}

@Composable
fun TransactionInputField(label: String, placeholder: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DarkBlue)
        Spacer(modifier = Modifier.height(8.dp))
        Text(placeholder, color = TextGray, fontSize = 16.sp, modifier = Modifier.padding(start = 12.dp))
    }
}
