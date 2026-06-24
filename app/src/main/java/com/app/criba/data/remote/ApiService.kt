package com.app.criba.data.remote

import com.app.criba.data.local.entity.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Retrofit API service for syncing data with the backend.
 * Base URL should be configured in the NetworkModule.
 * Endpoints are placeholders — update when backend is ready.
 */
interface ApiService {

    // ========== Terrains ==========
    @POST("api/terrains")
    suspend fun syncTerrain(@Body terrain: TerrainEntity): Response<Unit>

    @GET("api/terrains")
    suspend fun getTerrains(): Response<List<TerrainEntity>>

    // ========== Cycles ==========
    @POST("api/cycles")
    suspend fun syncCycle(@Body cycle: CycleEntity): Response<Unit>

    @GET("api/cycles")
    suspend fun getCycles(): Response<List<CycleEntity>>

    // ========== Transactions ==========
    @POST("api/transactions")
    suspend fun syncTransaction(@Body transaction: TransactionEntity): Response<Unit>

    // ========== Pests ==========
    @POST("api/pests")
    suspend fun syncPest(@Body pest: PestEntity): Response<Unit>

    // ========== Climate ==========
    @POST("api/climate")
    suspend fun syncClimateRecord(@Body record: ClimateEntity): Response<Unit>
}
