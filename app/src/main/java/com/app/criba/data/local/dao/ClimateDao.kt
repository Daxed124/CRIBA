package com.app.criba.data.local.dao

import androidx.room.*
import com.app.criba.data.local.entity.ClimateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClimateDao {
    @Query("SELECT * FROM climate_records WHERE cycleId = :cycleId ORDER BY date DESC")
    fun getClimateRecordsByCycleId(cycleId: Long): Flow<List<ClimateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: ClimateEntity): Long

    @Update
    suspend fun update(record: ClimateEntity)

    @Query("DELETE FROM climate_records WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM climate_records WHERE isSynced = 0")
    suspend fun getUnsyncedRecords(): List<ClimateEntity>

    @Query("UPDATE climate_records SET isSynced = 1 WHERE id = :id")
    suspend fun markAsSynced(id: Long)
}
