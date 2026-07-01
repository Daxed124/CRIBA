package com.app.criba.presentation.ui.climate.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import com.app.criba.domain.model.ClimateRecord

/**
 * Gráfica de temperatura (línea + área) dibujada con Canvas nativo, sin dependencias externas.
 */
@Composable
fun TemperaturaChart(records: List<ClimateRecord>, modifier: Modifier = Modifier) {
    val data = records.sortedBy { it.date }
    if (data.size < 2) {
        Box(modifier, contentAlignment = Alignment.Center) {
            Text("Registra al menos 2 días para ver la tendencia", style = MaterialTheme.typography.bodySmall)
        }
        return
    }

    val temps = data.map { it.temperature }
    val minT = temps.minOrNull() ?: 0.0
    val maxT = temps.maxOrNull() ?: 0.0
    val range = (maxT - minT).let { if (it <= 0.0) 1.0 else it }
    val lineColor = Color(0xFF2D6A4F)

    Canvas(modifier) {
        val w = size.width
        val h = size.height
        val padV = 16f
        val usableH = h - padV * 2
        val stepX = if (data.size > 1) w / (data.size - 1) else w

        // Gridlines horizontales
        val gridColor = Color.LightGray.copy(alpha = 0.4f)
        for (i in 0..4) {
            val y = padV + usableH * i / 4f
            drawLine(gridColor, Offset(0f, y), Offset(w, y), strokeWidth = 1f)
        }

        val points = data.mapIndexed { i, r ->
            val x = stepX * i
            val norm = ((r.temperature - minT) / range).toFloat()
            Offset(x, padV + usableH * (1f - norm))
        }

        // Área bajo la curva
        val area = Path().apply {
            moveTo(points.first().x, h)
            points.forEach { lineTo(it.x, it.y) }
            lineTo(points.last().x, h)
            close()
        }
        drawPath(area, brush = Brush.verticalGradient(listOf(lineColor.copy(alpha = 0.25f), Color.Transparent)))

        // Línea + puntos
        for (i in 0 until points.size - 1) {
            drawLine(lineColor, points[i], points[i + 1], strokeWidth = 5f)
        }
        points.forEach { drawCircle(lineColor, radius = 6f, center = it) }
    }
}

/**
 * Gráfica de precipitación (barras verticales) con Canvas.
 * La opacidad de cada barra es proporcional a los mm de ese registro.
 */
@Composable
fun LluviaChart(records: List<ClimateRecord>, modifier: Modifier = Modifier) {
    val data = records.sortedBy { it.date }
    if (data.isEmpty()) {
        Box(modifier, contentAlignment = Alignment.Center) {
            Text("Sin registros de lluvia", style = MaterialTheme.typography.bodySmall)
        }
        return
    }

    val maxMm = (data.maxOfOrNull { it.rainfall } ?: 0.0).let { if (it <= 0.0) 1.0 else it }
    val barColor = Color(0xFF1565C0)

    Canvas(modifier) {
        val w = size.width
        val h = size.height
        val barSpace = w / data.size
        val barWidth = barSpace * 0.6f

        data.forEachIndexed { i, r ->
            val barH = ((r.rainfall / maxMm) * h).toFloat().coerceAtLeast(0f)
            val x = barSpace * i + (barSpace - barWidth) / 2f
            val alpha = ((r.rainfall / maxMm).toFloat()).coerceIn(0.3f, 1f)
            drawRect(
                color = barColor.copy(alpha = alpha),
                topLeft = Offset(x, h - barH),
                size = Size(barWidth, barH)
            )
        }
    }
}
