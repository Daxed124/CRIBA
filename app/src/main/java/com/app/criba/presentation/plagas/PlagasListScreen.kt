package com.app.criba.presentation.plagas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.app.criba.domain.model.PestIncident
import com.app.criba.domain.model.Severity
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlagasListScreen(
    cicloId: Long,
    onNavigateToRegistrarPlaga: (Long) -> Unit,
    viewModel: PlagasViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(cicloId) {
        viewModel.cargarPlagas(cicloId)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToRegistrarPlaga(cicloId) }, containerColor = Color(0xFFC8A84B)) {
                Icon(Icons.Default.Add, contentDescription = "Registrar Plaga", tint = Color.White)
            }
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.plagas.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.BugReport, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("No hay plagas registradas en este ciclo", color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.plagas, key = { it.id }) { plaga ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = {
                            if (it == SwipeToDismissBoxValue.EndToStart) {
                                viewModel.eliminarPlaga(plaga.id)
                                true
                            } else false
                        }
                    )
                    
                    SwipeToDismissBox(
                        state = dismissState,
                        backgroundContent = {
                            Box(
                                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                            }
                        }
                    ) {
                        PlagaCard(plaga)
                    }
                }
            }
        }
    }
}

@Composable
fun PlagaCard(plaga: PestIncident) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            if (plaga.photoUri != null) {
                AsyncImage(
                    model = plaga.photoUri,
                    contentDescription = plaga.name,
                    modifier = Modifier.size(64.dp)
                )
            } else {
                Box(modifier = Modifier.size(64.dp), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.BugReport, contentDescription = null, tint = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(plaga.name, style = MaterialTheme.typography.titleMedium)
                Text(remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }.format(Date(plaga.date)), style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(4.dp))
                AssistChip(
                    onClick = { },
                    label = { Text(plaga.severity.displayName) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = when(plaga.severity) {
                            Severity.BAJO -> Color(0xFFD4EDDA)
                            Severity.MEDIO -> Color(0xFFFFF3CD)
                            Severity.CRITICO -> Color(0xFFF8D7DA)
                        }
                    )
                )
            }
        }
    }
}
