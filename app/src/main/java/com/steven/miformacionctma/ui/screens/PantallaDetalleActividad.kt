package com.steven.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.Prioridad
import com.steven.miformacionctma.domain.estadoActividad
import com.steven.miformacionctma.ui.theme.MiFormacionCTMATheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalleActividad(
    actividad: ActividadFormativa?,
    onVolverClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Detalle") })
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            if (actividad == null) {
                Text(
                    text = "No se encontró la actividad.",
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                Text(text = actividad.titulo, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))

                val estado = estadoActividad(actividad.progreso, actividad.diasRestantes)
                Text(
                    text = "$estado · ${actividad.prioridad.name}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))

                actividad.descripcion?.let {
                    Text(text = it, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                LinearProgressIndicator(
                    progress = { actividad.progreso / 100f },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${actividad.progreso}% completado",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PantallaDetalleActividadPreview() {
    MiFormacionCTMATheme {
        PantallaDetalleActividad(
            actividad = ActividadFormativa(
                1, "Laboratorio Compose", "Construir la pantalla de detalle", 65, 2, Prioridad.ALTA
            ),
            onVolverClick = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "No encontrada")
@Composable
fun PantallaDetalleActividadNoEncontradaPreview() {
    MiFormacionCTMATheme {
        PantallaDetalleActividad(actividad = null, onVolverClick = {})
    }
}