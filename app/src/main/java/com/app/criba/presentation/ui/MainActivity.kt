package com.app.criba.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.criba.presentation.ui.components.CribaTheme
import com.app.criba.presentation.ui.navigation.CribaBottomBar
import com.app.criba.presentation.ui.navigation.CribaNavGraph
import com.app.criba.presentation.ui.navigation.CribaTopBar
import com.app.criba.presentation.ui.navigation.Screen
import com.app.criba.presentation.viewmodel.SettingsViewModel
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
                    currentRoute == Screen.Dashboard.route -> "Inicio"
                    currentRoute == Screen.Parcelas.route -> "Mis Parcelas"
                    currentRoute == Screen.Historial.route -> "Historial"
                    currentRoute == Screen.Salud.route -> "Salud del Cultivo"
                    currentRoute == Screen.Settings.route -> "Configuración"
                    currentRoute.startsWith("parcela") -> "Detalle de Parcela"
                    currentRoute.startsWith("cycle") -> "Detalle de Ciclo"
                    currentRoute.startsWith("finance") -> "Finanzas"
                    currentRoute.startsWith("registrar_plaga") -> "Registrar Plaga"
                    currentRoute.startsWith("pest") -> "Plagas"
                    currentRoute.startsWith("climate") -> "Clima"
                    currentRoute == Screen.TerrainForm.route -> "Añadir Terreno"
                    currentRoute == Screen.Map.route -> "Mapa"
                    else -> ""
                }

                val showBackButton = !showBottomBar && showTopBar

                // Usuario para mostrar en el topbar (nombre + imagen)
                val settingsViewModel: SettingsViewModel = hiltViewModel()
                val currentUser by settingsViewModel.user.collectAsStateWithLifecycle()
                LaunchedEffect(currentRoute) { settingsViewModel.refresh() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (showTopBar) {
                            CribaTopBar(
                                title = title,
                                showBackButton = showBackButton,
                                onBackClick = { navController.popBackStack() },
                                showSettings = currentRoute != Screen.Settings.route,
                                onSettingsClick = { navController.navigate(Screen.Settings.route) },
                                userName = currentUser?.displayName,
                                userPhotoUrl = currentUser?.photoUrl
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
