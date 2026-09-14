package com.steven.miformacionctma.domain

sealed interface OperacionUiState {
    data object Inactiva : OperacionUiState
    data object EnCurso : OperacionUiState
    data object Exitosa : OperacionUiState
    data class Fallida(val mensaje: String) : OperacionUiState
}