package com.app.criba.domain.model

/**
 * Resultado del motor estadístico financiero de un ciclo (Fase 8).
 *
 * - utilidadNeta = Σ(Ingresos) − Σ(Gastos)
 * - roi (%)      = (utilidadNeta / Σ(Gastos)) × 100   (0 si no hay gastos, sin dividir por cero)
 * - eficienciaRendimiento (Erend) = volumenCosechadoKg / hectáreas  (null si no hay datos de cosecha)
 */
data class ResumenFinanciero(
    val totalGastos: Double = 0.0,
    val totalIngresos: Double = 0.0,
    val utilidadNeta: Double = 0.0,
    val roi: Double = 0.0,
    val esRentable: Boolean = false,
    val gastosPorCategoria: Map<String, Double> = emptyMap(),
    val porcentajeGastoPorCategoria: Map<String, Double> = emptyMap(),
    val eficienciaRendimiento: Double? = null
)
