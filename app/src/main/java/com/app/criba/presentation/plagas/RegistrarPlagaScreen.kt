package com.app.criba.presentation.plagas

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.app.criba.domain.model.PestIncident
import com.app.criba.domain.model.Severity
import com.app.criba.util.CameraHelper
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun RegistrarPlagaScreen(
    cicloId: Long,
    onNavigateBack: () -> Unit,
    viewModel: PlagasViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cameraHelper = remember { CameraHelper(context) }
    
    var nombrePlaga by remember { mutableStateOf("") }
    var severidad by remember { mutableStateOf<Severity?>(null) }
    var fotoUri by remember { mutableStateOf<Uri?>(null) }
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }
    
    var expanded by remember { mutableStateOf(false) }
    val plagasSugeridas = listOf("Pulgón", "Trips", "Mosca Blanca", "Roya", "Fusarium", "Tizón", "Araña Roja", "Nematodos", "Otro")
    var descripcion by remember { mutableStateOf("") }

    val isValid = nombrePlaga.isNotBlank() && severidad != null

    val takePictureLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        val currentUri = tempCameraUri
        if (success && currentUri != null) {
            fotoUri = currentUri
        }
    }

    val pickMediaLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            fotoUri = uri
        }
    }

    // Permisos en runtime (Android 6+): la cámara y el GPS no funcionan sin concesión explícita.
    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)
    val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    val lanzarCamara = {
        val file = cameraHelper.createImageFile()
        val uri = cameraHelper.getUriForFile(file)
        tempCameraUri = uri
        takePictureLauncher.launch(uri)
    }

    LaunchedEffect(Unit) {
        viewModel.clearLocation()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Incidencia") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1B4332),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Tipo y Severidad", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = nombrePlaga,
                        onValueChange = { nombrePlaga = it },
                        label = { Text("Nombre de Plaga o Enfermedad") },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        plagasSugeridas.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    nombrePlaga = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción / Observaciones") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )
            }

            item {
                Text("Severidad", style = MaterialTheme.typography.labelLarge)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = severidad == Severity.BAJO,
                        onClick = { severidad = Severity.BAJO },
                        label = { Text("BAJO") },
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Color(0xFFD4EDDA))
                    )
                    FilterChip(
                        selected = severidad == Severity.MEDIO,
                        onClick = { severidad = Severity.MEDIO },
                        label = { Text("MEDIO") },
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Color(0xFFFFF3CD))
                    )
                    FilterChip(
                        selected = severidad == Severity.CRITICO,
                        onClick = { severidad = Severity.CRITICO },
                        label = { Text("CRÍTICO") },
                        colors = FilterChipDefaults.filterChipColors(selectedContainerColor = Color(0xFFF8D7DA))
                    )
                }
            }

            item {
                Text("Evidencia Fotográfica", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (fotoUri == null) {
                            Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color.Gray)
                            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                Button(onClick = {
                                    if (cameraPermission.status.isGranted) {
                                        lanzarCamara()
                                    } else {
                                        cameraPermission.launchPermissionRequest()
                                    }
                                }) {
                                    Icon(Icons.Default.CameraAlt, contentDescription = null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Cámara")
                                }
                                OutlinedButton(onClick = {
                                    pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                }) {
                                    Icon(Icons.Default.Image, contentDescription = null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Galería")
                                }
                            }
                        } else {
                            AsyncImage(
                                model = fotoUri,
                                contentDescription = "Evidencia",
                                modifier = Modifier.fillMaxWidth().height(200.dp)
                            )
                            OutlinedButton(onClick = { fotoUri = null }) {
                                Text("Quitar Foto")
                            }
                        }
                    }
                }
            }

            item {
                Text("Ubicación GPS", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (uiState.isLocationLoading) {
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                            Text("Detectando ubicación...", modifier = Modifier.padding(top = 8.dp))
                        } else if (uiState.currentLocation != null) {
                            Text("Lat: ${uiState.currentLocation!!.first} | Lng: ${uiState.currentLocation!!.second}")
                        } else {
                            Button(onClick = {
                                if (locationPermission.status.isGranted) {
                                    viewModel.obtenerUbicacionActual()
                                } else {
                                    locationPermission.launchPermissionRequest()
                                }
                            }) {
                                Icon(Icons.Default.LocationOn, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Detectar Ubicación")
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (isValid) {
                            viewModel.registrarPlaga(
                                PestIncident(
                                    cycleId = cicloId,
                                    name = nombrePlaga,
                                    severity = severidad!!,
                                    description = descripcion,
                                    photoUri = fotoUri?.toString(),
                                    latitude = uiState.currentLocation?.first,
                                    longitude = uiState.currentLocation?.second,
                                    date = System.currentTimeMillis()
                                ),
                                fotoUri
                            )
                            onNavigateBack()
                        }
                    },
                    enabled = isValid,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar Plaga")
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
