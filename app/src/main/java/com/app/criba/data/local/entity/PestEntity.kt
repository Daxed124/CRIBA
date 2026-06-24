package com.app.criba.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pest_incidents",
    foreignKeys = [
        ForeignKey(
            entity = CycleEntity::class,
            parentColumns = ["id"],
            childColumns = ["cycleId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("cycleId")]
)
data class PestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cycleId: Long,
    val name: String,
    val severity: String,     // BAJO, MEDIO, CRITICO
    val description: String,
    val photoLocalUri: String?,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val date: Long,
    val isSynced: Boolean = false
)
