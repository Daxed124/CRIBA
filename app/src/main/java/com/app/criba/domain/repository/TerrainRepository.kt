package com.app.criba.domain.repository

import com.app.criba.domain.model.Terrain
import kotlinx.coroutines.flow.Flow

interface TerrainRepository {
    fun getAllTerrains(): Flow<List<Terrain>>
    fun getTerrainsByUserId(userId: String): Flow<List<Terrain>>
    suspend fun getTerrainById(id: Long): Terrain?
    suspend fun insertTerrain(terrain: Terrain): Long
    suspend fun updateTerrain(terrain: Terrain)
    suspend fun deleteTerrain(id: Long)
}
