package com.app.criba.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.app.criba.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.criba.presentation.state.LoginUiState
import com.app.criba.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    var isRegisterMode by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var displayName by remember { mutableStateOf("") }
    var aceptaTerminos by remember { mutableStateOf(false) }
    var showTerminos by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        if (uiState is LoginUiState.Authenticated) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_criba),
            contentDescription = "Logo CRIBA",
            modifier = Modifier.size(160.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Gestión Agrícola Inteligente",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(48.dp))

        if (isRegisterMode) {
            OutlinedTextField(
                value = displayName,
                onValueChange = { displayName = it },
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        // Términos y condiciones (obligatorio para registrarse)
        if (isRegisterMode) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(checked = aceptaTerminos, onCheckedChange = { aceptaTerminos = it })
                Text("Acepto los ", style = MaterialTheme.typography.bodySmall)
                Text(
                    "términos y condiciones",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { showTerminos = true }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = uiState) {
            is LoginUiState.Loading -> {
                CircularProgressIndicator()
            }
            is LoginUiState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            else -> {}
        }

        // Los campos son obligatorios: el botón se habilita solo si están completos
        val isFormValid = if (isRegisterMode) {
            displayName.isNotBlank() && email.isNotBlank() && password.isNotBlank() && aceptaTerminos
        } else {
            email.isNotBlank() && password.isNotBlank()
        }

        Button(
            onClick = {
                if (isRegisterMode) {
                    viewModel.register(email, password, displayName)
                } else {
                    viewModel.login(email, password)
                }
            },
            enabled = isFormValid && uiState !is LoginUiState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isRegisterMode) "Crear Cuenta" else "Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { isRegisterMode = !isRegisterMode }) {
            Text(if (isRegisterMode) "¿Ya tienes cuenta? Inicia Sesión" else "¿No tienes cuenta? Regístrate")
        }
    }

    if (showTerminos) {
        AlertDialog(
            onDismissRequest = { showTerminos = false },
            title = { Text("Términos y Condiciones") },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        com.app.criba.util.AppTexts.TERMINOS_Y_CONDICIONES,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { aceptaTerminos = true; showTerminos = false }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTerminos = false }) { Text("Cerrar") }
            }
        )
    }
}
