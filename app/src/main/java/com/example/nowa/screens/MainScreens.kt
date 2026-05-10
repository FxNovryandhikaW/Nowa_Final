package com.example.nowa.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nowa.ui.theme.*

@Composable
fun BerandaRiwayatScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize().background(NowaBackground), contentAlignment = Alignment.Center) {
        Text("Beranda Riwayat Screen (Placeholder)", color = NowaPrimary, fontSize = 20.sp)
    }
}

@Composable
fun RiwayatScreen() {
    Box(modifier = Modifier.fillMaxSize().background(NowaBackground), contentAlignment = Alignment.Center) {
        Text("Riwayat Screen (Placeholder)", color = NowaPrimary, fontSize = 20.sp)
    }
}

@Composable
fun TargetScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize().background(NowaBackground), contentAlignment = Alignment.Center) {
        Text("Target Screen (Placeholder)", color = NowaPrimary, fontSize = 20.sp)
    }
}

@Composable
fun TambahTargetScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize().background(NowaBackground), contentAlignment = Alignment.Center) {
        Text("Tambah Target Screen (Placeholder)", color = NowaPrimary, fontSize = 20.sp)
    }
}
