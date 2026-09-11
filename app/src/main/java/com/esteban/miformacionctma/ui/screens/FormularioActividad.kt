package com.esteban.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FormularioActividad(
    uiState: FormularioActividadUiState,
    onTituloChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onFechaChange: (String) -> Unit,
    onPrioridadChange: (String) -> Unit,
    onProgresoChange: (Int) -> Unit,
    onGuardar: () -> Unit
) {

    val prioridades = listOf("Baja", "Media", "Alta")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        OutlinedTextField(
            value = uiState.titulo,
            onValueChange = onTituloChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Título") },
            placeholder = { Text("Ej: Estudiar Scrum") },
            singleLine = true,
            isError = uiState.errorTitulo != null
        )
        uiState.errorTitulo?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        OutlinedTextField(
            value = uiState.descripcion,
            onValueChange = onDescripcionChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Descripción") },
            placeholder = { Text("Detalles de la actividad") },
            minLines = 3
        )

        OutlinedTextField(
            value = uiState.fecha,
            onValueChange = onFechaChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Fecha") },
            placeholder = { Text("Ej: 2026-09-08") },
            singleLine = true,
            isError = uiState.errorFecha != null
        )
        uiState.errorFecha?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Text(
            text = "Prioridad",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            prioridades.forEach { prioridad ->
                FilterChip(
                    selected = uiState.prioridad == prioridad,
                    onClick = { onPrioridadChange(prioridad) },
                    label = { Text(prioridad) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Progreso",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${uiState.progreso}%",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Slider(
                value = uiState.progreso.toFloat(),
                onValueChange = { onProgresoChange(it.toInt()) },
                valueRange = 0f..100f,
                steps = 9,
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        Spacer(Modifier.height(4.dp))

        Button(
            onClick = onGuardar,
            enabled = uiState.puedeGuardar,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(
                text = "Guardar",
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(24.dp))
    }
}