package com.example.kelompok_nokonteks_tam_nowa.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val registeredUsers = mutableListOf(
    Pair("muthia@nowa.com", "nowa123")
)

@Composable
fun RegisterScreen(onNavigateToLogin: () -> Unit) {
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var konfirmasi by remember { mutableStateOf("") }
    var pesanError by remember { mutableStateOf("") }
    var tabSelected by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NowaBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                .background(NowaPrimary)
                .padding(start = 32.dp, end = 32.dp, top = 56.dp, bottom = 36.dp)
        ) {
            Column {
                Text(
                    text = "Buat Akun\nBaru 📝",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 40.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Mulai perjalanan finansialmu bersama NOWA",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (tabSelected == 0) NowaPrimary else Color.Transparent)
                    .clickable {
                        tabSelected = 0
                        onNavigateToLogin()
                    }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Masuk",
                    color = if (tabSelected == 0) Color.White else Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (tabSelected == 1) NowaPrimary else Color.Transparent)
                    .clickable { tabSelected = 1 }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Daftar",
                    color = if (tabSelected == 1) Color.White else Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.padding(horizontal = 32.dp)) {
            Text("NAMA LENGKAP", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = nama,
                onValueChange = { nama = it },
                placeholder = { Text("Nama kamu", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text("EMAIL", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("nama@email.com", color = Color.LightGray) },
                modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text("PASSWORD", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text("KONFIRMASI PASSWORD", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = konfirmasi,
                onValueChange = { konfirmasi = it },
                modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )
            if (pesanError.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = pesanError, color = Color.Red, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        pesanError = "Semua kolom harus diisi"
                        return@Button
                    }
                    if (password != konfirmasi) {
                        pesanError = "Password tidak cocok"
                        return@Button
                    }
                    val sudahAda = registeredUsers.any { it.first == email }
                    if (sudahAda) {
                        pesanError = "Email sudah terdaftar"
                    } else {
                        registeredUsers.add(Pair(email, password))
                        onNavigateToLogin()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NowaPrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Daftar Sekarang", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = buildAnnotatedString {
                        append("Sudah punya akun? ")
                        withStyle(SpanStyle(color = NowaPrimary, fontWeight = FontWeight.Bold)) {
                            append("Masuk di sini")
                        }
                    },
                    fontSize = 13.sp,
                    modifier = Modifier.clickable { onNavigateToLogin() }
                )
            }
        }
    }
}