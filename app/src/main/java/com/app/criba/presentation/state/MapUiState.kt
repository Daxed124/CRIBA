package com.app.criba.presentation.state

import com.app.criba.domain.model.Terrain

sealed class MapUiState {
    data object Loading : MapUiState()
    data class Success(val terrains: List<Terrain>) : MapUiState()
    data class Error(val message: String) : MapUiState()
}
