package com.esteban.miformacionctma.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.esteban.miformacionctma.Actividad
import com.esteban.miformacionctma.data.ActividadDatabase
import com.esteban.miformacionctma.data.ActividadRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ActividadViewModel(private val repository: ActividadRepository) : ViewModel() {

    val actividades: StateFlow<List<Actividad>> = repository.actividades
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _formulario = kotlinx.coroutines.flow.MutableStateFlow(FormularioActividadUiState())
    val formulario: StateFlow<FormularioActividadUiState> = _formulario

    val estaEditando: Boolean
        get() = _actividadEditando != null

    private var _actividadEditando: Actividad? = null

    fun cargarActividad(actividad: Actividad) {
        _actividadEditando = actividad
        _formulario.value = FormularioActividadUiState(
            titulo = actividad.titulo,
            descripcion = actividad.descripcion,
            fecha = actividad.fecha,
            prioridad = actividad.prioridad,
            progreso = actividad.progreso,
            puedeGuardar = actividad.titulo.isNotBlank() && actividad.fecha.isNotBlank()
        )
    }

    fun onTituloChange(titulo: String) {
        _formulario.value = _formulario.value.copy(
            titulo = titulo,
            puedeGuardar = validador(titulo = titulo, fecha = _formulario.value.fecha)
        )
    }

    fun onDescripcionChange(descripcion: String) {
        _formulario.value = _formulario.value.copy(descripcion = descripcion)
    }

    fun onFechaChange(fecha: String) {
        _formulario.value = _formulario.value.copy(
            fecha = fecha,
            puedeGuardar = validador(titulo = _formulario.value.titulo, fecha = fecha)
        )
    }

    fun onPrioridadChange(prioridad: String) {
        _formulario.value = _formulario.value.copy(prioridad = prioridad)
    }

    fun onProgresoChange(progreso: Int) {
        _formulario.value = _formulario.value.copy(progreso = progreso)
    }

    fun guardar() {
        val estado = _formulario.value

        val errorTitulo = if (estado.titulo.isBlank()) "Escribe un título" else null
        val errorFecha = if (estado.fecha.isBlank()) "Escribe una fecha" else null
        _formulario.value = estado.copy(errorTitulo = errorTitulo, errorFecha = errorFecha)
        if (errorTitulo != null || errorFecha != null) return

        val actividad = Actividad(
            id = _actividadEditando?.id ?: 0,
            titulo = estado.titulo.trim(),
            descripcion = estado.descripcion.trim(),
            fecha = estado.fecha.trim(),
            prioridad = estado.prioridad,
            progreso = estado.progreso
        )

        viewModelScope.launch {
            if (_actividadEditando != null) {
                repository.update(actividad)
            } else {
                repository.insert(actividad)
            }
            limpiarFormulario()
        }
    }

    fun eliminar(actividad: Actividad) {
        viewModelScope.launch {
            repository.delete(actividad)
        }
    }

    fun limpiarFormulario() {
        _actividadEditando = null
        _formulario.value = FormularioActividadUiState()
    }

    private fun validador(titulo: String, fecha: String): Boolean =
        titulo.isNotBlank() && fecha.isNotBlank() && !titulo.startsWith(" ")

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]!!
                val dao = ActividadDatabase.getInstance(application).actividadDao()
                ActividadViewModel(ActividadRepository(dao))
            }
        }
    }
}