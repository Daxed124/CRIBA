package com.app.criba.presentation.state

import com.app.criba.domain.model.CropCycle

data class CycleUiState(
    val cycles: List<CropCycle> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    // Form fields
    val species: String = "",
    val startDate: Long = System.currentTimeMillis(),
    val endDate: Long? = null,
    val selectedState: String = "SIEMBRA",
    val isFormVisible: Boolean = false,
    val isSaved: Boolean = false
)
