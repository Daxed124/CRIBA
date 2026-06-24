package com.app.criba.data.repository

import com.app.criba.data.local.dao.*
import com.app.criba.data.remote.ApiService
import com.app.criba.domain.repository.SyncRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val terrainDao: TerrainDao,
    private val cycleDao: CycleDao,
    private val transactionDao: TransactionDao,
    private val pestDao: PestDao,
    private val climateDao: ClimateDao
) : SyncRepository {

    override suspend fun syncAllPendingData(): Result<Unit> {
        return try {
            // Sync terrains
            val unsyncedTerrains = terrainDao.getUnsyncedTerrains()
            for (terrain in unsyncedTerrains) {
                val response = apiService.syncTerrain(terrain)
                if (response.isSuccessful) {
                    terrainDao.markAsSynced(terrain.id)
                }
            }

            // Sync cycles
            val unsyncedCycles = cycleDao.getUnsyncedCycles()
            for (cycle in unsyncedCycles) {
                val response = apiService.syncCycle(cycle)
                if (response.isSuccessful) {
                    cycleDao.markAsSynced(cycle.id)
                }
            }

            // Sync transactions
            val unsyncedTransactions = transactionDao.getUnsyncedTransactions()
            for (transaction in unsyncedTransactions) {
                val response = apiService.syncTransaction(transaction)
                if (response.isSuccessful) {
                    transactionDao.markAsSynced(transaction.id)
                }
            }

            // Sync pests
            val unsyncedPests = pestDao.getUnsyncedPests()
            for (pest in unsyncedPests) {
                val response = apiService.syncPest(pest)
                if (response.isSuccessful) {
                    pestDao.markAsSynced(pest.id)
                }
            }

            // Sync climate records
            val unsyncedClimate = climateDao.getUnsyncedRecords()
            for (record in unsyncedClimate) {
                val response = apiService.syncClimateRecord(record)
                if (response.isSuccessful) {
                    climateDao.markAsSynced(record.id)
                }
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun hasPendingSync(): Boolean {
        return terrainDao.getUnsyncedTerrains().isNotEmpty() ||
                cycleDao.getUnsyncedCycles().isNotEmpty() ||
                transactionDao.getUnsyncedTransactions().isNotEmpty() ||
                pestDao.getUnsyncedPests().isNotEmpty() ||
                climateDao.getUnsyncedRecords().isNotEmpty()
    }
}
