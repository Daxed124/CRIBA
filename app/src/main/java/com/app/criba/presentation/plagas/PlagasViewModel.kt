package com.app.criba.presentation.plagas

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.criba.domain.model.PestIncident
import com.app.criba.domain.model.Severity
import com.app.criba.domain.repository.PestRepository
import com.app.criba.util.LocationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlagasViewModel @Inject constructor(
    private val plagasRepo: PestRepository,
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
