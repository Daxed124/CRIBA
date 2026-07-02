package com.app.criba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.criba.domain.model.User
import com.app.criba.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    init { loadUser() }

    fun refresh() = loadUser()

    private fun loadUser() {
        viewModelScope.launch {
            _user.update { authRepository.getCurrentUser() }
        }
    }

    fun updateProfile(displayName: String, photoUrl: String?) {
        viewModelScope.launch {
            try {
                val updated = authRepository.updateProfile(displayName, photoUrl)
                _user.update { updated }
            } catch (_: Exception) { }
        }
    }

    fun signOut(onDone: () -> Unit) {
        viewModelScope.launch {
            authRepository.signOut()
            onDone()
        }
    }
}
