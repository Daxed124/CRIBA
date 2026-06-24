package com.app.criba.data.repository

import com.app.criba.data.local.dao.PestDao
import com.app.criba.data.mapper.toDomain
import com.app.criba.data.mapper.toEntity
import com.app.criba.domain.model.PestIncident
import com.app.criba.domain.repository.PestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PestRepositoryImpl @Inject constructor(
    private val pestDao: PestDao
) : PestRepository {

    override fun getPestsByCycleId(cycleId: Long): Flow<List<PestIncident>> {
        return pestDao.getPestsByCycleId(cycleId).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun insertPest(pest: PestIncident): Long {
        return pestDao.insert(pest.toEntity().copy(isSynced = false))
    }

    override suspend fun updatePest(pest: PestIncident) {
        pestDao.update(pest.toEntity().copy(isSynced = false))
    }

    override suspend fun deletePest(id: Long) {
        pestDao.delete(id)
    }
}
