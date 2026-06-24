package com.app.criba.presentation.state

import com.app.criba.domain.model.User

sealed class LoginUiState {
    data object Idle : LoginUiState()
    data object Loading : LoginUiState()
    data class Authenticated(val user: User) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}
