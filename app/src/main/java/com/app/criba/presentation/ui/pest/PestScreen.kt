package com.app.criba.presentation.ui.pest

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
import com.app.criba.domain.model.PestIncident
import com.app.criba.domain.model.Severity
import com.app.criba.presentation.viewmodel.PestViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PestScreen(
    cycleId: Long,
    onNavigateBack: () -> Unit,
    viewModel: PestViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reportes de Plagas") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::toggleForm) {
                Icon(Icons.Filled.Add, contentDescription = "Reportar Plaga")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            if (uiState.isFormVisible) {
                PestForm(viewModel, uiState)
            } else {
                if (uiState.pests.isEmpty()) {
                    Text("No hay incidentes reportados.", modifier = Modifier.padding(16.dp))
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(uiState.pests) { pest ->
                            PestItem(pest)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PestForm(viewModel: PestViewModel, uiState: com.app.criba.presentation.state.PestUiState) {
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = uiState.name,
            onValueChange = viewModel::onNameChange,
            label = { Text("Nombre de la plaga") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = uiState.severity,
                onValueChange = {},
                readOnly = true,
                label = { Text("Severidad") },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Severity.values().forEach { severity ->
                    DropdownMenuItem(
                        text = { Text(severity.name) },
                        onClick = {
                            viewModel.onSeverityChange(severity.name)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.description,
            onValueChange = viewModel::onDescriptionChange,
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = viewModel::savePest, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar Reporte")
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(onClick = viewModel::toggleForm, modifier = Modifier.fillMaxWidth()) {
            Text("Cancelar")
        }
    }
}

@Composable
fun PestItem(pest: PestIncident) {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val severityColor = when (pest.severity) {
        Severity.BAJO -> MaterialTheme.colorScheme.primary
        Severity.MEDIO -> MaterialTheme.colorScheme.secondary
        Severity.CRITICO -> MaterialTheme.colorScheme.error
    }
    
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(text = pest.name, style = MaterialTheme.typography.titleMedium)
                Text(text = pest.severity.name, color = severityColor, style = MaterialTheme.typography.titleSmall)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = pest.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = formatter.format(Date(pest.date)), style = MaterialTheme.typography.bodySmall)
        }
    }
}
