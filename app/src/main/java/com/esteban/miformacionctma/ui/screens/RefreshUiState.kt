package com.esteban.miformacionctma.ui.screens

import com.esteban.miformacionctma.data.DatError

sealed interface RefreshUiState {
    data object Idle : RefreshUiState
    data object Loading : RefreshUiState
    data class Error(val error: DatError) : RefreshUiState
    data object Success : RefreshUiState
}
