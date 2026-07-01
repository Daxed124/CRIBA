package com.app.criba.util

import com.google.android.gms.maps.model.LatLng
import kotlin.math.abs
import kotlin.math.cos

/**
 * Utilidades para el área de parcelas marcada con puntos en el mapa.
 * El polígono se persiste como texto: "lat,lng;lat,lng;..."
 */
object GeoUtils {

    private const val EARTH_RADIUS_M = 6371000.0

    fun polygonToString(points: List<LatLng>): String =
        points.joinToString(";") { "${it.latitude},${it.longitude}" }

    fun polygonFromString(value: String?): List<LatLng> {
        if (value.isNullOrBlank()) return emptyList()
        return value.split(";").mapNotNull { pair ->
            val parts = pair.split(",")
            val lat = parts.getOrNull(0)?.toDoubleOrNull()
            val lng = parts.getOrNull(1)?.toDoubleOrNull()
            if (lat != null && lng != null) LatLng(lat, lng) else null
        }
    }

    /** Centro (promedio de vértices) para usar como ubicación del terreno. */
    fun centroid(points: List<LatLng>): LatLng =
        LatLng(points.map { it.latitude }.average(), points.map { it.longitude }.average())

    /**
     * Área del polígono en hectáreas (fórmula de Gauss/shoelace sobre proyección
     * equirectangular local; precisión sobrada para parcelas agrícolas).
     */
    fun areaHectares(points: List<LatLng>): Double {
        if (points.size < 3) return 0.0
        val lat0 = Math.toRadians(points.map { it.latitude }.average())
        val projected = points.map {
            val x = Math.toRadians(it.longitude) * EARTH_RADIUS_M * cos(lat0)
            val y = Math.toRadians(it.latitude) * EARTH_RADIUS_M
            x to y
        }
        var sum = 0.0
        for (i in projected.indices) {
            val (x1, y1) = projected[i]
            val (x2, y2) = projected[(i + 1) % projected.size]
            sum += x1 * y2 - x2 * y1
        }
        return abs(sum) / 2.0 / 10000.0
    }
}
