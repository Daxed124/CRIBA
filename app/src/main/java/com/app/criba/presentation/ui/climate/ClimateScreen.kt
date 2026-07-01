package com.app.criba.presentation.ui.climate

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.criba.domain.model.ClimaResumen
import com.app.criba.domain.model.ClimateRecord
import com.app.criba.domain.model.DroughtStage
import com.app.criba.presentation.ui.climate.components.LluviaChart
import com.app.criba.presentation.ui.climate.components.TemperaturaChart
import com.app.criba.presentation.viewmodel.ClimateViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClimateScreen(
    cycleId: Long,
    onNavigateBack: () -> Unit,
    viewModel: ClimateViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registros Climáticos") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::toggleForm) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir Registro")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            if (uiState.isFormVisible) {
                ClimateForm(viewModel, uiState)
            } else {
                ClimaContenido(records = uiState.records, resumen = uiState.resumen)
            }
        }
    }
}

@Composable
fun ClimaContenido(records: List<ClimateRecord>, resumen: ClimaResumen) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { ResumenClimaRow(resumen) }

        item {
            ChartCard(title = "TEMPERATURA (°C)") {
                TemperaturaChart(records, Modifier.fillMaxWidth().height(180.dp))
            }
        }
        item {
            ChartCard(title = "PRECIPITACIÓN (mm)") {
                LluviaChart(records, Modifier.fillMaxWidth().height(150.dp))
            }
        }

        if (records.isEmpty()) {
            item { Text("No hay registros.", modifier = Modifier.padding(16.dp)) }
        } else {
            item {
                Text(
                    "REGISTROS",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp)
                )
            }
            items(records) { record -> ClimateItem(record) }
        }
    }
}

@Composable
fun ResumenClimaRow(resumen: ClimaResumen) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ClimaMiniCard("Temp. Promedio", String.format(Locale.US, "%.1f °C", resumen.promedioTemp))
        ClimaMiniCard("Lluvia Acumulada", String.format(Locale.US, "%.0f mm", resumen.lluviaAcumulada))
        ClimaMiniCard("Días sin lluvia", "${resumen.diasSinLluvia}")
        ClimaMiniCard("Sequía Actual", resumen.etapaActual.displayName)
    }
}

@Composable
fun ClimaMiniCard(label: String, value: String) {
    Card(
        modifier = Modifier.width(140.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(label, style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun ChartCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClimateForm(viewModel: ClimateViewModel, uiState: com.app.criba.presentation.state.ClimateUiState) {
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = uiState.rainfall,
            onValueChange = viewModel::onRainfallChange,
            label = { Text("Precipitación (mm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.temperature,
            onValueChange = viewModel::onTemperatureChange,
            label = { Text("Temperatura Media (°C)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = uiState.droughtStage,
                onValueChange = {},
                readOnly = true,
                label = { Text("Etapa de Sequía") },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DroughtStage.values().forEach { stage ->
                    DropdownMenuItem(
                        text = { Text(stage.name) },
                        onClick = {
                            viewModel.onDroughtStageChange(stage.name)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = viewModel::saveRecord, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar Registro")
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(onClick = viewModel::toggleForm, modifier = Modifier.fillMaxWidth()) {
            Text("Cancelar")
        }
    }
}

@Composable
fun ClimateItem(record: ClimateRecord) {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = formatter.format(Date(record.date)), style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Lluvia: ${record.rainfall}mm")
                Text(text = "Temp: ${record.temperature}°C")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Sequía: ${record.droughtStage.name}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
