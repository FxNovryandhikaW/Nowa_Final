package com.example.nowa.screens.budget_goal

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nowa.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahTargetBottomSheet(onDismiss: () -> Unit) {
    ModalBottomSheet(onDismissRequest = onDismiss, containerColor = White) {
        Column(modifier = Modifier.padding(24.dp).fillMaxWidth()) {
            Text("Tambah Goal Baru", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = DarkBlue)
            Spacer(modifier = Modifier.height(24.dp))

            Text("NAMA GOAL", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("mis. Beli Laptop, Liburan...", color = TextGray.copy(alpha = 0.5f)) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).border(1.dp, Color(0xFFBBDEFB), RoundedCornerShape(12.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = TextBlack,
                    unfocusedTextColor = TextBlack
                )
            )

            Text("TARGET JUMLAH (IDR)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("0", color = TextGray.copy(alpha = 0.5f)) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).border(1.dp, Color(0xFFBBDEFB), RoundedCornerShape(12.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = TextBlack,
                    unfocusedTextColor = TextBlack
                )
            )

            Text("TARGET TANGGAL", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
            Text("Pilih Tanggal", modifier = Modifier.padding(vertical = 12.dp), color = TextBlack)

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5C6BC0)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🎯 ", fontSize = 16.sp)
                    Text("Simpan Goal", fontWeight = FontWeight.Bold)
                }
            }
            TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                Text("Batal", color = TextGray)
            }
        }
    }
}
