package com.steven.miformacionctma.domain

sealed interface ListadoUiState {
    data object Cargando : ListadoUiState
    data class Contenido(val actividades: List<ActividadFormativa>) : ListadoUiState
    data object Vacio : ListadoUiState
    data class Error(val mensaje: String) : ListadoUiState
}