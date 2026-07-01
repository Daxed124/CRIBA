package com.app.criba.domain.usecase

import com.app.criba.domain.model.ClimaResumen
import com.app.criba.domain.model.ClimateRecord
import com.app.criba.domain.model.DroughtStage
import javax.inject.Inject

/**
 * Calcula el resumen climático de un ciclo (Fase 9): temperatura promedio,
 * lluvia acumulada, días recientes consecutivos sin lluvia y etapa de sequía actual.
 */
class GenerarResumenClimaUseCase @Inject constructor() {

    operator fun invoke(records: List<ClimateRecord>): ClimaResumen {
        if (records.isEmpty()) return ClimaResumen()

        val masRecientePrimero = records.sortedByDescending { it.date }
        val promedio = records.map { it.temperature }.average()
        val acumulada = records.sumOf { it.rainfall }
        val diasSinLluvia = masRecientePrimero.takeWhile { it.rainfall <= 0.0 }.size
        val etapaActual = masRecientePrimero.first().droughtStage

        return ClimaResumen(
            promedioTemp = promedio,
            lluviaAcumulada = acumulada,
            diasSinLluvia = diasSinLluvia,
            etapaActual = etapaActual
        )
    }
}
