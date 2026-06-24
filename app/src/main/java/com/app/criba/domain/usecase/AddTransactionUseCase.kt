package com.app.criba.domain.usecase

import com.app.criba.domain.model.Transaction
import com.app.criba.domain.model.TransactionType
import com.app.criba.domain.repository.TransactionRepository
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction): Result<Long> {
        return try {
            if (transaction.amount <= 0) {
                return Result.failure(IllegalArgumentException("El monto debe ser positivo"))
            }
            if (transaction.type == TransactionType.GASTO && transaction.category == null) {
                return Result.failure(IllegalArgumentException("La categoría es obligatoria para gastos"))
            }
            val id = transactionRepository.insertTransaction(transaction)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
