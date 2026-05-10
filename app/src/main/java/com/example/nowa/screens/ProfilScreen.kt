package com.example.nowa.screens

import androidx.compose.foundation.Image
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
            .background(DarkBlue)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.background(White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text("Profil Pengguna", color = White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("KitaPastiBisa", color = White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("KitaPastiBisa@gmail.com", color = White.copy(alpha = 0.7f), fontSize = 12.sp)
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = BackgroundGray,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                item { SectionHeader("AKUN & KEUANGAN") }
                item { ProfileMenuItem("Kelola Akun", "3 akun terdaftar", Icons.Outlined.AccountBalance) { navController.navigate("add_account") } }

                item { SectionHeader("KOMUNITAS") }
                item { ProfileMenuItem("Postingan", "Lihat postingan terbaru", Icons.Outlined.Article) { navController.navigate("postingan") } }

                item { SectionHeader("PENGATURAN") }
                item { ProfileMenuItem("Notifikasi", "Atur pemberitahuan", Icons.Outlined.Notifications) { navController.navigate("notifikasi") } }

                item { SectionHeader("TENTANG") }
                item { ProfileMenuItem("Tentang NOWA", "v1.0.0 • Universitas Lampung", Icons.Outlined.Info) { navController.navigate("tentang") } }

                item { Spacer(modifier = Modifier.height(24.dp)) }
                item {
                    Button(
                        onClick = { navController.navigate("splash") { popUpTo(0) } },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RedExpense.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("🚪 Keluar dari Akun", color = RedExpense, fontWeight = FontWeight.Bold)
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
