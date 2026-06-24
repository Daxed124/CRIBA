package com.app.criba.presentation.plagas

import com.app.criba.domain.model.PestIncident

data class PlagasUiState(
    val cicloId: Long = -1,
    val plagas: List<PestIncident> = emptyList(),
    val healthScore: Int = 100,
    val isLoading: Boolean = true,
    val currentLocation: Pair<Double, Double>? = null,
    val isLocationLoading: Boolean = false,
    val error: String? = null
)
