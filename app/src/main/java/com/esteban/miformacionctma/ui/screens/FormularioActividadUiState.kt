package com.esteban.miformacionctma.ui.screens

data class FormularioActividadUiState(
    val titulo: String = "",
    val descripcion: String = "",
    val fecha: String = "",
    val prioridad: String = "Media",
    val progreso: Int = 0,

    val errorTitulo: String? = null,
    val errorFecha: String? = null,

    val puedeGuardar: Boolean = false
)


