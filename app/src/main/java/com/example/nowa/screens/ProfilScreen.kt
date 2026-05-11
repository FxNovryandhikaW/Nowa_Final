package com.example.nowa.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nowa.R
import com.example.nowa.component.SectionHeader
import com.example.nowa.ui.theme.*

@Composable
fun ProfilScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NowaPrimaryDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(NowaSecondary),
                contentAlignment = Alignment.Center
            ) {
                Text("R", fontSize = 48.sp, fontWeight = FontWeight.Black, color = NowaPrimaryDark)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text("Reggy Desvita", color = White, fontSize = 28.sp, fontWeight = FontWeight.Black)
            Text("2417051016@students.unila.ac.id", color = White.copy(alpha = 0.7f), fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = NowaBackground,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                item { SectionHeader("AKUN & KEUANGAN") }
                item { ProfileMenuItem("Kelola Akun", "3 akun terdaftar", Icons.Outlined.AccountBalance) { navController.navigate("accounts") } }
                item { ProfileMenuItem("Laporan Keuangan", "Ringkasan bulanan", Icons.Outlined.BarChart) { } }

                item { SectionHeader("PENGATURAN") }
                item { ProfileMenuItem("Notifikasi", "Aktif", Icons.Outlined.Notifications) { navController.navigate("notifications") } }
                item { ProfileMenuItem("Keamanan & Privasi", "Enkripsi lokal aktif", Icons.Outlined.Lock) { } }
                item { ProfileMenuItem("Bahasa", "Bahasa Indonesia", Icons.Outlined.Public) { } }

                item { SectionHeader("TENTANG") }
                item { ProfileMenuItem("Tentang NOWA", "v1.0.0 · Universitas Lampung", Icons.Outlined.Info) { navController.navigate("about") } }

                item { Spacer(modifier = Modifier.height(24.dp)) }
                item {
                    Button(
                        onClick = { navController.navigate("splash") { popUpTo(0) } },
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RedExpense.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, RedExpense.copy(alpha = 0.2f))
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("📕 ", fontSize = 16.sp)
                            Text("Keluar dari Akun", color = RedExpense, fontWeight = FontWeight.Black, fontSize = 16.sp)
                        }
                    }
                }
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(top = 20.dp), contentAlignment = Alignment.Center) {
                        Text(
                            "← Kembali ke Dashboard",
                            color = NowaPrimaryDark.copy(alpha = 0.6f),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileMenuItem(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(BackgroundGray, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = DarkBlue, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, color = TextBlack)
                Text(subtitle, fontSize = 12.sp, color = TextGray)
            }
            Icon(androidx.compose.material.icons.Icons.Outlined.ChevronRight, contentDescription = null, tint = TextGray)
        }
    }
}
