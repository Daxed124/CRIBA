package com.app.criba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.criba.domain.usecase.GetTerrainsUseCase
import com.app.criba.presentation.state.MapUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getTerrainsUseCase: GetTerrainsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MapUiState>(MapUiState.Loading)
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    init {
        loadTerrains()
    }

    private fun loadTerrains() {
        viewModelScope.launch {
            getTerrainsUseCase()
                .catch { e ->
                    _uiState.value = MapUiState.Error(e.message ?: "Error cargando terrenos")
                }
                .collect { terrains ->
                    _uiState.value = MapUiState.Success(terrains)
                }
        }
    }
}
