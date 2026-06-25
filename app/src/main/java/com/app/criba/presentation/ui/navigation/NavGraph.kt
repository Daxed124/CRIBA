package com.app.criba.presentation.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.criba.presentation.ui.login.LoginScreen

@Composable
fun CribaNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Dashboard.route) {
            com.app.criba.presentation.ui.dashboard.DashboardScreen(
                onNavigateToParcela = { terrainId -> navController.navigate(Screen.DetalleParcela.createRoute(terrainId)) },
                onNavigateToMapa = { navController.navigate(Screen.Map.route) }
            )
        }

        composable(Screen.Parcelas.route) {
            com.app.criba.presentation.ui.parcelas.ParcelasScreen(
                onNavigateToDetalle = { terrainId -> navController.navigate(Screen.DetalleParcela.createRoute(terrainId)) }
            )
        }
        
        composable(Screen.DetalleParcela.route) { backStackEntry ->
            val terrainId = backStackEntry.arguments?.getString("terrenoId")?.toLongOrNull() ?: 0L
            com.app.criba.presentation.ui.parcelas.DetalleParcelaScreen(
                terrenoId = terrainId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPlagas = { cycleId -> navController.navigate(Screen.Pest.createRoute(cycleId)) },
                onNavigateToClima = { cycleId -> navController.navigate(Screen.Climate.createRoute(cycleId)) },
                onNavigateToFinanzas = { cycleId -> navController.navigate(Screen.Finance.createRoute(cycleId)) }
            )
        }

        composable(Screen.Historial.route) {
            // Placeholder for Phase 9
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Pantalla de Historial Climático (En Desarrollo)")
            }
        }

        composable(Screen.Salud.route) {
            com.app.criba.presentation.plagas.SaludScreen()
        }



        composable(Screen.Finance.route) { backStackEntry ->
            val cycleId = backStackEntry.arguments?.getString("cycleId")?.toLongOrNull() ?: 0L
            com.app.criba.presentation.ui.finance.FinanceScreen(
                cycleId = cycleId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Pest.route) { backStackEntry ->
            val cycleId = backStackEntry.arguments?.getString("cycleId")?.toLongOrNull() ?: 0L
            com.app.criba.presentation.plagas.PlagasListScreen(
                cicloId = cycleId,
                onNavigateToRegistrarPlaga = { navController.navigate(Screen.RegistrarPlaga.createRoute(cycleId)) }
            )
        }

        composable(Screen.RegistrarPlaga.route) { backStackEntry ->
            val cycleId = backStackEntry.arguments?.getString("cycleId")?.toLongOrNull() ?: 0L
            com.app.criba.presentation.plagas.RegistrarPlagaScreen(
                cicloId = cycleId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Climate.route) { backStackEntry ->
            val cycleId = backStackEntry.arguments?.getString("cycleId")?.toLongOrNull() ?: 0L
            com.app.criba.presentation.ui.climate.ClimateScreen(
                cycleId = cycleId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Map.route) {
            com.app.criba.presentation.ui.map.MapScreen(
                onNavigateToTerrainForm = { navController.navigate(Screen.Parcelas.route) },
                onNavigateToCycle = { terrainId -> navController.navigate(Screen.DetalleParcela.createRoute(terrainId)) }
            )
        }
    }
}
