package com.app.criba.domain.repository

import com.app.criba.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactionsByCycleId(cycleId: Long): Flow<List<Transaction>>
    suspend fun insertTransaction(transaction: Transaction): Long
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun deleteTransaction(id: Long)
    suspend fun sumIncomeByCycleId(cycleId: Long): Double
    suspend fun sumExpenseByCycleId(cycleId: Long): Double
}
