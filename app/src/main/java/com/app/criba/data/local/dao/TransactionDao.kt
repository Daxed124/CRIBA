package com.app.criba.data.local.dao

import androidx.room.*
import com.app.criba.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions WHERE cycleId = :cycleId ORDER BY date DESC")
    fun getTransactionsByCycleId(cycleId: Long): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity): Long

    @Update
    suspend fun update(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT COALESCE(SUM(amount), 0.0) FROM transactions WHERE cycleId = :cycleId AND type = 'INGRESO'")
    suspend fun sumIncomeByCycleId(cycleId: Long): Double

    @Query("SELECT COALESCE(SUM(amount), 0.0) FROM transactions WHERE cycleId = :cycleId AND type = 'GASTO'")
    suspend fun sumExpenseByCycleId(cycleId: Long): Double

    @Query("SELECT * FROM transactions WHERE isSynced = 0")
    suspend fun getUnsyncedTransactions(): List<TransactionEntity>

    @Query("UPDATE transactions SET isSynced = 1 WHERE id = :id")
    suspend fun markAsSynced(id: Long)
}
