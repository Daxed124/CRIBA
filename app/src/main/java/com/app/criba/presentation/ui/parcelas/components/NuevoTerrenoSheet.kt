package com.app.criba.presentation.ui.parcelas.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.app.criba.domain.model.Terrain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoTerrenoSheet(
    onDismiss: () -> Unit,
    onGuardar: (Terrain) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var hectareas by remember { mutableStateOf("") }
    var tipoSuelo by remember { mutableStateOf("") }
    
    val soilTypes = listOf("Arcilloso", "Franco", "Arenoso", "Limoso", "Franco-Arcilloso")
    var expanded by remember { mutableStateOf(false) }

    val isValid = nombre.isNotBlank() && hectareas.toDoubleOrNull() != null && hectareas.toDoubleOrNull()!! > 0

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
                isError = hectareas.isNotBlank() && (hectareas.toDoubleOrNull() == null || hectareas.toDoubleOrNull()!! <= 0),
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

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = { /* TODO: GPS integration in Phase 7 */ }) {
                    Text("📍 Obtener Ubicación")
                }
                Text("Sin coordenadas", modifier = Modifier.padding(top = 12.dp))
            }

            Button(
                onClick = {
                    if (isValid) {
                        onGuardar(
                            Terrain(
                                userId = "local_user",
                                name = nombre,
                                surface = hectareas.toDoubleOrNull() ?: 0.0,
                                soilType = tipoSuelo,
                                latitude = 0.0, // Placeholder
                                longitude = 0.0 // Placeholder
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
}
