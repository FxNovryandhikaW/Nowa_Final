package com.example.nowa.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nowa.ui.theme.*

@Composable
fun GoalCard(title: String, target: String, saved: String, remaining: String, progress: Float, percent: String, emoji: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = White),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(40.dp).background(BackgroundGray, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                    Text(emoji, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, fontWeight = FontWeight.Bold, color = TextBlack)
                    Text("Target: $target", fontSize = 10.sp, color = TextGray)
                }
                Text(percent, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = DarkBlue)
            }
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                color = DarkBlue,
                trackColor = DarkBlue.copy(alpha = 0.1f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Tersimpan: $saved", fontSize = 10.sp, color = TextBlack, fontWeight = FontWeight.Bold)
                Text(if (remaining.contains("Rp")) "Sisa: $remaining" else remaining, fontSize = 10.sp, color = TextGray)
            }
        }
    }
}

@Composable
fun BudgetCard(title: String, spent: String, limit: String, progress: Float, status: String, remaining: String, color: Color, emoji: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(emoji, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(title, color = White, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text(spent, color = White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("April 2026", color = White.copy(alpha = 0.7f), fontSize = 10.sp)
                Text("dari $limit", color = White.copy(alpha = 0.7f), fontSize = 10.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                color = NowaSecondary,
                trackColor = White.copy(alpha = 0.2f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(status, color = White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Text(remaining, color = White, fontSize = 10.sp)
            }
        }
    }
}