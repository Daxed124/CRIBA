package com.app.criba.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "terrains")
data class TerrainEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val surface: Double,
    val soilType: String,
    val latitude: Double,
    val longitude: Double,
    // Vértices del área de la parcela: "lat,lng;lat,lng;..." (null si no se marcó)
    val polygon: String? = null,
    val userId: String,
    val isSynced: Boolean = false
)
