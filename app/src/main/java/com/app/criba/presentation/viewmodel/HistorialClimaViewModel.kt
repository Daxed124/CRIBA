package com.app.criba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.criba.domain.model.ClimaResumen
import com.app.criba.domain.model.ClimateRecord
import com.app.criba.domain.model.CropCycle
import com.app.criba.domain.repository.ClimateRepository
import com.app.criba.domain.repository.CycleRepository
import com.app.criba.domain.usecase.GenerarResumenClimaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistorialClimaViewModel @Inject constructor(
    private val cycleRepository: CycleRepository,
    private val climateRepository: ClimateRepository,
    private val generarResumenClima: GenerarResumenClimaUseCase
) : ViewModel() {

    data class State(
        val cycles: List<CropCycle> = emptyList(),
        val selectedCycle: CropCycle? = null,
        val records: List<ClimateRecord> = emptyList(),
        val resumen: ClimaResumen = ClimaResumen(),
        val isLoading: Boolean = true
    )

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    private var recordsJob: Job? = null

    init {
        viewModelScope.launch {
            cycleRepository.getAllCycles().collect { cycles ->
                _state.update { it.copy(cycles = cycles, isLoading = false) }
                // Selecciona el primero por defecto si aún no hay selección
                if (_state.value.selectedCycle == null && cycles.isNotEmpty()) {
                    selectCycle(cycles.first())
                }
            }
        }
    }

    fun selectCycle(cycle: CropCycle) {
        _state.update { it.copy(selectedCycle = cycle) }
        recordsJob?.cancel()
        recordsJob = viewModelScope.launch {
            climateRepository.getClimateRecordsByCycleId(cycle.id).collect { records ->
                _state.update { it.copy(records = records, resumen = generarResumenClima(records)) }
            }
        }
    }
}
