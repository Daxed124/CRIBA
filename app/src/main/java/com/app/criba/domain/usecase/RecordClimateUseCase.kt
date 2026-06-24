package com.app.criba.domain.usecase

import com.app.criba.domain.model.ClimateRecord
import com.app.criba.domain.repository.ClimateRepository
import javax.inject.Inject

class RecordClimateUseCase @Inject constructor(
    private val climateRepository: ClimateRepository
) {
    suspend operator fun invoke(record: ClimateRecord): Result<Long> {
        return try {
            val id = climateRepository.insertClimateRecord(record)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
