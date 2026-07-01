package com.app.criba.presentation.ui.parcelas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.criba.domain.model.CropCycle
import com.app.criba.presentation.state.ParcelasUiState
import com.app.criba.presentation.viewmodel.ParcelasViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleParcelaScreen(
    terrenoId: Long,
    onNavigateBack: () -> Unit,
    onNavigateToPlagas: (Long) -> Unit,
    onNavigateToClima: (Long) -> Unit,
    onNavigateToFinanzas: (Long) -> Unit,
    viewModel: ParcelasViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val terrenoData = uiState.terrenos.find { it.terrain.id == terrenoId }

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Ciclo Actual", "Historial", "Mapa")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(terrenoData?.terrain?.name ?: "Detalle de Parcela") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (terrenoData == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Cargando o terreno no encontrado...")
                }
                return@Scaffold
            }

            // Header Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Hectáreas: ${terrenoData.terrain.surface}")
                    Text("Tipo de suelo: ${terrenoData.terrain.soilType}")
                    Text("Coordenadas: Lat ${terrenoData.terrain.latitude} | Lng ${terrenoData.terrain.longitude}")
                }
            }

            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> CicloActualTab(
                    activeCycle = terrenoData.activeCycle,
                    onNavigateToPlagas = onNavigateToPlagas,
                    onNavigateToClima = onNavigateToClima,
                    onNavigateToFinanzas = onNavigateToFinanzas,
                    onIniciarCiclo = { viewModel.toggleNuevoCicloSheet(true, terrenoId) },
                    onCosechar = { volumen ->
                        terrenoData.activeCycle?.let { viewModel.cerrarCiclo(it, volumen) }
                    }
                )
                1 -> HistorialTab(terrenoData.cycles.filter { it.endDate != null })
                2 -> MapaTab(terrenoData.terrain)
            }

            if (uiState.isNuevoCicloSheetVisible) {
                com.app.criba.presentation.ui.parcelas.components.NuevoCicloSheet(
                    terrenoId = terrenoId,
                    onDismiss = { viewModel.toggleNuevoCicloSheet(false) },
                    onGuardar = { viewModel.iniciarCiclo(it) }
                )
            }
        }
    }
}

@Composable
fun CicloActualTab(
    activeCycle: CropCycle?,
    onNavigateToPlagas: (Long) -> Unit,
    onNavigateToClima: (Long) -> Unit,
    onNavigateToFinanzas: (Long) -> Unit,
    onIniciarCiclo: () -> Unit,
    onCosechar: (Double?) -> Unit
) {
    var showCosecharDialog by remember { mutableStateOf(false) }

    if (showCosecharDialog) {
        var volumenTexto by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showCosecharDialog = false },
            title = { Text("Cosechar / Cerrar ciclo") },
            text = {
                Column {
                    Text("Se cerrará el ciclo con la fecha de hoy y pasará al Historial.")
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = volumenTexto,
                        onValueChange = { volumenTexto = it },
                        label = { Text("Volumen cosechado (Kg) — opcional") },
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onCosechar(volumenTexto.toDoubleOrNull())
                    showCosecharDialog = false
                }) { Text("Cosechar") }
            },
            dismissButton = {
                TextButton(onClick = { showCosecharDialog = false }) { Text("Cancelar") }
            }
        )
    }

    if (activeCycle == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Sin ciclo activo", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onIniciarCiclo) {
                Text("Iniciar Ciclo")
            }
        }
    } else {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val diasTranscurridos = ((System.currentTimeMillis() - activeCycle.startDate) / (1000 * 60 * 60 * 24)).toInt()
        val progreso = (diasTranscurridos.toFloat() / 120f).coerceIn(0f, 1f) // Asumiendo ciclo 120 días

        Column(modifier = Modifier.padding(16.dp)) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(activeCycle.species, style = MaterialTheme.typography.titleLarge)
                    Text("Fecha Siembra: ${formatter.format(Date(activeCycle.startDate))}")
                    Text("Días transcurridos: $diasTranscurridos")
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(progress = { progreso }, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                    AssistChip(
                        onClick = { },
                        label = { Text(activeCycle.phenologicalState.name) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { onNavigateToPlagas(activeCycle.id) }) { Text("🌿 Plagas") }
                Button(onClick = { onNavigateToClima(activeCycle.id) }) { Text("🌧 Clima") }
                Button(onClick = { onNavigateToFinanzas(activeCycle.id) }) { Text("💰 Finanzas") }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { showCosecharDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
            ) {
                Text("🌾 Cosechar / Cerrar ciclo")
            }
        }
    }
}

@Composable
fun HistorialTab(pastCycles: List<CropCycle>) {
    if (pastCycles.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No hay ciclos anteriores.")
        }
    } else {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pastCycles) { cycle ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(cycle.species, style = MaterialTheme.typography.titleMedium)
                        Text("Siembra: ${formatter.format(Date(cycle.startDate))}")
                        Text("Cosecha: ${cycle.endDate?.let { formatter.format(Date(it)) } ?: "N/A"}")
                        AssistChip(onClick = {}, label = { Text("COSECHADO") })
                    }
                }
            }
        }
    }
}

@Composable
fun MapaTab(terrain: com.app.criba.domain.model.Terrain) {
    com.app.criba.presentation.ui.map.MapaTerrenoMini(
        latitud = terrain.latitude,
        longitud = terrain.longitude,
        nombre = terrain.name,
        modifier = Modifier.fillMaxSize()
    )
}
