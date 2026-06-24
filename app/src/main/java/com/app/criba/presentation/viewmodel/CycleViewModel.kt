package com.app.criba.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.criba.domain.model.CropCycle
import com.app.criba.domain.model.PhenologicalState
import com.app.criba.domain.repository.CycleRepository
import com.app.criba.domain.usecase.CreateCycleUseCase
import com.app.criba.domain.usecase.UpdateCycleStateUseCase
import com.app.criba.presentation.state.CycleUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CycleViewModel @Inject constructor(
    private val cycleRepository: CycleRepository,
    private val createCycleUseCase: CreateCycleUseCase,
    private val updateCycleStateUseCase: UpdateCycleStateUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val terrainId: Long = savedStateHandle.get<Long>("terrainId") ?: 0L

    private val _uiState = MutableStateFlow(CycleUiState())
    val uiState: StateFlow<CycleUiState> = _uiState.asStateFlow()

    init {
        loadCycles()
    }

    private fun loadCycles() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            cycleRepository.getCyclesByTerrainId(terrainId)
                .catch { e -> _uiState.update { it.copy(isLoading = false, errorMessage = e.message) } }
                .collect { cycles -> _uiState.update { it.copy(isLoading = false, cycles = cycles) } }
        }
    }

    fun onSpeciesChange(species: String) { _uiState.update { it.copy(species = species) } }
    fun onStartDateChange(date: Long) { _uiState.update { it.copy(startDate = date) } }
    fun onEndDateChange(date: Long?) { _uiState.update { it.copy(endDate = date) } }
    fun onStateChange(state: String) { _uiState.update { it.copy(selectedState = state) } }
    fun toggleForm() { _uiState.update { it.copy(isFormVisible = !it.isFormVisible, isSaved = false) } }

    fun saveCycle() {
        val state = _uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val cycle = CropCycle(
                terrainId = terrainId,
                species = state.species,
                startDate = state.startDate,
                endDate = state.endDate,
                phenologicalState = PhenologicalState.valueOf(state.selectedState)
            )
            val result = createCycleUseCase(cycle)
            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false, isSaved = true, isFormVisible = false,
                        species = "", selectedState = "SIEMBRA") }
                },
                onFailure = { e -> _uiState.update { it.copy(isLoading = false, errorMessage = e.message) } }
            )
        }
    }

    fun updateState(cycleId: Long, newState: PhenologicalState) {
        viewModelScope.launch {
            updateCycleStateUseCase(cycleId, newState)
        }
    }
}
