package com.app.criba.presentation.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.app.criba.util.GeoUtils
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState

/**
 * Mini mapa embebido que muestra la ubicación de un terreno (Fase 10.5).
 * Gestos desactivados: es solo para visualizar el punto y su área marcada.
 */
@Composable
fun MapaTerrenoMini(
    latitud: Double,
    longitud: Double,
    nombre: String,
    polygon: String? = null,
    modifier: Modifier = Modifier
) {
    if (latitud == 0.0 && longitud == 0.0) {
        Box(modifier, contentAlignment = Alignment.Center) {
            Text(
                "Este terreno no tiene ubicación GPS registrada.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        return
    }

    val posicion = LatLng(latitud, longitud)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(posicion, 14f)
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            scrollGesturesEnabled = false,
            rotationGesturesEnabled = false,
            tiltGesturesEnabled = false,
            zoomGesturesEnabled = false
        )
    ) {
        Marker(state = MarkerState(position = posicion), title = nombre)
        val area = GeoUtils.polygonFromString(polygon)
        if (area.size >= 3) {
            Polygon(
                points = area,
                strokeColor = Color(0xFFC8A84B),
                strokeWidth = 4f,
                fillColor = Color(0x4D2D6A4F)
            )
        }
    }
}
