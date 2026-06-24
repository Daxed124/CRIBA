package com.app.criba.data.local.dao

import androidx.room.*
import com.app.criba.data.local.entity.PestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PestDao {
    @Query("SELECT * FROM pest_incidents WHERE cycleId = :cycleId ORDER BY date DESC")
    fun getPestsByCycleId(cycleId: Long): Flow<List<PestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pest: PestEntity): Long

    @Update
    suspend fun update(pest: PestEntity)

    @Query("DELETE FROM pest_incidents WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM pest_incidents WHERE isSynced = 0")
    suspend fun getUnsyncedPests(): List<PestEntity>

    @Query("UPDATE pest_incidents SET isSynced = 1 WHERE id = :id")
    suspend fun markAsSynced(id: Long)
}
