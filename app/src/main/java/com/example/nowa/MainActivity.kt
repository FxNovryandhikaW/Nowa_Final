package com.example.kelompok_nokonteks_tam_nowa

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
import com.example.kelompok_nokonteks_tam_nowa.component.BottomNavigationBar
import com.example.kelompok_nokonteks_tam_nowa.screens.*
import com.example.kelompok_nokonteks_tam_nowa.ui.theme.DarkBlue
import com.example.kelompok_nokonteks_tam_nowa.ui.theme.KelompokNoKonteks_TAM_NOWATheme
import com.example.kelompok_nokonteks_tam_nowa.ui.theme.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KelompokNoKonteks_TAM_NOWATheme {
                val navController = rememberNavController()
                MainScreen(navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            val hideBottomBar = listOf("splash", "login", "register", "profile", "accounts", "account_detail", "add_account", "edit_account", "add_goal", "add_budget", "add_transaction", "notifications", "about")
            if (currentRoute !in hideBottomBar) {
                BottomNavigationBar(navController)
            }
        },
        floatingActionButton = {
            if (currentRoute == "home" || currentRoute == "history") {
                FloatingActionButton(
                    onClick = { navController.navigate("add_transaction") },
                    containerColor = DarkBlue,
                    contentColor = White,
                    shape = CircleShape,
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
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
            composable("history") { RiwayatScreen() }
            composable("goals") { TargetScreen(navController) }
            composable("accounts") { AkunScreen(navController) }
            composable("account_detail") { DetailAkunScreen(navController) }
            composable("profile") { ProfilScreen(navController) }
            composable("add_account") { TambahAkunScreen(navController) }
            composable("edit_account") { EditAkunScreen(navController) }
            composable("add_goal") { TambahTargetScreen(navController) }
            composable("add_budget") { TambahAnggaranScreen(navController) }
            composable("add_transaction") { TambahTransaksiScreen(navController) }
            composable("notifications") { NotifikasiScreen(navController) }
            composable("about") { TentangScreen(navController) }
            composable("posts") { PostinganScreen() }
        }
    }
}
