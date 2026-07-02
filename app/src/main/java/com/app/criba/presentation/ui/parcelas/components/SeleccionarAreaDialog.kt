package com.app.criba.presentation.ui.parcelas.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.app.criba.util.GeoUtils
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import java.util.Locale

/**
 * Mapa a pantalla completa (vista satelital) para marcar los vértices del área
 * de la parcela tocando el mapa. Muestra el polígono y el área estimada en vivo.
 */
@Composable
fun SeleccionarAreaDialog(
    initialCenter: LatLng?,
    initialPoints: List<LatLng> = emptyList(),
    onDismiss: () -> Unit,
    onConfirm: (List<LatLng>) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        var points by remember { mutableStateOf(initialPoints) }

        val start = initialPoints.firstOrNull() ?: initialCenter
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                start ?: LatLng(23.6345, -102.5528),
                if (start != null) 17f else 5f
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(mapType = MapType.HYBRID),
                uiSettings = MapUiSettings(zoomControlsEnabled = true, mapToolbarEnabled = false),
                onMapClick = { latLng -> points = points + latLng }
            ) {
                points.forEachIndexed { i, p ->
                    Marker(
                        state = MarkerState(position = p),
                        title = "Punto ${i + 1}"
                    )
                }
                if (points.size >= 3) {
                    Polygon(
                        points = points,
                        strokeColor = Color(0xFFC8A84B),
                        strokeWidth = 5f,
                        fillColor = Color(0x4D2D6A4F)
                    )
                } else if (points.size == 2) {
                    Polyline(points = points, color = Color(0xFFC8A84B), width = 5f)
                }
            }

            // Instrucciones + área estimada
            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Toca el mapa para marcar las esquinas de tu parcela",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (points.size >= 3) {
                        val ha = GeoUtils.areaHectares(points)
                        val m2 = GeoUtils.areaSquareMeters(points)
                        val excede = ha > 4.0
                        Text(
                            "Área: %.3f ha  ·  %,.0f m²".format(Locale.US, ha, m2),
                            style = MaterialTheme.typography.titleMedium,
                            color = if (excede) Color(0xFFC62828) else Color(0xFF1B4332)
                        )
                        if (excede) {
                            Text(
                                "El área supera el límite de 4 ha",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color(0xFFC62828)
                            )
                        }
                    } else if (points.isNotEmpty()) {
                        Text(
                            "${points.size} punto(s) — marca al menos 3",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }

            // Botonera inferior
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { if (points.isNotEmpty()) points = points.dropLast(1) },
                    enabled = points.isNotEmpty(),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                ) { Text("Deshacer") }

                OutlinedButton(
                    onClick = { points = emptyList() },
                    enabled = points.isNotEmpty(),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                ) { Text("Limpiar") }

                OutlinedButton(
                    onClick = onDismiss,
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                ) { Text("Cancelar") }

                Button(
                    onClick = { onConfirm(points) },
                    enabled = points.size >= 3 && GeoUtils.areaHectares(points) <= 4.0
                ) { Text("Confirmar") }
            }
        }
    }
}
