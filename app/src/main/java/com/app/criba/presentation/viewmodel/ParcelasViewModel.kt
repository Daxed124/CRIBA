package com.app.criba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.criba.domain.model.CropCycle
import com.app.criba.domain.model.Terrain
import com.app.criba.domain.model.TerrainWithCycles
import com.app.criba.domain.repository.CycleRepository
import com.app.criba.domain.repository.TerrainRepository
import com.app.criba.presentation.state.ParcelasUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParcelasViewModel @Inject constructor(
    private val terrainRepository: TerrainRepository,
    private val cycleRepository: CycleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ParcelasUiState(isLoading = true))
    val uiState: StateFlow<ParcelasUiState> = _uiState.asStateFlow()

    init {
        cargarTerrenos()
    }

    private fun cargarTerrenos() {
        viewModelScope.launch {
            try {
                // Combine terrains and all cycles to create the list of TerrainWithCycles
                terrainRepository.getAllTerrains().combine(cycleRepository.getAllCycles()) { terrains, allCycles ->
                    terrains.map { terrain ->
                        val cyclesForTerrain = allCycles.filter { it.terrainId == terrain.id }
                        TerrainWithCycles(terrain, cyclesForTerrain.sortedByDescending { it.startDate })
                    }
                }.collect { list ->
                    _uiState.update { it.copy(terrenos = list, isLoading = false, error = null) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun toggleNuevoTerrenoSheet(show: Boolean) {
        _uiState.update { it.copy(isNuevoTerrenoSheetVisible = show) }
    }

    fun toggleNuevoCicloSheet(show: Boolean, terrenoId: Long? = null) {
        _uiState.update { it.copy(isNuevoCicloSheetVisible = show, terrenoSeleccionadoId = terrenoId) }
    }

    fun agregarTerreno(terrain: Terrain) {
        viewModelScope.launch {
            try {
                terrainRepository.insertTerrain(terrain)
                toggleNuevoTerrenoSheet(false)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun eliminarTerreno(id: Long) {
        viewModelScope.launch {
            try {
                terrainRepository.deleteTerrain(id)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun iniciarCiclo(cycle: CropCycle) {
        viewModelScope.launch {
            try {
                // Ensure there isn't an active cycle already
                val currentTerreno = _uiState.value.terrenos.find { it.terrain.id == cycle.terrainId }
                if (currentTerreno?.activeCycle != null) {
                    _uiState.update { it.copy(error = "Ya existe un ciclo activo para este terreno.") }
                    return@launch
                }
                
                cycleRepository.insertCycle(cycle)
                toggleNuevoCicloSheet(false)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}
