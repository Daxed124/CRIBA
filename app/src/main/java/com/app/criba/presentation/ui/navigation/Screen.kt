package com.app.criba.presentation.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Landscape
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    // Auth
    data object Login : Screen("login")

    // Bottom Nav (4 tabs principales)
    data object Dashboard : Screen("dashboard")
    data object Map : Screen("map")
    data object Parcelas : Screen("parcelas")
    data object Historial : Screen("historial")
    data object Salud : Screen("salud")

    // Sub-pantallas
    data object TerrainForm : Screen("terrain_form")
    data object DetalleParcela : Screen("parcela/{terrenoId}") {
        fun createRoute(terrenoId: Long) = "parcela/$terrenoId"
    }
    data object Cycle : Screen("cycle/{terrainId}") {
        fun createRoute(terrainId: Long) = "cycle/$terrainId"
    }
    data object Finance : Screen("finance/{cycleId}") {
        fun createRoute(cycleId: Long) = "finance/$cycleId"
    }
    data object Pest : Screen("pest/{cycleId}") {
        fun createRoute(cycleId: Long) = "pest/$cycleId"
    }
    data object RegistrarPlaga : Screen("registrar_plaga/{cycleId}") {
        fun createRoute(cycleId: Long) = "registrar_plaga/$cycleId"
    }
    data object Climate : Screen("climate/{cycleId}") {
        fun createRoute(cycleId: Long) = "climate/$cycleId"
    }
    data object MapaTerreno : Screen("mapa/{terrenoId}") {
        fun createRoute(terrenoId: Long) = "mapa/$terrenoId"
    }
    data object SeleccionarUbicacion : Screen("ubicacion/seleccionar")
}

data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        screen = Screen.Dashboard,
        label = "Dashboard",
        selectedIcon = Icons.Filled.Dashboard,
        unselectedIcon = Icons.Outlined.Dashboard
    ),
    BottomNavItem(
        screen = Screen.Parcelas,
        label = "Parcelas",
        selectedIcon = Icons.Filled.Landscape,
        unselectedIcon = Icons.Outlined.Landscape
    ),
    BottomNavItem(
        screen = Screen.Historial,
        label = "Historial",
        selectedIcon = Icons.Filled.History,
        unselectedIcon = Icons.Outlined.History
    ),
    BottomNavItem(
        screen = Screen.Salud,
        label = "Salud",
        selectedIcon = Icons.Filled.HealthAndSafety,
        unselectedIcon = Icons.Outlined.HealthAndSafety
    ),
)
