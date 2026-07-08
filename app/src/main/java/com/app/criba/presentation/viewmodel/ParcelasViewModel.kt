package com.app.criba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.criba.domain.model.CropCycle
import com.app.criba.domain.model.PhenologicalState
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

    /**
     * Cierra (cosecha) un ciclo: marca la fecha de fin de hoy, guarda el volumen cosechado
     * y lo pasa a estado FINALIZADO. Así aparece en el Historial y libera el terreno para uno nuevo.
     */
    fun cerrarCiclo(cycle: CropCycle, volumenKg: Double?) {
        viewModelScope.launch {
            try {
                cycleRepository.updateCycle(
                    cycle.copy(
                        endDate = System.currentTimeMillis(),
                        harvestedVolumeKg = volumenKg,
                        phenologicalState = PhenologicalState.FINALIZADO
                    )
                )
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    /**
     * Marca o edita el área de una parcela ya creada: recalcula la superficie
     * (hectáreas) desde el polígono y actualiza también su ubicación al centro.
     */
    fun actualizarArea(terrain: com.app.criba.domain.model.Terrain, puntos: List<com.google.android.gms.maps.model.LatLng>) {
        viewModelScope.launch {
            try {
                if (puntos.size < 3) return@launch
                val ha = com.app.criba.util.GeoUtils.areaHectares(puntos)
                val centro = com.app.criba.util.GeoUtils.centroid(puntos)
                terrainRepository.updateTerrain(
                    terrain.copy(
                        surface = ha,
                        polygon = com.app.criba.util.GeoUtils.polygonToString(puntos),
                        latitude = if (terrain.latitude != 0.0) terrain.latitude else centro.latitude,
                        longitude = if (terrain.longitude != 0.0) terrain.longitude else centro.longitude
                    )
                )
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
}
