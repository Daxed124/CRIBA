package com.app.criba.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.criba.data.local.dao.*
import com.app.criba.data.local.entity.*

@Database(
    entities = [
        TerrainEntity::class,
        CycleEntity::class,
        TransactionEntity::class,
        PestEntity::class,
        ClimateEntity::class,
        UserEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class CribaDatabase : RoomDatabase() {
    abstract fun terrainDao(): TerrainDao
    abstract fun cycleDao(): CycleDao
    abstract fun transactionDao(): TransactionDao
    abstract fun pestDao(): PestDao
    abstract fun climateDao(): ClimateDao
    abstract fun userDao(): UserDao
}
