package com.app.criba.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.criba.domain.model.PestIncident
import com.app.criba.domain.model.Severity
import com.app.criba.domain.repository.PestRepository
import com.app.criba.domain.usecase.ReportPestUseCase
import com.app.criba.presentation.state.PestUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PestViewModel @Inject constructor(
    private val pestRepository: PestRepository,
    private val reportPestUseCase: ReportPestUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val cycleId: Long = savedStateHandle.get<Long>("cycleId") ?: 0L

    private val _uiState = MutableStateFlow(PestUiState())
    val uiState: StateFlow<PestUiState> = _uiState.asStateFlow()

    init { loadPests() }

    private fun loadPests() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            pestRepository.getPestsByCycleId(cycleId)
                .catch { e -> _uiState.update { it.copy(isLoading = false, errorMessage = e.message) } }
                .collect { pests -> _uiState.update { it.copy(isLoading = false, pests = pests) } }
        }
    }

    fun onNameChange(name: String) { _uiState.update { it.copy(name = name) } }
    fun onSeverityChange(severity: String) { _uiState.update { it.copy(severity = severity) } }
    fun onDescriptionChange(desc: String) { _uiState.update { it.copy(description = desc) } }
    fun onPhotoTaken(uri: String) { _uiState.update { it.copy(photoUri = uri) } }
    fun toggleForm() { _uiState.update { it.copy(isFormVisible = !it.isFormVisible, isSaved = false) } }

    fun savePest() {
        val state = _uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val pest = PestIncident(
                cycleId = cycleId,
                name = state.name,
                severity = Severity.valueOf(state.severity),
                description = state.description,
                photoUri = state.photoUri,
                date = System.currentTimeMillis()
            )
            val result = reportPestUseCase(pest)
            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false, isSaved = true, isFormVisible = false,
                        name = "", description = "", severity = "BAJO", photoUri = null) }
                },
                onFailure = { e -> _uiState.update { it.copy(isLoading = false, errorMessage = e.message) } }
            )
        }
    }
}
