package com.steven.miformacionctma.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steven.miformacionctma.data.PreferenciasRepository
import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.ActividadRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ActividadesViewModel(
    private val repository: ActividadRepository,
    private val preferencias: PreferenciasRepository
) : ViewModel() {

    val uiState: StateFlow<List<ActividadFormativa>> = repository.actividades

    val modoGridPreferido: StateFlow<Boolean> = preferencias.modoGridPreferido
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun agregar(actividad: ActividadFormativa) {
        repository.agregar(actividad)
    }

    fun buscarPorId(id: Long): ActividadFormativa? =
        repository.buscarPorId(id)

    fun siguienteId(): Long =
        (uiState.value.maxOfOrNull { it.id } ?: 0L) + 1L

    fun alternarModoGrid(activo: Boolean) {
        viewModelScope.launch {
            preferencias.guardarModoGrid(activo)
        }
    }
}