package com.app.criba.domain.model

/**
 * Resumen estadístico climático de un ciclo (Fase 9).
 */
data class ClimaResumen(
    val promedioTemp: Double = 0.0,
    val lluviaAcumulada: Double = 0.0,
    val diasSinLluvia: Int = 0,
    val etapaActual: DroughtStage = DroughtStage.NINGUNA
)
