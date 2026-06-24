package com.app.criba.di

import com.app.criba.data.repository.*
import com.app.criba.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds @Singleton
    abstract fun bindTerrainRepository(impl: TerrainRepositoryImpl): TerrainRepository

    @Binds @Singleton
    abstract fun bindCycleRepository(impl: CycleRepositoryImpl): CycleRepository

    @Binds @Singleton
    abstract fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

    @Binds @Singleton
    abstract fun bindPestRepository(impl: PestRepositoryImpl): PestRepository

    @Binds @Singleton
    abstract fun bindClimateRepository(impl: ClimateRepositoryImpl): ClimateRepository

    @Binds @Singleton
    abstract fun bindSyncRepository(impl: SyncRepositoryImpl): SyncRepository
}
