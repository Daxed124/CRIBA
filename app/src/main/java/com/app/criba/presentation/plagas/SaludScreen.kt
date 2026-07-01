package com.app.criba.presentation.plagas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.criba.domain.model.Severity

@Composable
fun SaludScreen(
    viewModel: PlagasViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.cargarSaludGlobal()
    }

    val plagasCriticas = uiState.plagas.count { it.severity == Severity.CRITICO }
    val plagasMedias = uiState.plagas.count { it.severity == Severity.MEDIO }
    val plagasBajas = uiState.plagas.count { it.severity == Severity.BAJO }
    val total = uiState.plagas.size

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                HealthScoreMeter(score = uiState.healthScore)
            }
            
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("DISTRIBUCIÓN DE PLAGAS", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    SeverityProgress("Bajo (Monitoreo regular)", plagasBajas, total, Color(0xFF2D6A4F))
                    Spacer(modifier = Modifier.height(8.dp))
                    SeverityProgress("Medio (Tratamiento preventivo)", plagasMedias, total, Color(0xFFE87C1B))
                    Spacer(modifier = Modifier.height(8.dp))
                    SeverityProgress("Crítico (Acción inmediata)", plagasCriticas, total, Color(0xFFC62828))
                }
            }
            
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("RECOMENDACIONES", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    if (plagasCriticas > 0) {
                        RecommendationCard("⚠️ Aplicar control inmediato. Contactar agrónomo.", Color(0xFFF8D7DA))
                    } else if (plagasMedias > 0) {
                        RecommendationCard("🔎 Monitorear cada 48h. Evaluar fungicida.", Color(0xFFFFF3CD))
                    } else if (plagasBajas > 0) {
                        RecommendationCard("🌿 Nivel bajo de incidencia. Aplicar control preventivo.", Color(0xFFD4EDDA))
                    } else {
                        RecommendationCard("✅ Cultivo saludable. Continuar monitoreo regular.", Color(0xFFD4EDDA))
                    }
                }
            }
        }
    }
}

@Composable
fun HealthScoreMeter(score: Int) {
    val arcColor = when {
        score >= 80 -> Color(0xFF2D6A4F)
        score >= 50 -> Color(0xFFE87C1B)
        else -> Color(0xFFC62828)
    }

    Box(modifier = Modifier.size(200.dp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = Color.LightGray,
                startAngle = 135f,
                sweepAngle = 270f,
                useCenter = false,
                style = Stroke(width = 24.dp.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = arcColor,
                startAngle = 135f,
                sweepAngle = 270f * (score / 100f),
                useCenter = false,
                style = Stroke(width = 24.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(score.toString(), style = MaterialTheme.typography.displayLarge, color = arcColor)
            Text("SALUD DEL CULTIVO", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
        }
    }
}

@Composable
fun SeverityProgress(label: String, count: Int, total: Int, color: Color) {
    val progress = if (total > 0) count.toFloat() / total.toFloat() else 0f
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, style = MaterialTheme.typography.bodyMedium)
            Text("$count", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(8.dp),
            color = color,
            trackColor = Color.LightGray
        )
    }
}

@Composable
fun RecommendationCard(text: String, bgColor: Color) {
    Card(colors = CardDefaults.cardColors(containerColor = bgColor), modifier = Modifier.fillMaxWidth()) {
        Text(text, modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.bodyLarge)
    }
}
