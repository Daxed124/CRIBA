package com.app.criba.domain.model

data class PestIncident(
    val id: Long = 0,
    val cycleId: Long,
    val name: String,
    val severity: Severity,
    val description: String,
    val photoUri: String?,
    val date: Long, // epoch millis
    val isSynced: Boolean = false
)

enum class Severity(val displayName: String) {
    BAJO("Bajo"),
    MEDIO("Medio"),
    CRITICO("Crítico")
}
