package com.app.criba.di

import android.content.Context
import androidx.room.Room
import com.app.criba.data.local.CribaDatabase
import com.app.criba.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CribaDatabase {
        return Room.databaseBuilder(
            context,
            CribaDatabase::class.java,
            "criba_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides fun provideTerrainDao(db: CribaDatabase): TerrainDao = db.terrainDao()
    @Provides fun provideCycleDao(db: CribaDatabase): CycleDao = db.cycleDao()
    @Provides fun provideTransactionDao(db: CribaDatabase): TransactionDao = db.transactionDao()
    @Provides fun providePestDao(db: CribaDatabase): PestDao = db.pestDao()
    @Provides fun provideClimateDao(db: CribaDatabase): ClimateDao = db.climateDao()
    @Provides fun provideUserDao(db: CribaDatabase): UserDao = db.userDao()
}
