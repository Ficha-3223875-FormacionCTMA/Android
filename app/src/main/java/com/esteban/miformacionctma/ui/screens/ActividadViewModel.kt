package com.esteban.miformacionctma.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esteban.miformacionctma.data.local.entity.ActividadEntity
import com.esteban.miformacionctma.repository.ActividadRepository
import kotlinx.coroutines.launch

class ActividadViewModel(
    private val repository: ActividadRepository
) : ViewModel() {

    var uiState by mutableStateOf(FormularioActividadUiState())
        private set

    fun onTituloChange(valor: String) {
        uiState = uiState.copy(
            titulo = valor,
            errorTitulo = if (valor.isBlank()) "El título es obligatorio" else null
        )
    }

    fun onDescripcionChange(valor: String) {
        uiState = uiState.copy(descripcion = valor)
    }

    fun onFechaChange(valor: String) {
        uiState = uiState.copy(
            fecha = valor,
            errorFecha = if (valor.isBlank()) "La fecha es obligatoria" else null
        )
    }

    fun onPrioridadChange(valor: String) {
        uiState = uiState.copy(prioridad = valor)
    }

    fun onProgresoChange(valor: Int) {
        uiState = uiState.copy(progreso = valor)
    }

    fun guardar(onGuardado: () -> Unit = {}) {
        if (uiState.titulo.isBlank()) {
            uiState = uiState.copy(errorTitulo = "El título es obligatorio")
            return
        }
        if (uiState.fecha.isBlank()) {
            uiState = uiState.copy(errorFecha = "La fecha es obligatoria")
            return
        }

        viewModelScope.launch {
            repository.insertar(
                ActividadEntity(
                    titulo = uiState.titulo,
                    descripcion = uiState.descripcion,
                    fecha = uiState.fecha,
                    prioridad = uiState.prioridad,
                    progreso = uiState.progreso,
                    categoriaId = uiState.categoriaId
                )
            )
            uiState = FormularioActividadUiState()  // limpia el formulario
            onGuardado()                            // vuelve a la lista
        }
    }
}