package com.app.criba.presentation.state

import com.app.criba.domain.model.ResumenFinanciero
import com.app.criba.domain.model.Transaction

data class FinanceUiState(
    val transactions: List<Transaction> = emptyList(),
    val resumen: ResumenFinanciero = ResumenFinanciero(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    // Form fields
    val isIncome: Boolean = true,
    val amount: String = "",
    val category: String = "",
    val description: String = "",
    val isFormVisible: Boolean = false,
    val isSaved: Boolean = false
)
