package com.app.criba.data.local.dao

import androidx.room.*
import com.app.criba.data.local.entity.TerrainEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TerrainDao {
    @Query("SELECT * FROM terrains ORDER BY name ASC")
    fun getAllTerrains(): Flow<List<TerrainEntity>>

    @Query("SELECT * FROM terrains WHERE userId = :userId ORDER BY name ASC")
    fun getTerrainsByUserId(userId: String): Flow<List<TerrainEntity>>

    @Query("SELECT * FROM terrains WHERE id = :id")
    suspend fun getTerrainById(id: Long): TerrainEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(terrain: TerrainEntity): Long

    @Update
    suspend fun update(terrain: TerrainEntity)

    @Query("DELETE FROM terrains WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM terrains WHERE isSynced = 0")
    suspend fun getUnsyncedTerrains(): List<TerrainEntity>

    @Query("UPDATE terrains SET isSynced = 1 WHERE id = :id")
    suspend fun markAsSynced(id: Long)
}
