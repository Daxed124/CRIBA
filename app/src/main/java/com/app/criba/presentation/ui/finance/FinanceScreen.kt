package com.app.criba.presentation.ui.finance

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.app.criba.domain.model.ExpenseCategory
import com.app.criba.domain.model.Transaction
import com.app.criba.presentation.viewmodel.FinanceViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceScreen(
    cycleId: Long,
    onNavigateBack: () -> Unit,
    viewModel: FinanceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Finanzas del Ciclo") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::toggleForm) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir Transacción")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            if (uiState.isFormVisible) {
                FinanceForm(viewModel, uiState)
            } else {
                if (uiState.transactions.isEmpty()) {
                    Text("No hay transacciones.", modifier = Modifier.padding(16.dp))
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(uiState.transactions) { tx ->
                            TransactionItem(tx)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceForm(viewModel: FinanceViewModel, uiState: com.app.criba.presentation.state.FinanceUiState) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row {
            RadioButton(selected = uiState.isIncome, onClick = { viewModel.onTypeChange(true) })
            Text("Ingreso", modifier = Modifier.padding(top = 12.dp, end = 16.dp))
            RadioButton(selected = !uiState.isIncome, onClick = { viewModel.onTypeChange(false) })
            Text("Gasto", modifier = Modifier.padding(top = 12.dp))
        }
        
        OutlinedTextField(
            value = uiState.amount,
            onValueChange = viewModel::onAmountChange,
            label = { Text("Monto") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        
        if (!uiState.isIncome) {
            // Dropdown for category
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = uiState.category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoría") },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    ExpenseCategory.values().forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat.name) },
                            onClick = {
                                viewModel.onCategoryChange(cat.name)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        OutlinedTextField(
            value = uiState.description,
            onValueChange = viewModel::onDescriptionChange,
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = viewModel::saveTransaction, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar Transacción")
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(onClick = viewModel::toggleForm, modifier = Modifier.fillMaxWidth()) {
            Text("Cancelar")
        }
    }
}

@Composable
fun TransactionItem(tx: Transaction) {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val color = if (tx.type.name == "INGRESO") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
    
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(text = tx.description, style = MaterialTheme.typography.titleMedium)
                Text(text = "${formatter.format(Date(tx.date))} ${tx.category?.name ?: ""}", style = MaterialTheme.typography.bodySmall)
            }
            Text(
                text = "$${tx.amount}",
                style = MaterialTheme.typography.titleMedium,
                color = color
            )
        }
    }
}
