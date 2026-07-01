package com.app.criba.domain.usecase

import com.app.criba.domain.model.ResumenFinanciero
import com.app.criba.domain.model.Transaction
import com.app.criba.domain.model.TransactionType
import javax.inject.Inject

/**
 * Motor estadístico financiero (Fase 8). Función pura y testeable:
 * calcula utilidad neta, ROI, rentabilidad, desglose de gastos por categoría
 * (monto y %) y la eficiencia de rendimiento (Erend) si hay datos de cosecha.
 */
class GenerarResumenFinancieroUseCase @Inject constructor() {

    operator fun invoke(
        transactions: List<Transaction>,
        volumenKg: Double? = null,
        hectareas: Double? = null
    ): ResumenFinanciero {
        val totalIngresos = transactions.filter { it.type == TransactionType.INGRESO }.sumOf { it.amount }
        val totalGastos = transactions.filter { it.type == TransactionType.GASTO }.sumOf { it.amount }

        val utilidadNeta = totalIngresos - totalGastos
        val roi = if (totalGastos > 0.0) (utilidadNeta / totalGastos) * 100.0 else 0.0

        val gastosPorCategoria = transactions
            .filter { it.type == TransactionType.GASTO && it.category != null }
            .groupBy { it.category!!.displayName }
            .mapValues { entry -> entry.value.sumOf { it.amount } }

        val porcentajeGastoPorCategoria =
            if (totalGastos > 0.0) gastosPorCategoria.mapValues { (it.value / totalGastos) * 100.0 }
            else emptyMap()

        val eficienciaRendimiento =
            if (volumenKg != null && hectareas != null && hectareas > 0.0) volumenKg / hectareas
            else null

        return ResumenFinanciero(
            totalGastos = totalGastos,
            totalIngresos = totalIngresos,
            utilidadNeta = utilidadNeta,
            roi = roi,
            esRentable = utilidadNeta > 0.0,
            gastosPorCategoria = gastosPorCategoria,
            porcentajeGastoPorCategoria = porcentajeGastoPorCategoria,
            eficienciaRendimiento = eficienciaRendimiento
        )
    }
}
