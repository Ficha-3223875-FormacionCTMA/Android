package com.steven.miformacionctma.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.Prioridad
import com.steven.miformacionctma.domain.estadoActividad
import com.steven.miformacionctma.ui.theme.MiFormacionCTMATheme

@Composable
fun TarjetaActividad(
    actividad: ActividadFormativa,
    onClick: (ActividadFormativa) -> Unit
) {
    val estado = estadoActividad(actividad.progreso, actividad.diasRestantes)
    val descripcionSemantica = "${actividad.titulo}, estado: $estado, progreso: ${actividad.progreso} por ciento"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick(actividad) }
            .semantics { contentDescription = descripcionSemantica },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = actividad.titulo,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row {
                Text(
                    text = estado,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "· ${actividad.prioridad.name}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

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

@Preview(showBackground = true)
@Composable
fun TarjetaActividadPreview() {
    MiFormacionCTMATheme {
        TarjetaActividad(
            actividad = ActividadFormativa(
                id = 1,
                titulo = "Laboratorio Compose",
                descripcion = null,
                progreso = 65,
                diasRestantes = 2,
                prioridad = Prioridad.ALTA
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TarjetaActividadTituloLargoPreview() {
    MiFormacionCTMATheme {
        TarjetaActividad(
            actividad = ActividadFormativa(
                id = 2,
                titulo = "Documentar el alcance inicial del proyecto integrador completo de la ficha",
                descripcion = null,
                progreso = 100,
                diasRestantes = -1,
                prioridad = Prioridad.BAJA
            ),
            onClick = {}
        )
    }
}