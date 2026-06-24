package com.app.criba.presentation.ui.cycle

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.criba.domain.model.CropCycle
import com.app.criba.presentation.viewmodel.CycleViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CycleScreen(
    terrainId: Long,
    onNavigateBack: () -> Unit,
    onNavigateToFinance: (Long) -> Unit,
    onNavigateToPest: (Long) -> Unit,
    onNavigateToClimate: (Long) -> Unit,
    onNavigateToDashboard: (Long) -> Unit,
    viewModel: CycleViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ciclos de Cultivo") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::toggleForm) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir Ciclo")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            if (uiState.isFormVisible) {
                CycleForm(viewModel, uiState)
            } else {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                } else if (uiState.cycles.isEmpty()) {
                    Text("No hay ciclos registrados.", modifier = Modifier.padding(16.dp))
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(uiState.cycles) { cycle ->
                            CycleItem(
                                cycle = cycle,
                                onFinanceClick = { onNavigateToFinance(cycle.id) },
                                onPestClick = { onNavigateToPest(cycle.id) },
                                onClimateClick = { onNavigateToClimate(cycle.id) },
                                onDashboardClick = { onNavigateToDashboard(cycle.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CycleForm(viewModel: CycleViewModel, uiState: com.app.criba.presentation.state.CycleUiState) {
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = uiState.species,
            onValueChange = viewModel::onSpeciesChange,
            label = { Text("Especie") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = viewModel::saveCycle, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar Ciclo")
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(onClick = viewModel::toggleForm, modifier = Modifier.fillMaxWidth()) {
            Text("Cancelar")
        }
    }
}

@Composable
fun CycleItem(
    cycle: CropCycle,
    onFinanceClick: () -> Unit,
    onPestClick: () -> Unit,
    onClimateClick: () -> Unit,
    onDashboardClick: () -> Unit
) {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onDashboardClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = cycle.species, style = MaterialTheme.typography.titleLarge)
            Text(text = "Estado: ${cycle.phenologicalState.name}")
            Text(text = "Inicio: ${formatter.format(Date(cycle.startDate))}")
            
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = onFinanceClick) { Text("Finanzas") }
                TextButton(onClick = onPestClick) { Text("Plagas") }
                TextButton(onClick = onClimateClick) { Text("Clima") }
            }
        }
    }
}
