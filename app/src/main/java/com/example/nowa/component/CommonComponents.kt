package com.example.nowa.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.nowa.ui.theme.*

data class Transaction(
    val name: String,
    val category: String,
    val amount: String,
    val color: Color,
    val emoji: String,
    val time: String = "Hari ini"
)

val recentTransactions = mutableStateListOf<Transaction>()

@Composable
fun TransactionItem(transaction: Transaction) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(48.dp).background(BackgroundGray, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(transaction.emoji, fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(transaction.name, fontWeight = FontWeight.Bold, color = TextBlack)
                Text(transaction.category, fontSize = 12.sp, color = TextGray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(transaction.amount, fontWeight = FontWeight.Bold, color = transaction.color)
                Text(transaction.time, fontSize = 10.sp, color = TextGray)
            }
        }
    }
}

@Composable
fun SectionHeader(text: String) {
    Text(
        text = text,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        color = TextGray,
        modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
    )
}

@Composable
fun SummaryCard(title: String, amount: String, icon: ImageVector, color: Color, modifier: Modifier, onClick: () -> Unit = {}) {
    Card(
        modifier = modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontSize = 10.sp, color = TextGray, fontWeight = FontWeight.Bold)
            Text(amount, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextBlack)
        }
    }
}

@Composable
fun MenuIcon(label: String, icon: ImageVector, bgColor: Color, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onClick() }) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = label, tint = DarkBlue)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun FilterChip(selected: Boolean, text: String, onClick: () -> Unit) {
    Surface(
        color = if (selected) White.copy(alpha = 0.2f) else White.copy(alpha = 0.1f),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(end = 8.dp).clickable { onClick() }
    ) {
        Text(
            text = text,
            color = White,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 14.sp
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Surface(
        color = White,
        tonalElevation = 8.dp,
        shadowElevation = 16.dp,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            modifier = Modifier.height(80.dp)
        ) {
            NavigationBarItem(
                selected = currentRoute == "home",
                onClick = { 
                    if (currentRoute != "home") {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                },
                icon = { Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(26.dp)) },
                label = { Text("HOME", fontSize = 10.sp, fontWeight = FontWeight.Black) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NowaPrimary,
                    unselectedIconColor = TextGray.copy(alpha = 0.5f),
                    selectedTextColor = NowaPrimary,
                    unselectedTextColor = TextGray.copy(alpha = 0.5f),
                    indicatorColor = NowaPrimary.copy(alpha = 0.1f)
                )
            )
            NavigationBarItem(
                selected = currentRoute == "history",
                onClick = { 
                    if (currentRoute != "history") {
                        navController.navigate("history")
                    }
                },
                icon = { Icon(Icons.Default.BarChart, contentDescription = null, modifier = Modifier.size(26.dp)) },
                label = { Text("RIWAYAT", fontSize = 10.sp, fontWeight = FontWeight.Black) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NowaPrimary,
                    unselectedIconColor = TextGray.copy(alpha = 0.5f),
                    selectedTextColor = NowaPrimary,
                    unselectedTextColor = TextGray.copy(alpha = 0.5f),
                    indicatorColor = NowaPrimary.copy(alpha = 0.1f)
                )
            )
            NavigationBarItem(
                selected = currentRoute == "goals",
                onClick = { 
                    if (currentRoute != "goals") {
                        navController.navigate("goals")
                    }
                },
                icon = { Icon(Icons.Default.TrackChanges, contentDescription = null, modifier = Modifier.size(26.dp)) },
                label = { Text("GOALS", fontSize = 10.sp, fontWeight = FontWeight.Black) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NowaPrimary,
                    unselectedIconColor = TextGray.copy(alpha = 0.5f),
                    selectedTextColor = NowaPrimary,
                    unselectedTextColor = TextGray.copy(alpha = 0.5f),
                    indicatorColor = NowaPrimary.copy(alpha = 0.1f)
                )
            )
        }
    }
}
