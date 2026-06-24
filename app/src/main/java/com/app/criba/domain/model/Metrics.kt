package com.app.criba.domain.model

data class Metrics(
    val netUtility: Double,       // Uneta = Σ(Ingresos) - Σ(Gastos)
    val roi: Double,              // ROI = (Uneta / Σ(Gastos)) * 100
    val yieldEfficiency: Double   // Erend = Volumen Cosechado / Superficie
)
