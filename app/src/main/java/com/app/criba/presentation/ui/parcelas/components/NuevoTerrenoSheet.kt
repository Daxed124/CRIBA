package com.app.criba.presentation.ui.parcelas.components

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

private const val LIMITE_HA = 4.0

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
    var tipoSuelo by remember { mutableStateOf("") }

    // Superficie manual OPCIONAL (se usa solo si no se marca el área en el mapa)
    var hectareas by remember { mutableStateOf("") }
    var metros by remember { mutableStateOf("") }

    var latitud by remember { mutableStateOf<Double?>(null) }
    var longitud by remember { mutableStateOf<Double?>(null) }
    var cargandoUbicacion by remember { mutableStateOf(false) }

    // Área de la parcela marcada con puntos en el mapa
    var areaPuntos by remember { mutableStateOf<List<LatLng>>(emptyList()) }
    var showAreaDialog by remember { mutableStateOf(false) }

    val soilTypes = listOf("Arcilloso", "Franco", "Arenoso", "Limoso", "Franco-Arcilloso")
    var expanded by remember { mutableStateOf(false) }

    // Superficie efectiva en hectáreas: prioridad al área medida en el mapa (automática),
    // si no, hectáreas manuales, si no, metros manuales convertidos.
    val areaMapaHa = if (areaPuntos.size >= 3) GeoUtils.areaHectares(areaPuntos) else null
    val haManual = hectareas.replace(',', '.').toDoubleOrNull()
    val m2Manual = metros.replace(',', '.').toDoubleOrNull()
    val superficieHa: Double? = areaMapaHa ?: haManual ?: m2Manual?.let { it / 10000.0 }

    val excedeLimite = superficieHa != null && superficieHa > LIMITE_HA
    val isValid = nombre.isNotBlank() && tipoSuelo.isNotBlank() &&
        superficieHa != null && superficieHa > 0.0 && !excedeLimite

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 600.dp)
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Registrar Nueva Parcela", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del terreno") },
                isError = nombre.isBlank(),
                singleLine = true,
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
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
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

            // ===== Ubicación GPS =====
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
                ) { Text("📍 Obtener Ubicación") }
                when {
                    cargandoUbicacion -> CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    latitud != null -> Text("%.4f, %.4f".format(latitud, longitud))
                    else -> Text("Sin coordenadas")
                }
            }

            // ===== Área marcada en el mapa (medida automática) =====
            Text("Área de la parcela", style = MaterialTheme.typography.titleSmall)
            OutlinedButton(onClick = { showAreaDialog = true }, modifier = Modifier.fillMaxWidth()) {
                Text(if (areaPuntos.isEmpty()) "🗺 Marcar Área en el Mapa" else "🗺 Editar Área")
            }
            if (areaMapaHa != null) {
                val m2 = GeoUtils.areaSquareMeters(areaPuntos)
                Text(
                    "Medida: %.3f ha  ·  %,.0f m²".format(Locale.US, areaMapaHa, m2),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (excedeLimite) Color(0xFFC62828) else Color(0xFF1B4332)
                )
            } else {
                Text(
                    "Marca el contorno para medir el área automáticamente, o ingrésala abajo.",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
            }

            // ===== Superficie manual (opcional) =====
            if (areaMapaHa == null) {
                Text("Superficie manual (opcional)", style = MaterialTheme.typography.titleSmall)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = hectareas,
                        onValueChange = { hectareas = it; if (it.isNotBlank()) metros = "" },
                        label = { Text("Hectáreas") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = metros,
                        onValueChange = { metros = it; if (it.isNotBlank()) hectareas = "" },
                        label = { Text("Metros²") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (excedeLimite) {
                Text(
                    "La superficie supera el límite de $LIMITE_HA hectáreas.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(
                onClick = {
                    if (isValid) {
                        val centro = if (areaPuntos.size >= 3) GeoUtils.centroid(areaPuntos) else null
                        onGuardar(
                            Terrain(
                                userId = "local_user",
                                name = nombre,
                                surface = superficieHa ?: 0.0,
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
            }
        )
    }
}
