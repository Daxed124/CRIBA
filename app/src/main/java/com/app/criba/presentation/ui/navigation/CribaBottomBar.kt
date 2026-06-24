package com.app.criba.presentation.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.criba.presentation.ui.components.CribaAccent
import com.app.criba.presentation.ui.components.CribaPrimary
import com.app.criba.presentation.ui.components.CribaSecondary

@Composable
fun CribaBottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = CribaPrimary,
        contentColor = Color.White
    ) {
        bottomNavItems.forEach { item ->
            val isSelected = currentRoute == item.screen.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.screen.route) {
                        navController.navigate(item.screen.route) {
                            // Pop up to the dashboard to avoid building up a stack
                            popUpTo(Screen.Dashboard.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(text = item.label)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    indicatorColor = CribaAccent,
                    unselectedIconColor = CribaSecondary.copy(alpha = 0.6f),
                    unselectedTextColor = CribaSecondary.copy(alpha = 0.6f)
                ),
                alwaysShowLabel = true
            )
        }
    }
}
