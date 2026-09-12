package com.steven.miformacionctma.ui

import androidx.lifecycle.ViewModel
import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.ActividadRepository
import kotlinx.coroutines.flow.StateFlow

class ActividadesViewModel(
    private val repository: ActividadRepository
) : ViewModel() {

    val uiState: StateFlow<List<ActividadFormativa>> = repository.actividades

    fun agregar(actividad: ActividadFormativa) {
        repository.agregar(actividad)
    }

    fun buscarPorId(id: Long): ActividadFormativa? =
        repository.buscarPorId(id)

    fun siguienteId(): Long =
        (uiState.value.maxOfOrNull { it.id } ?: 0L) + 1L
}