package com.app.criba.presentation.state

import com.app.criba.domain.model.TerrainWithCycles

data class ParcelasUiState(
    val terrenos: List<TerrainWithCycles> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isNuevoTerrenoSheetVisible: Boolean = false,
    val isNuevoCicloSheetVisible: Boolean = false,
    val terrenoSeleccionadoId: Long? = null
)
