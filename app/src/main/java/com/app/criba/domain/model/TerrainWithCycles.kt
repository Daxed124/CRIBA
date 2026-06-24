package com.app.criba.domain.model

data class TerrainWithCycles(
    val terrain: Terrain,
    val cycles: List<CropCycle>
) {
    val activeCycle: CropCycle?
        get() = cycles.find { it.endDate == null }
}
