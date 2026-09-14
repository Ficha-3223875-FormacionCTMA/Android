package com.steven.miformacionctma.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.ActividadRepository
import com.steven.miformacionctma.domain.ListadoUiState
import com.steven.miformacionctma.domain.OperacionUiState
import com.steven.miformacionctma.domain.PreferenciasRepositoryContrato
import com.steven.miformacionctma.domain.ordenarActividades
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ActividadesViewModel(
    private val repository: ActividadRepository,
    private val preferencias: PreferenciasRepositoryContrato
) : ViewModel() {

    private val textoBusqueda = MutableStateFlow("")

    private val actividadesFlow = textoBusqueda.flatMapLatest { texto ->
        repository.observar(texto)
    }

    val uiState: StateFlow<ListadoUiState> = combine(
        actividadesFlow,
        preferencias.ordenPreferido
    ) { lista, orden ->
        val listaOrdenada = if (orden == "prioridad") ordenarActividades(lista) else lista
        if (listaOrdenada.isEmpty()) {
            ListadoUiState.Vacio
        } else {
            ListadoUiState.Contenido(listaOrdenada)
        }
    }
        .catch { excepcion ->
            emit(ListadoUiState.Error(excepcion.message ?: "Ocurrió un error al cargar las actividades"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ListadoUiState.Cargando
        )

    val modoGridPreferido: StateFlow<Boolean> = preferencias.modoGridPreferido
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    private val _operacion = MutableStateFlow<OperacionUiState>(OperacionUiState.Inactiva)
    val operacion: StateFlow<OperacionUiState> = _operacion

    fun buscar(texto: String) {
        textoBusqueda.value = texto
    }

    fun agregar(actividad: ActividadFormativa) {
        if (_operacion.value == OperacionUiState.EnCurso) return

        viewModelScope.launch {
            _operacion.value = OperacionUiState.EnCurso
            try {
                repository.agregar(actividad)
                _operacion.value = OperacionUiState.Exitosa
            } catch (cancelacion: CancellationException) {
                throw cancelacion
            } catch (excepcion: Exception) {
                _operacion.value = OperacionUiState.Fallida(
                    excepcion.message ?: "No se pudo guardar la actividad"
                )
            }
        }
    }

    fun eliminar(id: Long) {
        viewModelScope.launch {
            _operacion.value = OperacionUiState.EnCurso
            try {
                repository.eliminar(id)
                _operacion.value = OperacionUiState.Exitosa
            } catch (cancelacion: CancellationException) {
                throw cancelacion
            } catch (excepcion: Exception) {
                _operacion.value = OperacionUiState.Fallida(
                    excepcion.message ?: "No se pudo eliminar la actividad"
                )
            }
        }
    }

    fun reiniciarOperacion() {
        _operacion.value = OperacionUiState.Inactiva
    }

    fun buscarPorId(id: Long): ActividadFormativa? {
        val estadoActual = uiState.value
        return (estadoActual as? ListadoUiState.Contenido)?.actividades?.find { it.id == id }
    }

    fun siguienteId(): Long {
        val estadoActual = uiState.value
        val lista = (estadoActual as? ListadoUiState.Contenido)?.actividades ?: emptyList()
        return (lista.maxOfOrNull { it.id } ?: 0L) + 1L
    }

    fun alternarModoGrid(activo: Boolean) {
        viewModelScope.launch {
            preferencias.guardarModoGrid(activo)
        }
    }
}