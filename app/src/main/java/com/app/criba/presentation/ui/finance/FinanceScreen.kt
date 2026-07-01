package com.app.criba.presentation.ui.finance

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
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item { ResumenFinancieroSection(uiState.resumen) }
                    if (uiState.transactions.isEmpty()) {
                        item { Text("No hay transacciones.", modifier = Modifier.padding(16.dp)) }
                    } else {
                        item {
                            Text(
                                "TRANSACCIONES",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 4.dp)
                            )
                        }
                        items(uiState.transactions) { tx ->
                            TransactionItem(tx)
                        }
                    }
                }
            }
        }
    }
}

private val moneyFormat = java.text.DecimalFormat("$ #,##0.00")

fun formatMoney(value: Double): String = moneyFormat.format(value)

fun formatRoi(roi: Double): String = String.format(Locale.US, "%+.1f%%", roi)

@Composable
fun ResumenFinancieroSection(resumen: com.app.criba.domain.model.ResumenFinanciero) {
    val positivo = MaterialTheme.colorScheme.primary
    val negativo = MaterialTheme.colorScheme.error

    Column(modifier = Modifier.padding(16.dp)) {
        // Fila de KPIs (scroll horizontal)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            KpiCard("Utilidad Neta", formatMoney(resumen.utilidadNeta), if (resumen.utilidadNeta >= 0) positivo else negativo)
            KpiCard("ROI", formatRoi(resumen.roi), if (resumen.roi >= 0) positivo else negativo)
            KpiCard("Ingresos", formatMoney(resumen.totalIngresos), positivo)
            KpiCard("Gastos", formatMoney(resumen.totalGastos), negativo)
            resumen.eficienciaRendimiento?.let {
                KpiCard("Erend", String.format(Locale.US, "%.1f Kg/ha", it), positivo)
            }
        }

        // Desglose de gastos por categoría
        if (resumen.gastosPorCategoria.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("DESGLOSE DE GASTOS", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(8.dp))
            resumen.gastosPorCategoria.entries.sortedByDescending { it.value }.forEach { (cat, monto) ->
                val pct = resumen.porcentajeGastoPorCategoria[cat] ?: 0.0
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(cat, style = MaterialTheme.typography.bodyMedium)
                        Text(
                            "${formatMoney(monto)}  (${String.format(Locale.US, "%.0f", pct)}%)",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    LinearProgressIndicator(
                        progress = { (pct / 100.0).toFloat() },
                        modifier = Modifier.fillMaxWidth().height(6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun KpiCard(label: String, value: String, valueColor: androidx.compose.ui.graphics.Color) {
    Card(
        modifier = Modifier.width(150.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.labelMedium, color = androidx.compose.ui.graphics.Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.titleLarge, color = valueColor)
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
            prefix = { Text("$") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
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
                    value = ExpenseCategory.entries.find { it.name == uiState.category }?.displayName ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoría") },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    ExpenseCategory.entries.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat.displayName) },
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
    val formatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    val isIncome = tx.type == com.app.criba.domain.model.TransactionType.INGRESO
    val color = if (isIncome) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
    val signo = if (isIncome) "+" else "−"

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(text = tx.description.ifBlank { if (isIncome) "Ingreso" else "Gasto" }, style = MaterialTheme.typography.titleMedium)
                Text(text = "${formatter.format(Date(tx.date))}  ${tx.category?.displayName ?: ""}", style = MaterialTheme.typography.bodySmall)
            }
            Text(
                text = "$signo ${formatMoney(tx.amount)}",
                style = MaterialTheme.typography.titleMedium,
                color = color
            )
        }
    }
}
