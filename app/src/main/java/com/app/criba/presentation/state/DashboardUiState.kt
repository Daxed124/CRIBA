package com.app.criba.presentation.state

import com.app.criba.domain.model.ClimateRecord
import com.app.criba.domain.model.CropCycle
import com.app.criba.domain.model.Terrain

sealed class DashboardUiState {
    data object Loading : DashboardUiState()
    data class Success(
        val activeTerrain: Terrain?,
        val activeCycle: CropCycle?,
        val history: List<CropCycle>,
        val currentClimate: ClimateRecord?,
        val totalIncome: Double = 0.0,
        val totalExpense: Double = 0.0,
        val netProfit: Double = 0.0,
        val roi: Double = 0.0,
        val daysSincePlanting: Int = 0,
        val activePestsCount: Int = 0,
        val criticalPests: List<com.app.criba.domain.model.PestIncident> = emptyList(),
        val aiSuggestion: String = ""
    ) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}
