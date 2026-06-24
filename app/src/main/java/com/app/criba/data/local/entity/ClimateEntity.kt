package com.app.criba.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "climate_records",
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
data class ClimateEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cycleId: Long,
    val rainfall: Double,
    val temperature: Double,
    val droughtStage: String,  // enum name
    val date: Long,
    val isSynced: Boolean = false
)
