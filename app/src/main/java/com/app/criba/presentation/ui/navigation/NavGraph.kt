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
                onNavigateToMapa = { terrainId -> navController.navigate(Screen.MapaTerreno.createRoute(terrainId)) }
            )
        }

        composable(Screen.Parcelas.route) {
            // Placeholder for Phase 6
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Pantalla de Parcelas (En Desarrollo)")
            }
        }

        composable(Screen.Historial.route) {
            // Placeholder for Phase 9
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Pantalla de Historial Climático (En Desarrollo)")
            }
        }

        composable(Screen.Salud.route) {
            // Placeholder for Phase 7
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Pantalla de Salud y Plagas (En Desarrollo)")
            }
        }

        composable(Screen.TerrainForm.route) {
            com.app.criba.presentation.ui.terrain.TerrainFormScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Cycle.route) { backStackEntry ->
            val terrainId = backStackEntry.arguments?.getString("terrainId")?.toLongOrNull() ?: 0L
            com.app.criba.presentation.ui.cycle.CycleScreen(
                terrainId = terrainId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToFinance = { cycleId -> navController.navigate(Screen.Finance.createRoute(cycleId)) },
                onNavigateToPest = { cycleId -> navController.navigate(Screen.Pest.createRoute(cycleId)) },
                onNavigateToClimate = { cycleId -> navController.navigate(Screen.Climate.createRoute(cycleId)) },
                onNavigateToDashboard = { _ -> navController.navigate(Screen.Dashboard.route) }
            )
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
            com.app.criba.presentation.ui.pest.PestScreen(
                cycleId = cycleId,
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
                onNavigateToTerrainForm = { navController.navigate(Screen.TerrainForm.route) },
                onNavigateToCycle = { terrainId -> navController.navigate(Screen.Cycle.createRoute(terrainId)) }
            )
        }
    }
}
