package com.app.criba.presentation.ui.parcelas.components

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.app.criba.domain.model.Terrain
import com.app.criba.util.GeoUtils
import com.app.criba.util.LocationHelper
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun NuevoTerrenoSheet(
    onDismiss: () -> Unit,
    onGuardar: (Terrain) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val locationHelper = remember { LocationHelper(context) }
    val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    var nombre by remember { mutableStateOf("") }
    var hectareas by remember { mutableStateOf("") }
    var tipoSuelo by remember { mutableStateOf("") }

    var latitud by remember { mutableStateOf<Double?>(null) }
    var longitud by remember { mutableStateOf<Double?>(null) }
    var cargandoUbicacion by remember { mutableStateOf(false) }

    // Área de la parcela marcada con puntos en el mapa
    var areaPuntos by remember { mutableStateOf<List<LatLng>>(emptyList()) }
    var showAreaDialog by remember { mutableStateOf(false) }

    val soilTypes = listOf("Arcilloso", "Franco", "Arenoso", "Limoso", "Franco-Arcilloso")
    var expanded by remember { mutableStateOf(false) }

    // Acepta coma o punto decimal (el teclado en español escribe coma)
    val hectareasNum = hectareas.replace(',', '.').toDoubleOrNull()
    val isValid = nombre.isNotBlank() && hectareasNum != null && hectareasNum > 0 &&
        tipoSuelo.isNotBlank()

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Registrar Nueva Parcela", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del terreno") },
                isError = nombre.isBlank(),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = hectareas,
                onValueChange = { hectareas = it },
                label = { Text("Hectáreas") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                isError = hectareas.isNotBlank() && (hectareasNum == null || hectareasNum <= 0),
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = tipoSuelo,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de suelo") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    soilTypes.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                tipoSuelo = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    enabled = !cargandoUbicacion,
                    onClick = {
                        if (locationPermission.status.isGranted) {
                            cargandoUbicacion = true
                            scope.launch {
                                val loc = locationHelper.getCurrentLocation()
                                    ?: locationHelper.getLastKnownLocation()
                                latitud = loc?.first
                                longitud = loc?.second
                                cargandoUbicacion = false
                            }
                        } else {
                            locationPermission.launchPermissionRequest()
                        }
                    }
                ) {
                    Text("📍 Obtener Ubicación")
                }
                when {
                    cargandoUbicacion -> CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    latitud != null -> Text(
                        "%.4f, %.4f".format(latitud, longitud),
                        modifier = Modifier.padding(top = 12.dp)
                    )
                    else -> Text("Sin coordenadas", modifier = Modifier.padding(top = 12.dp))
                }
            }

            // Marcar el área de la parcela con puntos sobre el mapa satelital
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(onClick = { showAreaDialog = true }) {
                    Text(if (areaPuntos.isEmpty()) "🗺 Marcar Área" else "🗺 Editar Área")
                }
                if (areaPuntos.size >= 3) {
                    Text(
                        "%d puntos · %.2f ha".format(
                            Locale.US, areaPuntos.size, GeoUtils.areaHectares(areaPuntos)
                        ),
                        style = MaterialTheme.typography.labelLarge
                    )
                } else {
                    Text("Sin área marcada", style = MaterialTheme.typography.labelMedium)
                }
            }

            Button(
                onClick = {
                    if (isValid) {
                        // Si se marcó área y no hay GPS, usa el centro del polígono como ubicación
                        val centro = if (areaPuntos.size >= 3) GeoUtils.centroid(areaPuntos) else null
                        onGuardar(
                            Terrain(
                                userId = "local_user",
                                name = nombre,
                                surface = hectareasNum ?: 0.0,
                                soilType = tipoSuelo,
                                latitude = latitud ?: centro?.latitude ?: 0.0,
                                longitude = longitud ?: centro?.longitude ?: 0.0,
                                polygon = if (areaPuntos.size >= 3) GeoUtils.polygonToString(areaPuntos) else null
                            )
                        )
                    }
                },
                enabled = isValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Parcela")
            }
        }
    }

    if (showAreaDialog) {
        SeleccionarAreaDialog(
            initialCenter = latitud?.let { la -> longitud?.let { lo -> LatLng(la, lo) } },
            initialPoints = areaPuntos,
            onDismiss = { showAreaDialog = false },
            onConfirm = { puntos ->
                areaPuntos = puntos
                showAreaDialog = false
                // Autollenar hectáreas con el área calculada si el campo está vacío
                if (hectareas.isBlank() && puntos.size >= 3) {
                    hectareas = "%.2f".format(Locale.US, GeoUtils.areaHectares(puntos))
                }
            }
        )
    }
}
