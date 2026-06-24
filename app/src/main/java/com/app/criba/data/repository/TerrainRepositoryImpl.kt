package com.app.criba.data.repository

import com.app.criba.data.local.dao.TerrainDao
import com.app.criba.data.mapper.toDomain
import com.app.criba.data.mapper.toEntity
import com.app.criba.domain.model.Terrain
import com.app.criba.domain.repository.TerrainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TerrainRepositoryImpl @Inject constructor(
    private val terrainDao: TerrainDao
) : TerrainRepository {

    override fun getAllTerrains(): Flow<List<Terrain>> {
        return terrainDao.getAllTerrains().map { list -> list.map { it.toDomain() } }
    }

    override fun getTerrainsByUserId(userId: String): Flow<List<Terrain>> {
        return terrainDao.getTerrainsByUserId(userId).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getTerrainById(id: Long): Terrain? {
        return terrainDao.getTerrainById(id)?.toDomain()
    }

    override suspend fun insertTerrain(terrain: Terrain): Long {
        return terrainDao.insert(terrain.toEntity().copy(isSynced = false))
    }

    override suspend fun updateTerrain(terrain: Terrain) {
        terrainDao.update(terrain.toEntity().copy(isSynced = false))
    }

    override suspend fun deleteTerrain(id: Long) {
        terrainDao.delete(id)
    }
}
