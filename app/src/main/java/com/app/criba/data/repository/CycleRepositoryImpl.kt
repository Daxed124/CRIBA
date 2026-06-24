package com.app.criba.data.repository

import com.app.criba.data.local.dao.CycleDao
import com.app.criba.data.mapper.toDomain
import com.app.criba.data.mapper.toEntity
import com.app.criba.domain.model.CropCycle
import com.app.criba.domain.repository.CycleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CycleRepositoryImpl @Inject constructor(
    private val cycleDao: CycleDao
) : CycleRepository {

    override fun getAllCycles(): Flow<List<CropCycle>> {
        return cycleDao.getAllCycles().map { list -> list.map { it.toDomain() } }
    }

    override fun getCyclesByTerrainId(terrainId: Long): Flow<List<CropCycle>> {
        return cycleDao.getCyclesByTerrainId(terrainId).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getCycleById(id: Long): CropCycle? {
        return cycleDao.getCycleById(id)?.toDomain()
    }

    override suspend fun insertCycle(cycle: CropCycle): Long {
        return cycleDao.insert(cycle.toEntity().copy(isSynced = false))
    }

    override suspend fun updateCycle(cycle: CropCycle) {
        cycleDao.update(cycle.toEntity().copy(isSynced = false))
    }

    override suspend fun deleteCycle(id: Long) {
        cycleDao.delete(id)
    }
}
