package com.app.criba.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "crop_cycles",
    foreignKeys = [
        ForeignKey(
            entity = TerrainEntity::class,
            parentColumns = ["id"],
            childColumns = ["terrainId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("terrainId")]
)
data class CycleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val terrainId: Long,
    val species: String,
    val startDate: Long,
    val endDate: Long?,
    val phenologicalState: String, // stored as enum name
    val harvestedVolumeKg: Double? = null,
    val isSynced: Boolean = false
)
