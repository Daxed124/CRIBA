package com.app.criba.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
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
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cycleId: Long,
    val type: String,        // INGRESO or GASTO
    val amount: Double,
    val category: String?,   // nullable for income
    val description: String,
    val date: Long,
    val isSynced: Boolean = false
)
