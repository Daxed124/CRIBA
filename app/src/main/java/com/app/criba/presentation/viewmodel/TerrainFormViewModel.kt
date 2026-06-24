package com.app.criba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.criba.domain.model.Terrain
import com.app.criba.domain.repository.AuthRepository
import com.app.criba.domain.usecase.CreateTerrainUseCase
import com.app.criba.presentation.state.TerrainFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TerrainFormViewModel @Inject constructor(
    private val createTerrainUseCase: CreateTerrainUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TerrainFormUiState())
    val uiState: StateFlow<TerrainFormUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) { _uiState.update { it.copy(name = name) } }
    fun onSurfaceChange(surface: String) { _uiState.update { it.copy(surface = surface) } }
    fun onSoilTypeChange(soilType: String) { _uiState.update { it.copy(soilType = soilType) } }
    fun onLatitudeChange(lat: String) { _uiState.update { it.copy(latitude = lat) } }
    fun onLongitudeChange(lng: String) { _uiState.update { it.copy(longitude = lng) } }

    fun saveTerrain() {
        val state = _uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val user = authRepository.getCurrentUser()
            if (user == null) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "No hay sesión activa") }
                return@launch
            }
            val terrain = Terrain(
                name = state.name,
                surface = state.surface.toDoubleOrNull() ?: 0.0,
                soilType = state.soilType,
                latitude = state.latitude.toDoubleOrNull() ?: 0.0,
                longitude = state.longitude.toDoubleOrNull() ?: 0.0,
                userId = user.id
            )
            val result = createTerrainUseCase(terrain)
            result.fold(
                onSuccess = { _uiState.update { it.copy(isLoading = false, isSaved = true) } },
                onFailure = { e -> _uiState.update { it.copy(isLoading = false, errorMessage = e.message) } }
            )
        }
    }

    fun resetForm() {
        _uiState.value = TerrainFormUiState()
    }
}
