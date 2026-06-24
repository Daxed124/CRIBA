package com.app.criba.domain.usecase

import com.app.criba.domain.model.PestIncident
import com.app.criba.domain.repository.PestRepository
import javax.inject.Inject

class ReportPestUseCase @Inject constructor(
    private val pestRepository: PestRepository
) {
    suspend operator fun invoke(pest: PestIncident): Result<Long> {
        return try {
            if (pest.name.isBlank()) {
                return Result.failure(IllegalArgumentException("El nombre de la plaga es obligatorio"))
            }
            val id = pestRepository.insertPest(pest)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
