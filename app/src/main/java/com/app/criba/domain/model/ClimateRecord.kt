package com.app.criba.domain.model

data class ClimateRecord(
    val id: Long = 0,
    val cycleId: Long,
    val rainfall: Double,      // mm
    val temperature: Double,   // °C
    val droughtStage: DroughtStage,
    val date: Long,            // epoch millis
    val isSynced: Boolean = false
)

enum class DroughtStage(val displayName: String) {
    NINGUNA("Ninguna"),
    LEVE("Leve"),
    MODERADA("Moderada"),
    SEVERA("Severa"),
    EXTREMA("Extrema")
}
