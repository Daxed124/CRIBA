package com.app.criba.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.criba.domain.model.ExpenseCategory
import com.app.criba.domain.model.Transaction
import com.app.criba.domain.model.TransactionType
import com.app.criba.domain.repository.CycleRepository
import com.app.criba.domain.repository.TerrainRepository
import com.app.criba.domain.repository.TransactionRepository
import com.app.criba.domain.usecase.AddTransactionUseCase
import com.app.criba.domain.usecase.GenerarResumenFinancieroUseCase
import com.app.criba.presentation.state.FinanceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinanceViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val generarResumen: GenerarResumenFinancieroUseCase,
    private val cycleRepository: CycleRepository,
    private val terrainRepository: TerrainRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // El arg de navegación llega como texto; convertir a Long de forma segura (evita ClassCastException)
    private val cycleId: Long = savedStateHandle.get<String>("cycleId")?.toLongOrNull() ?: 0L

    // Datos para el cálculo de Erend (eficiencia de rendimiento)
    private var volumenKg: Double? = null
    private var hectareas: Double? = null

    private val _uiState = MutableStateFlow(FinanceUiState())
    val uiState: StateFlow<FinanceUiState> = _uiState.asStateFlow()

    init {
        loadCycleInfo()
        loadTransactions()
    }

    private fun loadCycleInfo() {
        viewModelScope.launch {
            val cycle = cycleRepository.getCycleById(cycleId)
            volumenKg = cycle?.harvestedVolumeKg
            hectareas = cycle?.terrainId?.let { terrainRepository.getTerrainById(it)?.surface }
            // Recalcula el resumen con las hectáreas/volumen ya disponibles
            _uiState.update {
                it.copy(resumen = generarResumen(it.transactions, volumenKg, hectareas))
            }
        }
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            transactionRepository.getTransactionsByCycleId(cycleId)
                .catch { e -> _uiState.update { it.copy(isLoading = false, errorMessage = e.message) } }
                .collect { txs ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            transactions = txs,
                            resumen = generarResumen(txs, volumenKg, hectareas)
                        )
                    }
                }
        }
    }

    fun onTypeChange(isIncome: Boolean) { _uiState.update { it.copy(isIncome = isIncome) } }
    fun onAmountChange(amount: String) { _uiState.update { it.copy(amount = amount) } }
    fun onCategoryChange(category: String) { _uiState.update { it.copy(category = category) } }
    fun onDescriptionChange(desc: String) { _uiState.update { it.copy(description = desc) } }
    fun toggleForm() { _uiState.update { it.copy(isFormVisible = !it.isFormVisible, isSaved = false) } }

    fun saveTransaction() {
        val state = _uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val type = if (state.isIncome) TransactionType.INGRESO else TransactionType.GASTO
            val category = if (!state.isIncome && state.category.isNotBlank()) {
                try { ExpenseCategory.valueOf(state.category) } catch (_: Exception) { null }
            } else null

            val transaction = Transaction(
                cycleId = cycleId,
                type = type,
                amount = state.amount.toDoubleOrNull() ?: 0.0,
                category = category,
                description = state.description,
                date = System.currentTimeMillis()
            )
            val result = addTransactionUseCase(transaction)
            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false, isSaved = true, isFormVisible = false,
                        amount = "", description = "", category = "") }
                },
                onFailure = { e -> _uiState.update { it.copy(isLoading = false, errorMessage = e.message) } }
            )
        }
    }
}
