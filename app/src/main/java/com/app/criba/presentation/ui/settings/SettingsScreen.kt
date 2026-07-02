package com.app.criba.presentation.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.app.criba.presentation.viewmodel.SettingsViewModel
import com.app.criba.util.AppTexts

@Composable
fun SettingsScreen(
    onLoggedOut: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val user by viewModel.user.collectAsStateWithLifecycle()
    var showEditDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ===== Tarjeta de cuenta =====
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val photo = user?.photoUrl
                if (!photo.isNullOrBlank()) {
                    AsyncImage(
                        model = photo,
                        contentDescription = "Foto de perfil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(64.dp).clip(CircleShape)
                    )
                } else {
                    Icon(
                        Icons.Filled.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        user?.displayName ?: "Usuario",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        user?.email ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                IconButton(onClick = { showEditDialog = true }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Editar perfil")
                }
            }
        }

        // ===== Ayuda =====
        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.AutoMirrored.Filled.HelpOutline, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Ayuda", style = MaterialTheme.typography.titleMedium)
                    Text("Descargar el Manual de Usuario", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                Button(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(AppTexts.MANUAL_URL))
                    context.startActivity(intent)
                }) { Text("Abrir") }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // ===== Cerrar sesión =====
        Button(
            onClick = { viewModel.signOut(onLoggedOut) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Filled.Logout, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Cerrar Sesión")
        }
    }

    if (showEditDialog) {
        EditarPerfilDialog(
            nombreActual = user?.displayName ?: "",
            fotoActual = user?.photoUrl,
            onDismiss = { showEditDialog = false },
            onGuardar = { nombre, foto ->
                viewModel.updateProfile(nombre, foto)
                showEditDialog = false
            }
        )
    }
}

@Composable
fun EditarPerfilDialog(
    nombreActual: String,
    fotoActual: String?,
    onDismiss: () -> Unit,
    onGuardar: (String, String?) -> Unit
) {
    val context = LocalContext.current
    var nombre by remember { mutableStateOf(nombreActual) }
    var foto by remember { mutableStateOf(fotoActual) }

    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            // Intenta conservar el permiso de lectura para que la foto persista
            try {
                context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } catch (_: Exception) { }
            foto = uri.toString()
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar perfil") },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (!foto.isNullOrBlank()) {
                    AsyncImage(
                        model = foto,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(80.dp).clip(CircleShape)
                    )
                } else {
                    Icon(Icons.Filled.AccountCircle, contentDescription = null, modifier = Modifier.size(80.dp), tint = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }) { Text("Cambiar foto") }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onGuardar(nombre.trim(), foto) },
                enabled = nombre.isNotBlank()
            ) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}
