package com.app.criba.presentation.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.criba.presentation.state.DashboardUiState
import com.app.criba.presentation.viewmodel.DashboardViewModel
import com.app.criba.presentation.ui.dashboard.components.*

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onNavigateToParcela: (Long) -> Unit,
    onNavigateToMapa: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState is DashboardUiState.Loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else if (uiState is DashboardUiState.Error) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = (uiState as DashboardUiState.Error).message, 
                color = MaterialTheme.colorScheme.error
            )
        }
    } else if (uiState is DashboardUiState.Success) {
        val state = uiState as DashboardUiState.Success
        
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F7F2)), // Fondo crema neutro CRIBA
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { 
                WeatherCard(climate = state.currentClimate) 
            }
            item { 
                ParcelaResumenCard(
                    cycle = state.activeCycle,
                    daysSincePlanting = state.daysSincePlanting,
                    onVerDetalle = { 
                        if (state.activeTerrain != null) {
                            onNavigateToParcela(state.activeTerrain.id)
                        }
                    }
                ) 
            }
            item { 
                if (state.aiSuggestion.isNotEmpty()) {
                    SugerenciaIACard(texto = state.aiSuggestion)
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MapaMiniaturaCard(
                        nombreSector = state.activeTerrain?.name ?: "Sin Parcela",
                        onVerMapa = {
                            if (state.activeTerrain != null) {
                                onNavigateToMapa(state.activeTerrain.id)
                            } else {
                                onNavigateToMapa(0L) // o cualquier id para crear
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                    FinanzasResumenCard(
                        roi = state.roi,
                        netProfit = state.netProfit,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item { 
                HistorialPlantacionesCard(history = state.history) 
            }
        }
    }
}
