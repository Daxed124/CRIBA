package com.app.criba.presentation.ui.historial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.criba.presentation.ui.climate.ClimateItem
import com.app.criba.presentation.ui.climate.ClimaMiniCard
import com.app.criba.presentation.ui.climate.ChartCard
import com.app.criba.presentation.ui.climate.components.LluviaChart
import com.app.criba.presentation.ui.climate.components.TemperaturaChart
import com.app.criba.presentation.viewmodel.HistorialClimaViewModel
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialScreen(
    viewModel: HistorialClimaViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val formatter = remember {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply { timeZone = TimeZone.getTimeZone("UTC") }
    }

    if (!state.isLoading && state.cycles.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Aún no hay ciclos. Crea un terreno y un ciclo para ver su historial climático.")
        }
        return
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        // Selector de ciclo
        item {
            var expanded by remember { mutableStateOf(false) }
            val seleccionado = state.selectedCycle
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = seleccionado?.let { "${it.species} — ${formatter.format(Date(it.startDate))}" } ?: "Selecciona un ciclo",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Ciclo") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    state.cycles.forEach { cycle ->
                        DropdownMenuItem(
                            text = { Text("${cycle.species} — ${formatter.format(Date(cycle.startDate))}") },
                            onClick = {
                                viewModel.selectCycle(cycle)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        // Resumen
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ClimaMiniCard("Temp. Promedio", String.format(Locale.US, "%.1f °C", state.resumen.promedioTemp))
                ClimaMiniCard("Lluvia Acumulada", String.format(Locale.US, "%.0f mm", state.resumen.lluviaAcumulada))
                ClimaMiniCard("Días sin lluvia", "${state.resumen.diasSinLluvia}")
                ClimaMiniCard("Sequía Actual", state.resumen.etapaActual.displayName)
            }
        }

        // Gráficas
        item {
            ChartCard(title = "TEMPERATURA (°C)") {
                TemperaturaChart(state.records, Modifier.fillMaxWidth().height(180.dp))
            }
        }
        item {
            ChartCard(title = "PRECIPITACIÓN (mm)") {
                LluviaChart(state.records, Modifier.fillMaxWidth().height(150.dp))
            }
        }

        if (state.records.isEmpty()) {
            item {
                Text(
                    "Este ciclo no tiene registros climáticos.",
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            item {
                Text(
                    "REGISTROS",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp)
                )
            }
            items(state.records) { record -> ClimateItem(record) }
        }
    }
}
