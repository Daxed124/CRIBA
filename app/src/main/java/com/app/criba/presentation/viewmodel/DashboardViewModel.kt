package com.app.criba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.criba.domain.repository.ClimateRepository
import com.app.criba.domain.repository.CycleRepository
import com.app.criba.domain.repository.PestRepository
import com.app.criba.domain.repository.TerrainRepository
import com.app.criba.domain.repository.TransactionRepository
import com.app.criba.domain.model.Severity
import com.app.criba.presentation.state.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val terrainRepository: TerrainRepository,
    private val cycleRepository: CycleRepository,
    private val climateRepository: ClimateRepository,
    private val transactionRepository: TransactionRepository,
    private val pestRepository: PestRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            try {
                _uiState.value = DashboardUiState.Loading
                
                // Offline-first approach: just get the first available terrain
                val terrains = terrainRepository.getAllTerrains().firstOrNull() ?: emptyList()
                val activeTerrain = terrains.firstOrNull()

                if (activeTerrain == null) {
                    _uiState.value = DashboardUiState.Success(null, null, emptyList(), null)
                    return@launch
                }

                // Obtener ciclos del terreno
                val cycles = cycleRepository.getCyclesByTerrainId(activeTerrain.id).firstOrNull() ?: emptyList()
                val sortedCycles = cycles.sortedByDescending { it.startDate }
                
                val activeCycle = sortedCycles.firstOrNull { it.endDate == null } ?: sortedCycles.firstOrNull()
                val history = sortedCycles.take(3)

                // Obtener clima actual del ciclo activo
                var currentClimate = if (activeCycle != null) {
                    val climates = climateRepository.getClimateRecordsByCycleId(activeCycle.id).firstOrNull() ?: emptyList()
                    climates.maxByOrNull { it.date }
                } else null

                var totalIncome = 0.0
                var totalExpense = 0.0
                var netProfit = 0.0
                var roi = 0.0
                var daysSincePlanting = 0
                var activePestsCount = 0
                var criticalPests = emptyList<com.app.criba.domain.model.PestIncident>()
                var aiSuggestion = "Sin sugerencias por ahora."

                if (activeCycle != null) {
                    totalIncome = transactionRepository.sumIncomeByCycleId(activeCycle.id)
                    totalExpense = transactionRepository.sumExpenseByCycleId(activeCycle.id)
                    netProfit = totalIncome - totalExpense
                    roi = if (totalExpense > 0) (netProfit / totalExpense) * 100 else 0.0

                    val diffMillis = System.currentTimeMillis() - activeCycle.startDate
                    daysSincePlanting = (diffMillis / (1000 * 60 * 60 * 24)).toInt()

                    val pests = pestRepository.getPestsByCycleId(activeCycle.id).firstOrNull() ?: emptyList()
                    activePestsCount = pests.size
                    criticalPests = pests.filter { it.severity == Severity.CRITICO }

                    aiSuggestion = if (criticalPests.isNotEmpty()) {
                        "ALERTA: Tienes ${criticalPests.size} plagas críticas. Aplica medidas correctivas inmediatamente."
                    } else if (currentClimate?.droughtStage == com.app.criba.domain.model.DroughtStage.EXTREMA || currentClimate?.droughtStage == com.app.criba.domain.model.DroughtStage.SEVERA) {
                        "ALERTA CLIMÁTICA: Sequía detectada. Incrementa el riego para evitar estrés hídrico."
                    } else if (daysSincePlanting > 30 && totalExpense == 0.0) {
                        "SUGERENCIA: No has registrado gastos en los últimos 30 días. Mantén tus finanzas al día."
                    } else {
                        "SUGERENCIA: Tu parcela está en buen estado. Continúa monitoreando el clima y las plagas."
                    }
                }

                _uiState.value = DashboardUiState.Success(
                    activeTerrain = activeTerrain,
                    activeCycle = activeCycle,
                    history = history,
                    currentClimate = currentClimate,
                    totalIncome = totalIncome,
                    totalExpense = totalExpense,
                    netProfit = netProfit,
                    roi = roi,
                    daysSincePlanting = daysSincePlanting,
                    activePestsCount = activePestsCount,
                    criticalPests = criticalPests,
                    aiSuggestion = aiSuggestion
                )

            } catch (e: Exception) {
                _uiState.value = DashboardUiState.Error(e.message ?: "Error al cargar datos del dashboard")
            }
        }
    }
}
