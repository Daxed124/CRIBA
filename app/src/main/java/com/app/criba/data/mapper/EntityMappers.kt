package com.app.criba.data.mapper

import com.app.criba.data.local.entity.*
import com.app.criba.domain.model.*

// ========== Terrain Mappers ==========
fun TerrainEntity.toDomain(): Terrain = Terrain(
    id = id, name = name, surface = surface, soilType = soilType,
    latitude = latitude, longitude = longitude, userId = userId, isSynced = isSynced
)

fun Terrain.toEntity(): TerrainEntity = TerrainEntity(
    id = id, name = name, surface = surface, soilType = soilType,
    latitude = latitude, longitude = longitude, userId = userId, isSynced = isSynced
)

// ========== CropCycle Mappers ==========
fun CycleEntity.toDomain(): CropCycle = CropCycle(
    id = id, terrainId = terrainId, species = species,
    startDate = startDate, endDate = endDate,
    phenologicalState = PhenologicalState.valueOf(phenologicalState),
    harvestedVolumeKg = harvestedVolumeKg,
    isSynced = isSynced
)

fun CropCycle.toEntity(): CycleEntity = CycleEntity(
    id = id, terrainId = terrainId, species = species,
    startDate = startDate, endDate = endDate,
    phenologicalState = phenologicalState.name,
    harvestedVolumeKg = harvestedVolumeKg,
    isSynced = isSynced
)

// ========== Transaction Mappers ==========
fun TransactionEntity.toDomain(): Transaction = Transaction(
    id = id, cycleId = cycleId,
    type = TransactionType.valueOf(type),
    amount = amount,
    category = category?.let { ExpenseCategory.valueOf(it) },
    description = description, date = date, isSynced = isSynced
)

fun Transaction.toEntity(): TransactionEntity = TransactionEntity(
    id = id, cycleId = cycleId,
    type = type.name,
    amount = amount,
    category = category?.name,
    description = description, date = date, isSynced = isSynced
)

// ========== PestIncident Mappers ==========
fun PestEntity.toDomain(): PestIncident = PestIncident(
    id = id, cycleId = cycleId, name = name,
    severity = Severity.valueOf(severity),
    description = description, photoUri = photoLocalUri,
    latitude = latitude, longitude = longitude,
    date = date, isSynced = isSynced
)

fun PestIncident.toEntity(): PestEntity = PestEntity(
    id = id, cycleId = cycleId, name = name,
    severity = severity.name,
    description = description, photoLocalUri = photoUri,
    latitude = latitude, longitude = longitude,
    date = date, isSynced = isSynced
)

// ========== ClimateRecord Mappers ==========
fun ClimateEntity.toDomain(): ClimateRecord = ClimateRecord(
    id = id, cycleId = cycleId,
    rainfall = rainfall, temperature = temperature,
    droughtStage = DroughtStage.valueOf(droughtStage),
    date = date, isSynced = isSynced
)

fun ClimateRecord.toEntity(): ClimateEntity = ClimateEntity(
    id = id, cycleId = cycleId,
    rainfall = rainfall, temperature = temperature,
    droughtStage = droughtStage.name,
    date = date, isSynced = isSynced
)
