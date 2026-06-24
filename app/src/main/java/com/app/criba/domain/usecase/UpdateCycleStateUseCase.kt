package com.app.criba.domain.usecase

import com.app.criba.domain.model.CropCycle
import com.app.criba.domain.model.PhenologicalState
import com.app.criba.domain.repository.CycleRepository
import javax.inject.Inject

class UpdateCycleStateUseCase @Inject constructor(
    private val cycleRepository: CycleRepository
) {
    suspend operator fun invoke(cycleId: Long, newState: PhenologicalState): Result<Unit> {
        return try {
            val cycle = cycleRepository.getCycleById(cycleId)
                ?: return Result.failure(IllegalArgumentException("Ciclo no encontrado"))
            val updated = cycle.copy(phenologicalState = newState, isSynced = false)
            cycleRepository.updateCycle(updated)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
