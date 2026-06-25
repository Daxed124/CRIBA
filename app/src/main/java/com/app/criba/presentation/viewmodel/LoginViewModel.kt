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
        val emailTrimmed = email.trim()

        // Validación obligatoria: no permitir acceso con campos vacíos o inválidos
        if (emailTrimmed.isBlank() || passwordHash.isBlank()) {
            _uiState.value = LoginUiState.Error("Ingresa tu correo y contraseña.")
            return
        }
        if (!isValidEmail(emailTrimmed)) {
            _uiState.value = LoginUiState.Error("Ingresa un correo electrónico válido.")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                val user = authRepository.login(emailTrimmed, passwordHash)
                _uiState.value = LoginUiState.Authenticated(user)
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(e.message ?: "Error al iniciar sesión")
            }
        }
    }

    fun register(email: String, passwordHash: String, displayName: String) {
        val emailTrimmed = email.trim()
        val nameTrimmed = displayName.trim()

        // Validación obligatoria: todos los campos son requeridos para crear la cuenta
        if (nameTrimmed.isBlank() || emailTrimmed.isBlank() || passwordHash.isBlank()) {
            _uiState.value = LoginUiState.Error("Completa todos los campos para registrarte.")
            return
        }
        if (!isValidEmail(emailTrimmed)) {
            _uiState.value = LoginUiState.Error("Ingresa un correo electrónico válido.")
            return
        }
        if (passwordHash.length < 6) {
            _uiState.value = LoginUiState.Error("La contraseña debe tener al menos 6 caracteres.")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                val user = authRepository.register(emailTrimmed, passwordHash, nameTrimmed)
                _uiState.value = LoginUiState.Authenticated(user)
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(e.message ?: "Error al registrarse")
            }
        }
    }

    private fun isValidEmail(email: String): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

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

