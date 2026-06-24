package com.app.criba.domain.usecase

import com.app.criba.domain.model.Metrics
import com.app.criba.domain.repository.TransactionRepository
import javax.inject.Inject

class CalculateMetricsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(cycleId: Long, harvestedVolume: Double, surface: Double): Metrics {
        val totalIncome = transactionRepository.sumIncomeByCycleId(cycleId)
        val totalExpense = transactionRepository.sumExpenseByCycleId(cycleId)

        val netUtility = totalIncome - totalExpense
        val roi = if (totalExpense > 0) (netUtility / totalExpense) * 100 else 0.0
        val yieldEfficiency = if (surface > 0) harvestedVolume / surface else 0.0

        return Metrics(
            netUtility = netUtility,
            roi = roi,
            yieldEfficiency = yieldEfficiency
        )
    }
}
