package com.esteban.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Nueva actividad"
        )

        OutlinedTextField(
            value = uiState.titulo,
            onValueChange = onTituloChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Título")
            },
            isError = uiState.errorTitulo != null
        )

        uiState.errorTitulo?.let {
            Text(
                text = it
            )
        }

        OutlinedTextField(
            value = uiState.descripcion,
            onValueChange = onDescripcionChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Descripción")
            }
        )

        OutlinedTextField(
            value = uiState.fecha,
            onValueChange = onFechaChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Fecha")
            },
            isError = uiState.errorFecha != null
        )

        uiState.errorFecha?.let {
            Text(
                text = it
            )
        }

        Text(
            text = "Prioridad: ${uiState.prioridad}"
        )

        Button(
            onClick = {
                onPrioridadChange("Baja")
            }
        ) {
            Text("Baja")
        }

        Button(
            onClick = {
                onPrioridadChange("Media")
            }
        ) {
            Text("Media")
        }

        Button(
            onClick = {
                onPrioridadChange("Alta")
            }
        ) {
            Text("Alta")
        }

        Text(
            text = "Progreso: ${uiState.progreso}%"
        )

        Slider(
            value = uiState.progreso.toFloat(),
            onValueChange = {
                onProgresoChange(it.toInt())
            },
            valueRange = 0f..100f,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            onClick = onGuardar,
            enabled = uiState.puedeGuardar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}