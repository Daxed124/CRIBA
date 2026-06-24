package com.app.criba.domain.usecase

import com.app.criba.domain.repository.SyncRepository
import javax.inject.Inject

class SyncDataUseCase @Inject constructor(
    private val syncRepository: SyncRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return syncRepository.syncAllPendingData()
    }

    suspend fun hasPending(): Boolean {
        return syncRepository.hasPendingSync()
    }
}
