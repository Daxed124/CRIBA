package com.app.criba.domain.usecase

import com.app.criba.domain.model.Terrain
import com.app.criba.domain.repository.TerrainRepository
import javax.inject.Inject

class CreateTerrainUseCase @Inject constructor(
    private val terrainRepository: TerrainRepository
) {
    suspend operator fun invoke(terrain: Terrain): Result<Long> {
        return try {
            if (terrain.name.isBlank()) {
                return Result.failure(IllegalArgumentException("El nombre del terreno es obligatorio"))
            }
            if (terrain.surface <= 0) {
                return Result.failure(IllegalArgumentException("La superficie debe ser mayor a 0"))
            }
            val id = terrainRepository.insertTerrain(terrain)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
