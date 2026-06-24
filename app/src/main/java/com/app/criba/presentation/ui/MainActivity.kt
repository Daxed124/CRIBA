package com.app.criba.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.criba.presentation.ui.components.CribaTheme
import com.app.criba.presentation.ui.navigation.CribaBottomBar
import com.app.criba.presentation.ui.navigation.CribaNavGraph
import com.app.criba.presentation.ui.navigation.CribaTopBar
import com.app.criba.presentation.ui.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CribaTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route ?: ""

                // Determine visibility
                val showBottomBar = currentRoute in listOf(
                    Screen.Dashboard.route,
                    Screen.Parcelas.route,
                    Screen.Historial.route,
                    Screen.Salud.route
                )
                
                val showTopBar = currentRoute != Screen.Login.route

                val title = when {
                    currentRoute == Screen.Dashboard.route -> "Dashboard"
                    currentRoute == Screen.Parcelas.route -> "Mis Parcelas"
                    currentRoute == Screen.Historial.route -> "Historial"
                    currentRoute == Screen.Salud.route -> "Salud del Cultivo"
                    currentRoute.startsWith("cycle") -> "Detalle de Ciclo"
                    currentRoute.startsWith("finance") -> "Finanzas"
                    currentRoute.startsWith("pest") -> "Plagas"
                    currentRoute.startsWith("climate") -> "Clima"
                    currentRoute == Screen.TerrainForm.route -> "Añadir Terreno"
                    currentRoute == Screen.Map.route -> "Mapa"
                    else -> "AgroMonitor Pro"
                }

                val showBackButton = !showBottomBar && showTopBar

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (showTopBar) {
                            CribaTopBar(
                                title = title,
                                showBackButton = showBackButton,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    },
                    bottomBar = {
                        if (showBottomBar) {
                            CribaBottomBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    CribaNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
