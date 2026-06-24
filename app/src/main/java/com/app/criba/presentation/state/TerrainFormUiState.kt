package com.app.criba.presentation.state

data class TerrainFormUiState(
    val name: String = "",
    val surface: String = "",
    val soilType: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null
)
