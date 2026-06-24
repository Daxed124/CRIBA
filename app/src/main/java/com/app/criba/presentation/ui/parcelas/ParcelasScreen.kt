package com.app.criba.presentation.ui.parcelas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.criba.domain.model.TerrainWithCycles
import com.app.criba.presentation.state.ParcelasUiState
import com.app.criba.presentation.ui.parcelas.components.NuevoCicloSheet
import com.app.criba.presentation.ui.parcelas.components.NuevoTerrenoSheet
import com.app.criba.presentation.viewmodel.ParcelasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParcelasScreen(
    viewModel: ParcelasViewModel = hiltViewModel(),
    onNavigateToDetalle: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.toggleNuevoTerrenoSheet(true) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Nueva Parcela")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF8F7F2))
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.terrenos.isEmpty()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Sin parcelas registradas", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.toggleNuevoTerrenoSheet(true) }) {
                        Text("Agregar primera parcela")
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.terrenos, key = { it.terrain.id }) { terrenoData ->
                        SwipeToDeleteTerrainCard(
                            terrenoData = terrenoData,
                            onDelete = { viewModel.eliminarTerreno(terrenoData.terrain.id) },
                            onCardClick = { onNavigateToDetalle(terrenoData.terrain.id) },
                            onAddCycleClick = { viewModel.toggleNuevoCicloSheet(true, terrenoData.terrain.id) }
                        )
                    }
                }
            }

            if (uiState.isNuevoTerrenoSheetVisible) {
                NuevoTerrenoSheet(
                    onDismiss = { viewModel.toggleNuevoTerrenoSheet(false) },
                    onGuardar = { viewModel.agregarTerreno(it) }
                )
            }

            if (uiState.isNuevoCicloSheetVisible && uiState.terrenoSeleccionadoId != null) {
                NuevoCicloSheet(
                    terrenoId = uiState.terrenoSeleccionadoId!!,
                    onDismiss = { viewModel.toggleNuevoCicloSheet(false) },
                    onGuardar = { viewModel.iniciarCiclo(it) }
                )
            }
            
            uiState.error?.let { errorMsg ->
                Snackbar(modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)) {
                    Text(errorMsg)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteTerrainCard(
    terrenoData: TerrainWithCycles,
    onDelete: () -> Unit,
    onCardClick: () -> Unit,
    onAddCycleClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                showDialog = true
            }
            false // Don't dismiss immediately, wait for dialog
        }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Eliminar Parcela") },
            text = { Text("¿Estás seguro de que deseas eliminar la parcela ${terrenoData.terrain.name}?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onDelete()
                }) { Text("Eliminar", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancelar") }
            }
        )
    }

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.error, shape = MaterialTheme.shapes.medium)
                    .padding(end = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(Icons.Filled.Delete, contentDescription = "Eliminar", tint = Color.White)
            }
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onCardClick),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(terrenoData.terrain.name, style = MaterialTheme.typography.titleLarge)
                    IconButton(onClick = onAddCycleClick) {
                        Icon(Icons.Filled.Add, contentDescription = "Añadir Ciclo", tint = MaterialTheme.colorScheme.primary)
                    }
                }
                Text("${terrenoData.terrain.surface} ha", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                if (terrenoData.activeCycle != null) {
                    AssistChip(
                        onClick = { },
                        label = { Text(terrenoData.activeCycle!!.species) },
                        colors = AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    )
                } else {
                    AssistChip(
                        onClick = { },
                        label = { Text("Sin cultivo") },
                        colors = AssistChipDefaults.assistChipColors(containerColor = Color.LightGray)
                    )
                }
            }
        }
    }
}
