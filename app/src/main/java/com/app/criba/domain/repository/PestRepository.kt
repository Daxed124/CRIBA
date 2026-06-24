package com.app.criba.domain.repository

import com.app.criba.domain.model.PestIncident
import kotlinx.coroutines.flow.Flow

interface PestRepository {
    fun getPestsByCycleId(cycleId: Long): Flow<List<PestIncident>>
    suspend fun insertPest(pest: PestIncident): Long
    suspend fun updatePest(pest: PestIncident)
    suspend fun deletePest(id: Long)
}
