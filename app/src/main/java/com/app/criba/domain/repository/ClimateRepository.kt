package com.app.criba.domain.repository

import com.app.criba.domain.model.ClimateRecord
import kotlinx.coroutines.flow.Flow

interface ClimateRepository {
    fun getClimateRecordsByCycleId(cycleId: Long): Flow<List<ClimateRecord>>
    suspend fun insertClimateRecord(record: ClimateRecord): Long
    suspend fun updateClimateRecord(record: ClimateRecord)
    suspend fun deleteClimateRecord(id: Long)
}
