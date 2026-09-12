package com.steven.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.steven.miformacionctma.domain.FormularioActividadUiState
import com.steven.miformacionctma.domain.Prioridad
import com.steven.miformacionctma.ui.theme.MiFormacionCTMATheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioActividad(
    uiState: FormularioActividadUiState,
    onTituloChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onFechaChange: (String) -> Unit,
    onProgresoChange: (String) -> Unit,
    onPrioridadChange: (Prioridad) -> Unit,
    onGuardarClick: () -> Unit,
    onCancelarClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Nueva actividad") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = uiState.titulo,
                onValueChange = onTituloChange,
                label = { Text("Título") },
                isError = uiState.errorTitulo != null,
                supportingText = {
                    uiState.errorTitulo?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.descripcion,
                onValueChange = onDescripcionChange,
                label = { Text("Descripción (opcional)") },
                isError = uiState.errorDescripcion != null,
                supportingText = {
                    uiState.errorDescripcion?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.fecha,
                onValueChange = onFechaChange,
                label = { Text("Fecha límite (AAAA-MM-DD)") },
                isError = uiState.errorFecha != null,
                supportingText = {
                    uiState.errorFecha?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.progreso,
                onValueChange = onProgresoChange,
                label = { Text("Progreso (0-100)") },
                isError = uiState.errorProgreso != null,
                supportingText = {
                    uiState.errorProgreso?.let { Text(it) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            SelectorPrioridad(
                prioridadSeleccionada = uiState.prioridad,
                onPrioridadChange = onPrioridadChange
            )

            Button(
                onClick = onGuardarClick,
                enabled = uiState.puedeGuardar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (uiState.guardando) "Guardando..." else "Guardar")
            }

            Button(
                onClick = onCancelarClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectorPrioridad(
    prioridadSeleccionada: Prioridad,
    onPrioridadChange: (Prioridad) -> Unit
) {
    var expandido by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = { expandido = it }
    ) {
        OutlinedTextField(
            value = prioridadSeleccionada.name,
            onValueChange = {},
            readOnly = true,
            label = { Text("Prioridad") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )
        DropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            Prioridad.entries.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion.name) },
                    onClick = {
                        onPrioridadChange(opcion)
                        expandido = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun FormularioActividadPreview() {
    MiFormacionCTMATheme {
        FormularioActividad(
            uiState = FormularioActividadUiState(titulo = "Laboratorio Compose", progreso = "50"),
            onTituloChange = {},
            onDescripcionChange = {},
            onFechaChange = {},
            onProgresoChange = {},
            onPrioridadChange = {},
            onGuardarClick = {},
            onCancelarClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Con errores")
@Composable
fun FormularioActividadConErroresPreview() {
    MiFormacionCTMATheme {
        FormularioActividad(
            uiState = FormularioActividadUiState(
                titulo = "",
                errorTitulo = "El título es obligatorio",
                fecha = "2020-01-01",
                errorFecha = "La fecha no puede ser anterior a hoy"
            ),
            onTituloChange = {},
            onDescripcionChange = {},
            onFechaChange = {},
            onProgresoChange = {},
            onPrioridadChange = {},
            onGuardarClick = {},
            onCancelarClick = {}
        )
    }
}