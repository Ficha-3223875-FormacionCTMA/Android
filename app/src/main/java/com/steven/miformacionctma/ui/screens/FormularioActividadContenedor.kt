package com.steven.miformacionctma.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.FormularioActividadUiState
import com.steven.miformacionctma.domain.Prioridad
import com.steven.miformacionctma.domain.validarDescripcion
import com.steven.miformacionctma.domain.validarFecha
import com.steven.miformacionctma.domain.validarProgreso
import com.steven.miformacionctma.domain.validarTitulo

@Composable
fun FormularioActividadContenedor(
    onGuardar: (ActividadFormativa) -> Unit,
    onCancelar: () -> Unit,
    siguienteId: () -> Long
) {
    // rememberSaveable: el borrador sobrevive a rotar el dispositivo,
    // porque cada campo es un tipo simple (String), no una lista ni una
    // entidad grande, tal como pide el punto 4 del laboratorio.
    var titulo by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var fecha by rememberSaveable { mutableStateOf("") }
    var progreso by rememberSaveable { mutableStateOf("0") }
    var prioridad by rememberSaveable { mutableStateOf(Prioridad.MEDIA) }
    var guardando by rememberSaveable { mutableStateOf(false) }

    val uiState = FormularioActividadUiState(
        titulo = titulo,
        descripcion = descripcion,
        fecha = fecha,
        progreso = progreso,
        prioridad = prioridad,
        errorTitulo = validarTitulo(titulo),
        errorDescripcion = validarDescripcion(descripcion),
        errorFecha = if (fecha.isBlank()) null else validarFecha(fecha),
        errorProgreso = validarProgreso(progreso),
        guardando = guardando
    )

    FormularioActividad(
        uiState = uiState,
        onTituloChange = { titulo = it },
        onDescripcionChange = { descripcion = it },
        onFechaChange = { fecha = it },
        onProgresoChange = { progreso = it },
        onPrioridadChange = { prioridad = it },
        onGuardarClick = {
            // Protección contra doble toque: si ya está guardando, ignora el clic.
            if (guardando) return@FormularioActividad
            guardando = true

            val nuevaActividad = ActividadFormativa(
                id = siguienteId(),
                titulo = titulo,
                descripcion = descripcion.ifBlank { null },
                progreso = progreso.toIntOrNull() ?: 0,
                diasRestantes = 0, // se recalcula desde la fecha en pantallas futuras
                prioridad = prioridad
            )
            onGuardar(nuevaActividad)
        },
        onCancelarClick = onCancelar
    )
}