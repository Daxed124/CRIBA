package com.app.criba.domain.repository

import com.app.criba.domain.model.CropCycle
import kotlinx.coroutines.flow.Flow

interface CycleRepository {
    fun getCyclesByTerrainId(terrainId: Long): Flow<List<CropCycle>>
    suspend fun getCycleById(id: Long): CropCycle?
    suspend fun insertCycle(cycle: CropCycle): Long
    suspend fun updateCycle(cycle: CropCycle)
    suspend fun deleteCycle(id: Long)
}
