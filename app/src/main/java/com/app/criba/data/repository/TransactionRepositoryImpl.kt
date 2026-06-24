package com.app.criba.data.repository

import com.app.criba.data.local.dao.TransactionDao
import com.app.criba.data.mapper.toDomain
import com.app.criba.data.mapper.toEntity
import com.app.criba.domain.model.Transaction
import com.app.criba.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepository {

    override fun getTransactionsByCycleId(cycleId: Long): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByCycleId(cycleId).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun insertTransaction(transaction: Transaction): Long {
        return transactionDao.insert(transaction.toEntity().copy(isSynced = false))
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.update(transaction.toEntity().copy(isSynced = false))
    }

    override suspend fun deleteTransaction(id: Long) {
        transactionDao.delete(id)
    }

    override suspend fun sumIncomeByCycleId(cycleId: Long): Double {
        return transactionDao.sumIncomeByCycleId(cycleId)
    }

    override suspend fun sumExpenseByCycleId(cycleId: Long): Double {
        return transactionDao.sumExpenseByCycleId(cycleId)
    }
}
