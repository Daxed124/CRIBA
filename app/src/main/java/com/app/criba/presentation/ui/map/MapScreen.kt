package com.app.criba.presentation.ui.map

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.criba.domain.model.Terrain
import com.app.criba.presentation.state.MapUiState
import com.app.criba.presentation.viewmodel.MapViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState

/** Un terreno tiene ubicación válida si no está en (0,0) (placeholder). */
private fun Terrain.tieneCoordenadas(): Boolean = latitude != 0.0 || longitude != 0.0

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    onNavigateToTerrainForm: () -> Unit,
    onNavigateToCycle: (Long) -> Unit,
    viewModel: MapViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    // Centro de México por defecto
    val defaultLocation = LatLng(23.6345, -102.5528)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 5f)
    }

    // Pide el permiso de ubicación una vez al entrar
    LaunchedEffect(Unit) {
        if (!locationPermission.status.isGranted) locationPermission.launchPermissionRequest()
    }

    // Centra la cámara en el primer terreno con coordenadas UNA sola vez
    // (para no interrumpir el paneo del usuario en cada actualización de datos)
    var camaraCentrada by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(uiState) {
        val s = uiState
        if (!camaraCentrada && s is MapUiState.Success) {
            s.terrains.firstOrNull { it.tieneCoordenadas() }?.let {
                cameraPositionState.position =
                    CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 12f)
                camaraCentrada = true
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Terrenos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToTerrainForm) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir Terreno")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (val state = uiState) {
                is MapUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is MapUiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is MapUiState.Success -> {
                    val terrenosConCoords = state.terrains.filter { it.tieneCoordenadas() }

                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        properties = MapProperties(isMyLocationEnabled = locationPermission.status.isGranted),
                        uiSettings = MapUiSettings(
                            zoomControlsEnabled = true,
                            myLocationButtonEnabled = true,
                            mapToolbarEnabled = false
                        )
                    ) {
                        terrenosConCoords.forEach { terrain ->
                            Marker(
                                state = MarkerState(position = LatLng(terrain.latitude, terrain.longitude)),
                                title = terrain.name,
                                snippet = "Superficie: ${terrain.surface} ha",
                                onClick = {
                                    onNavigateToCycle(terrain.id)
                                    true
                                }
                            )
                        }
                        // Áreas de parcelas marcadas con puntos
                        state.terrains.forEach { terrain ->
                            val area = com.app.criba.util.GeoUtils.polygonFromString(terrain.polygon)
                            if (area.size >= 3) {
                                Polygon(
                                    points = area,
                                    strokeColor = androidx.compose.ui.graphics.Color(0xFFC8A84B),
                                    strokeWidth = 4f,
                                    fillColor = androidx.compose.ui.graphics.Color(0x4D2D6A4F)
                                )
                            }
                        }
                    }

                    if (terrenosConCoords.isEmpty()) {
                        Card(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(16.dp)
                        ) {
                            Text(
                                "Ningún terreno tiene ubicación GPS. Al crear un terreno, usa \"Obtener Ubicación\" para que aparezca en el mapa.",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
