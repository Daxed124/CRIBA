package com.app.criba.domain.usecase

import com.app.criba.domain.model.Terrain
import com.app.criba.domain.repository.TerrainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTerrainsUseCase @Inject constructor(
    private val terrainRepository: TerrainRepository
) {
    operator fun invoke(): Flow<List<Terrain>> {
        return terrainRepository.getAllTerrains()
    }

    fun byUserId(userId: String): Flow<List<Terrain>> {
        return terrainRepository.getTerrainsByUserId(userId)
    }
}
