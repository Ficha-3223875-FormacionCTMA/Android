@file:Suppress("SpellCheckingInspection")

package com.tuinstitucion.evaluacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

@Suppress("unused")
class CrearReporteViewModel(
    private val repository: ReporteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CrearUiState())
    val uiState: StateFlow<CrearUiState> = _uiState.asStateFlow()

    fun actualizarTitulo(valor: String) {
        val recortado = valor.take(80)
        _uiState.update { estadoActual ->
            estadoActual.copy(
                titulo = recortado,
                errorTitulo = if (recortado.isNotBlank() && recortado.length >= 4) {
                    null
                } else {
                    estadoActual.errorTitulo
                }
            )
        }
    }

    fun guardar() {
        val tituloActual = _uiState.value.titulo.trim()

        if (tituloActual.isBlank() || tituloActual.length < 4) {
            _uiState.update {
                it.copy(errorTitulo = "El título debe tener entre 4 y 80 caracteres.")
            }
            return
        }

        val nuevoId = UUID.randomUUID().toString()
        val nuevoReporte = Reporte(id = nuevoId, titulo = tituloActual)

        viewModelScope.launch {
            _uiState.update { it.copy(guardando = true) }
            repository.agregar(nuevoReporte)
            _uiState.update { it.copy(guardando = false, guardadoId = nuevoId) }
        }
    }
}