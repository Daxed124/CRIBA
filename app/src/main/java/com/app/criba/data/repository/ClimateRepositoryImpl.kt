package com.app.criba.data.repository

import com.app.criba.data.local.dao.ClimateDao
import com.app.criba.data.mapper.toDomain
import com.app.criba.data.mapper.toEntity
import com.app.criba.domain.model.ClimateRecord
import com.app.criba.domain.repository.ClimateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClimateRepositoryImpl @Inject constructor(
    private val climateDao: ClimateDao
) : ClimateRepository {

    override fun getClimateRecordsByCycleId(cycleId: Long): Flow<List<ClimateRecord>> {
        return climateDao.getClimateRecordsByCycleId(cycleId).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun insertClimateRecord(record: ClimateRecord): Long {
        return climateDao.insert(record.toEntity().copy(isSynced = false))
    }

    override suspend fun updateClimateRecord(record: ClimateRecord) {
        climateDao.update(record.toEntity().copy(isSynced = false))
    }

    override suspend fun deleteClimateRecord(id: Long) {
        climateDao.delete(id)
    }
}
