package com.app.criba.domain.repository

interface SyncRepository {
    suspend fun syncAllPendingData(): Result<Unit>
    suspend fun hasPendingSync(): Boolean
}
