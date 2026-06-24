package com.app.criba.presentation.state

import com.app.criba.domain.model.ClimateRecord

data class ClimateUiState(
    val records: List<ClimateRecord> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    // Form fields
    val rainfall: String = "",
    val temperature: String = "",
    val droughtStage: String = "NINGUNA",
    val isFormVisible: Boolean = false,
    val isSaved: Boolean = false
)
