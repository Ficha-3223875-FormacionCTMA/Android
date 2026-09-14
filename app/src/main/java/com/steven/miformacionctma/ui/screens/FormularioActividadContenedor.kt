package com.steven.miformacionctma.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.FormularioActividadUiState
import com.steven.miformacionctma.domain.OperacionUiState
import com.steven.miformacionctma.domain.Prioridad
import com.steven.miformacionctma.domain.validarDescripcion
import com.steven.miformacionctma.domain.validarFecha
import com.steven.miformacionctma.domain.validarProgreso
import com.steven.miformacionctma.domain.validarTitulo

@Composable
fun FormularioActividadContenedor(
    operacion: OperacionUiState,
    onGuardar: (ActividadFormativa) -> Unit,
    onCancelar: () -> Unit,
    siguienteId: () -> Long
) {
    var titulo by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var fecha by rememberSaveable { mutableStateOf("") }
    var progreso by rememberSaveable { mutableStateOf("0") }
    var prioridad by rememberSaveable { mutableStateOf(Prioridad.MEDIA) }

    val guardando = operacion is OperacionUiState.EnCurso
    val errorGuardado = (operacion as? OperacionUiState.Fallida)?.mensaje

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
        mensajeError = errorGuardado,
        onTituloChange = { titulo = it },
        onDescripcionChange = { descripcion = it },
        onFechaChange = { fecha = it },
        onProgresoChange = { progreso = it },
        onPrioridadChange = { prioridad = it },
        onGuardarClick = {
            if (guardando) return@FormularioActividad
            val nuevaActividad = ActividadFormativa(
                id = siguienteId(),
                titulo = titulo,
                descripcion = descripcion.ifBlank { null },
                progreso = progreso.toIntOrNull() ?: 0,
                diasRestantes = 0,
                prioridad = prioridad
            )
            onGuardar(nuevaActividad)
        },
        onCancelarClick = onCancelar
    )
}