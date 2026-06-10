package com.example.nowa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nowa.component.BottomNavigationBar
import com.example.nowa.screens.*
import com.example.nowa.ui.theme.NowaBackground
import com.example.nowa.ui.theme.NowaPrimary
import com.example.nowa.ui.theme.NowaTheme
import com.example.nowa.ui.theme.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NowaTheme(dynamicColor = false) {
                val navController = rememberNavController()
                MainScreen(navController)
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        containerColor = NowaBackground,
        bottomBar = {
            val showBottomBar = listOf("home", "history", "goals", "profile", "accounts")
            if (currentRoute in showBottomBar) {
                BottomNavigationBar(navController)
            }
        },
        floatingActionButton = {
            val showFAB = listOf("home", "history", "goals")
            if (currentRoute in showFAB) {
                FloatingActionButton(
                    onClick = { navController.navigate("add_transaction") },
                    containerColor = NowaPrimary,
                    contentColor = White,
                    shape = CircleShape,
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("splash") { SplashSkrin(navController) }
            composable("login") { MasukScreen(navController) }
            composable("register") { DaftarScreen(navController) }
            composable("home") { BerandaRiwayatScreen(navController) }
            composable("history") { RiwayatScreen(navController) }
            composable("goals") { TargetScreen(navController) }
            composable("accounts") { AkunScreen(navController) }
            
            composable("account_detail?accountName={accountName}") { backStackEntry ->
                val accountName = backStackEntry.arguments?.getString("accountName") ?: ""
                DetailAkunScreen(navController, accountName)
            }
            
            composable("add_account") { TambahAkunScreen(navController) }
            
            composable("edit_account?accountName={accountName}") { backStackEntry ->
                val accountName = backStackEntry.arguments?.getString("accountName") ?: ""
                EditAkunScreen(navController, accountName)
            }
            
            composable("notifications") { NotifikasiScreen(navController) }
            composable("profile") { ProfilScreen(navController) }
            composable("profil") { ProfilScreen(navController) }
            composable("laporan") { LaporanScreen(navController) }
            composable("about") { TentangScreen(navController) }
            composable("add_transaction") { TambahTransaksiScreen(navController) }
            composable("add_budget") { TambahAnggaranScreen(navController) }
            composable("add_goal") { TambahTargetScreen(navController) }
            composable("add_account") { TambahAkunScreen(navController) }
        }
    }
}
