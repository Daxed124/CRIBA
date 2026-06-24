package com.app.criba.domain.usecase

import com.app.criba.domain.model.CropCycle
import com.app.criba.domain.repository.CycleRepository
import javax.inject.Inject

class CreateCycleUseCase @Inject constructor(
    private val cycleRepository: CycleRepository
) {
    suspend operator fun invoke(cycle: CropCycle): Result<Long> {
        return try {
            if (cycle.species.isBlank()) {
                return Result.failure(IllegalArgumentException("La especie es obligatoria"))
            }
            val id = cycleRepository.insertCycle(cycle)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
