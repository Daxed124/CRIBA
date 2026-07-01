package com.app.criba.presentation.plagas

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.criba.domain.model.PestIncident
import com.app.criba.domain.model.Severity
import com.app.criba.domain.repository.CycleRepository
import com.app.criba.domain.repository.PestRepository
import com.app.criba.util.LocationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlagasViewModel @Inject constructor(
    private val plagasRepo: PestRepository,
    private val cycleRepository: CycleRepository,
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlagasUiState())
    val uiState: StateFlow<PlagasUiState> = _uiState.asStateFlow()

    fun cargarPlagas(idCiclo: Long) {
        _uiState.update { it.copy(cicloId = idCiclo, isLoading = true) }
        viewModelScope.launch {
            try {
                plagasRepo.getPestsByCycleId(idCiclo).collect { list ->
                    val score = calcularScoreSalud(list)
                    _uiState.update { it.copy(plagas = list, healthScore = score, isLoading = false) }
                }
            } catch(e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    /**
     * Carga la salud consolidada: agrega las plagas de TODOS los ciclos activos.
     * Usado por la pestaña "Salud" del menú inferior (sin ciclo específico).
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun cargarSaludGlobal() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            cycleRepository.getAllCycles()
                .flatMapLatest { cycles ->
                    val activeIds = cycles.filter { it.endDate == null }.map { it.id }
                    if (activeIds.isEmpty()) {
                        flowOf(emptyList())
                    } else {
                        combine(activeIds.map { plagasRepo.getPestsByCycleId(it) }) { arr ->
                            arr.toList().flatten()
                        }
                    }
                }
                .catch { e -> _uiState.update { it.copy(error = e.message, isLoading = false) } }
                .collect { pests ->
                    _uiState.update {
                        it.copy(plagas = pests, healthScore = calcularScoreSalud(pests), isLoading = false)
                    }
                }
        }
    }

    fun registrarPlaga(plaga: PestIncident, fotoUri: Uri?) {
        viewModelScope.launch {
            try {
                val plagaToInsert = plaga.copy(photoUri = fotoUri?.toString())
                plagasRepo.insertPest(plagaToInsert)
            } catch(e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun eliminarPlaga(id: Long) {
        viewModelScope.launch {
            try {
                plagasRepo.deletePest(id)
            } catch(e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun obtenerUbicacionActual() {
        _uiState.update { it.copy(isLocationLoading = true) }
        viewModelScope.launch {
            val location = locationHelper.getCurrentLocation() ?: locationHelper.getLastKnownLocation()
            _uiState.update { it.copy(currentLocation = location, isLocationLoading = false) }
        }
    }

    fun clearLocation() {
        _uiState.update { it.copy(currentLocation = null) }
    }

    private fun calcularScoreSalud(plagas: List<PestIncident>): Int {
        val score = 100 - plagas.sumOf {
            val deduct: Int = when(it.severity) {
                Severity.CRITICO -> 30
                Severity.MEDIO -> 10
                Severity.BAJO -> 3
            }
            deduct
        }
        return score.coerceAtLeast(0)
    }
}
