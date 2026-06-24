package com.app.criba.domain.model

data class CropCycle(
    val id: Long = 0,
    val terrainId: Long,
    val species: String,
    val startDate: Long, // epoch millis
    val endDate: Long?,  // epoch millis, nullable if ongoing
    val phenologicalState: PhenologicalState = PhenologicalState.SIEMBRA,
    val harvestedVolumeKg: Double? = null,
    val isSynced: Boolean = false
)

enum class PhenologicalState(val displayName: String) {
    SIEMBRA("Siembra"),
    DESARROLLO("Desarrollo"),
    FLORACION("Floración"),
    COSECHA("Cosecha"),
    FINALIZADO("Finalizado")
}
