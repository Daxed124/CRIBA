package com.app.criba.presentation.state

import com.app.criba.domain.model.PestIncident

data class PestUiState(
    val pests: List<PestIncident> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    // Form fields
    val name: String = "",
    val severity: String = "BAJO",
    val description: String = "",
    val photoUri: String? = null,
    val isFormVisible: Boolean = false,
    val isSaved: Boolean = false
)
