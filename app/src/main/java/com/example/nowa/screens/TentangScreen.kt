package com.example.kelompok_nokonteks_tam_nowa.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kelompok_nokonteks_tam_nowa.ui.theme.*

@Composable
fun TentangScreen(navController: NavHostController) {
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
                Text("Tentang NOWA", color = White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = White,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(NowaSecondary, RoundedCornerShape(20.dp))
                        .align(Alignment.CenterHorizontally),
                    contentAlignment = Alignment.Center
                ) {
                    Text("N", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = DarkBlue)
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "NOWA (Notes Walet)",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
                Text(
                    "Financial Health Check Assistant",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = TextGray
                )

                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    "Apa itu NOWA?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "NOWA (Notes Walet) adalah aplikasi mobile berbasis Android yang menghadirkan konsep Financial Health Check secara komprehensif. Aplikasi ini dirancang untuk membantu Anda mendiagnosis, memantau, dan meningkatkan kondisi kesehatan keuangan melalui pendekatan berbasis data.",
                    fontSize = 14.sp,
                    color = TextBlack,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Mengapa NOWA?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Berbeda dengan aplikasi pencatat keuangan biasa, NOWA menganalisis data keuangan secara holistik mencakup arus kas, rasio utang, dana darurat, dan target finansial untuk menghasilkan Skor Kesehatan Finansial (Financial Health Score) yang objektif dan terukur.",
                    fontSize = 14.sp,
                    color = TextBlack,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Visi Kami",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Membantu generasi muda Indonesia mencapai kemandirian finansial dengan menjembatani kesenjangan antara literasi keuangan dan praktik pengelolaan yang sehat dalam kehidupan sehari-hari.",
                    fontSize = 14.sp,
                    color = TextBlack,
                    lineHeight = 22.sp
                )
                
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    "v1.0.0 • Universitas Lampung",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = TextGray
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
