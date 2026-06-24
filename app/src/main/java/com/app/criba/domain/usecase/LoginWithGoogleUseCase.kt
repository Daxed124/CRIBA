package com.app.criba.domain.usecase

import com.app.criba.domain.model.User
import com.app.criba.domain.repository.AuthRepository
import javax.inject.Inject

class LoginWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, passwordHash: String): Result<User> {
        return try {
            val user = authRepository.login(email, passwordHash)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

