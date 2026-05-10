package com.example.nowa.screen

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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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

val NowaPrimary = Color(0xFF4840A3)
val NowaSecondary = Color(0xFFF9CD6A)
val NowaBackground = Color(0xFFD8EFF7)
val NowaLightBlue = Color(0xFF9CBBFC)

val dummyEmail = "muthia@nowa.com"
val dummyPassword = "nowa123"

@Composable
fun LoginScreen(onNavigateToRegister: () -> Unit, onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var pesanError by remember { mutableStateOf("") }
    var tabSelected by remember { mutableStateOf(0) }

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
                    text = "Selamat\nDatang 👋",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 40.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Masuk ke akun NOWA kamu",
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
                    .clickable { tabSelected = 0 }
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
                    .clickable {
                        tabSelected = 1
                        onNavigateToRegister()
                    }
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

        Spacer(modifier = Modifier.height(28.dp))

        Column(modifier = Modifier.padding(horizontal = 32.dp)) {
            Text(
                text = "EMAIL",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = email,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color(0x00000000),
                    unfocusedTextColor = Color(0x00000000),

                    focusedLabelColor = Color(0x00000000),
                    unfocusedLabelColor = Color(0x00000000)

                ),
                onValueChange = { email = it },
                placeholder = { Text("nama@email.com", color = Color(0x00000000)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp)
                    ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "PASSWORD",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )
            if (pesanError.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = pesanError, color = Color.Red, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(28.dp))
            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        pesanError = "Email dan password tidak boleh kosong"
                        return@Button
                    }
                    if (email == dummyEmail && password == dummyPassword) {
                        onLoginSuccess()
                    } else {
                        pesanError = "Email atau password salah"
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NowaPrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Masuk Sekarang", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = buildAnnotatedString {
                        append("Belum punya akun? ")
                        withStyle(SpanStyle(color = NowaPrimary, fontWeight = FontWeight.Bold)) {
                            append("Daftar di sini")
                        }
                    },
                    fontSize = 13.sp,
                    modifier = Modifier.clickable { onNavigateToRegister() }
                )
            }
        }
    }
}