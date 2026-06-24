package com.app.criba.domain.model

data class ResumenDashboard(
    val terreno: Terrain?,
    val cicloActivo: CropCycle?,
    val diasDesdeSiembra: Int,
    val totalGastos: Double,
    val totalIngresos: Double,
    val utilidadNeta: Double,
    val roi: Double,
    val ultimosCiclos: List<CropCycle>,
    val plagasActivas: Int,
    val alertasSeveridadCritica: Int
)
