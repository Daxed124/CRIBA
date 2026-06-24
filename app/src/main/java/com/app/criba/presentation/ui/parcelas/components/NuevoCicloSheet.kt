package com.app.criba.presentation.ui.parcelas.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.criba.domain.model.CropCycle
import com.app.criba.domain.model.PhenologicalState
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoCicloSheet(
    terrenoId: Long,
    onDismiss: () -> Unit,
    onGuardar: (CropCycle) -> Unit
) {
    var cultivo by remember { mutableStateOf("") }
    
    var showStartDatePicker by remember { mutableStateOf(false) }
    var startDate by remember { mutableStateOf<Long?>(null) }
    val startDatePickerState = rememberDatePickerState()

    var showEndDatePicker by remember { mutableStateOf(false) }
    var endDate by remember { mutableStateOf<Long?>(null) }
    val endDatePickerState = rememberDatePickerState()

    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val isValid = cultivo.isNotBlank() && startDate != null && endDate != null && startDate!! < endDate!!

    if (showStartDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showStartDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    startDate = startDatePickerState.selectedDateMillis
                    showStartDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showStartDatePicker = false }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = startDatePickerState)
        }
    }

    if (showEndDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showEndDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    endDate = endDatePickerState.selectedDateMillis
                    showEndDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showEndDatePicker = false }) { Text("Cancelar") }
            }
        ) {
            DatePicker(state = endDatePickerState)
        }
    }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Iniciar Nuevo Ciclo", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = cultivo,
                onValueChange = { cultivo = it },
                label = { Text("Cultivo (ej: Maíz Blanco)") },
                isError = cultivo.isBlank(),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedButton(onClick = { showStartDatePicker = true }, modifier = Modifier.fillMaxWidth()) {
                Text(startDate?.let { "Siembra: ${formatter.format(Date(it))}" } ?: "Seleccionar Fecha de Siembra")
            }

            OutlinedButton(onClick = { showEndDatePicker = true }, modifier = Modifier.fillMaxWidth()) {
                Text(endDate?.let { "Cosecha Estimada: ${formatter.format(Date(it))}" } ?: "Seleccionar Cosecha Estimada")
            }

            if (startDate != null && endDate != null && startDate!! >= endDate!!) {
                Text("La fecha de cosecha debe ser posterior a la de siembra", color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    if (isValid) {
                        onGuardar(
                            CropCycle(
                                terrainId = terrenoId,
                                species = cultivo,
                                startDate = startDate!!,
                                endDate = null, // Ongoing cycle
                                phenologicalState = PhenologicalState.SIEMBRA
                            )
                        )
                    }
                },
                enabled = isValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Ciclo")
            }
        }
    }
}
