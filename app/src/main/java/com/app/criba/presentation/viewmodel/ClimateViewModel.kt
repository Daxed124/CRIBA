package com.app.criba.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.criba.domain.model.ClimateRecord
import com.app.criba.domain.model.DroughtStage
import com.app.criba.domain.repository.ClimateRepository
import com.app.criba.domain.usecase.GenerarResumenClimaUseCase
import com.app.criba.domain.usecase.RecordClimateUseCase
import com.app.criba.presentation.state.ClimateUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClimateViewModel @Inject constructor(
    private val climateRepository: ClimateRepository,
    private val recordClimateUseCase: RecordClimateUseCase,
    private val generarResumenClima: GenerarResumenClimaUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // El arg de navegación llega como texto; convertir a Long de forma segura (evita ClassCastException)
    private val cycleId: Long = savedStateHandle.get<String>("cycleId")?.toLongOrNull() ?: 0L

    private val _uiState = MutableStateFlow(ClimateUiState())
    val uiState: StateFlow<ClimateUiState> = _uiState.asStateFlow()

    private var recordsJob: Job? = null

    init { loadRecords() }

    /** Vuelve a cargar los registros del ciclo (botón de actualizar). */
    fun refresh() { loadRecords() }

    private fun loadRecords() {
        recordsJob?.cancel()
        recordsJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            climateRepository.getClimateRecordsByCycleId(cycleId)
                .catch { e -> _uiState.update { it.copy(isLoading = false, errorMessage = e.message) } }
                .collect { records ->
                    _uiState.update {
                        it.copy(isLoading = false, records = records, resumen = generarResumenClima(records))
                    }
                }
        }
    }

    fun onRainfallChange(value: String) { _uiState.update { it.copy(rainfall = value) } }
    fun onTemperatureChange(value: String) { _uiState.update { it.copy(temperature = value) } }
    fun onDroughtStageChange(stage: String) { _uiState.update { it.copy(droughtStage = stage) } }
    fun toggleForm() { _uiState.update { it.copy(isFormVisible = !it.isFormVisible, isSaved = false) } }

    fun saveRecord() {
        val state = _uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val record = ClimateRecord(
                cycleId = cycleId,
                // Acepta coma o punto decimal (el teclado en español escribe coma)
                rainfall = state.rainfall.replace(',', '.').toDoubleOrNull() ?: 0.0,
                temperature = state.temperature.replace(',', '.').toDoubleOrNull() ?: 0.0,
                droughtStage = DroughtStage.valueOf(state.droughtStage),
                date = System.currentTimeMillis()
            )
            val result = recordClimateUseCase(record)
            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false, isSaved = true, isFormVisible = false,
                        rainfall = "", temperature = "", droughtStage = "NINGUNA") }
                },
                onFailure = { e -> _uiState.update { it.copy(isLoading = false, errorMessage = e.message) } }
            )
        }
    }
}
