package com.app.criba.data.local.dao

import androidx.room.*
import com.app.criba.data.local.entity.CycleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CycleDao {
    @Query("SELECT * FROM crop_cycles WHERE terrainId = :terrainId ORDER BY startDate DESC")
    fun getCyclesByTerrainId(terrainId: Long): Flow<List<CycleEntity>>

    @Query("SELECT * FROM crop_cycles WHERE id = :id")
    suspend fun getCycleById(id: Long): CycleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cycle: CycleEntity): Long

    @Update
    suspend fun update(cycle: CycleEntity)

    @Query("DELETE FROM crop_cycles WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM crop_cycles WHERE isSynced = 0")
    suspend fun getUnsyncedCycles(): List<CycleEntity>

    @Query("UPDATE crop_cycles SET isSynced = 1 WHERE id = :id")
    suspend fun markAsSynced(id: Long)
}
