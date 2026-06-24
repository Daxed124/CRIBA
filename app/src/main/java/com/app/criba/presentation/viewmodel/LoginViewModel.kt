package com.app.criba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.criba.domain.repository.AuthRepository
import com.app.criba.presentation.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        checkExistingSession()
    }

    private fun checkExistingSession() {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            if (user != null) {
                _uiState.value = LoginUiState.Authenticated(user)
            }
        }
    }

    fun login(email: String, passwordHash: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                val user = authRepository.login(email, passwordHash)
                _uiState.value = LoginUiState.Authenticated(user)
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(e.message ?: "Error al iniciar sesión")
            }
        }
    }

    fun register(email: String, passwordHash: String, displayName: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                val user = authRepository.register(email, passwordHash, displayName)
                _uiState.value = LoginUiState.Authenticated(user)
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(e.message ?: "Error al registrarse")
            }
        }
    }

    fun onSignInError(message: String) {
        _uiState.value = LoginUiState.Error(message)
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            _uiState.value = LoginUiState.Idle
        }
    }
}

