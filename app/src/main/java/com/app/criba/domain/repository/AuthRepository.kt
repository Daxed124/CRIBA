package com.app.criba.domain.repository

import com.app.criba.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, passwordHash: String): User
    suspend fun loginWithGoogle(email: String, displayName: String, photoUrl: String?): User
    suspend fun register(email: String, passwordHash: String, displayName: String): User
    suspend fun getCurrentUser(): User?
    suspend fun updateProfile(displayName: String, photoUrl: String?): User
    suspend fun signOut()
    suspend fun isLoggedIn(): Boolean
}
