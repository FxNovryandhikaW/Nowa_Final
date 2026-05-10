package com.example.nowa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nowa.screens.*
import com.example.nowa.ui.theme.NowaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NowaTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashSkrin(navController) }
        composable("login") { MasukScreen(navController) }
        composable("register") { DaftarScreen(navController) }
        composable("home") { BerandaRiwayatScreen(navController) }
        composable("history") { RiwayatScreen(navController) }
        composable("goals") { TargetScreen(navController) }
        composable("accounts") { AkunScreen(navController) }
        composable("add_account") { TambahAkunScreen(navController) }
        composable("edit_account") { EditAkunScreen(navController) }
        composable("account_detail") { DetailAkunScreen(navController) }
        composable("notifikasi") { NotifikasiScreen(navController) }
        composable("profil") { ProfilScreen(navController) }
        composable("tentang") { TentangScreen(navController) }
        composable("postingan") { PostinganScreen() }
    }
}
