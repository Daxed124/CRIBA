package com.app.criba.domain.model

data class Terrain(
    val id: Long = 0,
    val name: String,
    val surface: Double,
    val soilType: String,
    val latitude: Double,
    val longitude: Double,
    val userId: String,
    val isSynced: Boolean = false
)
