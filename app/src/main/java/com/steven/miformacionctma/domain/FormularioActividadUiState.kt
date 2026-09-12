package com.steven.miformacionctma.domain

data class FormularioActividadUiState(
    val titulo: String = "",
    val descripcion: String = "",
    val fecha: String = "",
    val progreso: String = "0",
    val prioridad: Prioridad = Prioridad.MEDIA,
    val errorTitulo: String? = null,
    val errorDescripcion: String? = null,
    val errorFecha: String? = null,
    val errorProgreso: String? = null,
    val guardando: Boolean = false
) {
    val puedeGuardar: Boolean
        get() = errorTitulo == null &&
                errorDescripcion == null &&
                errorFecha == null &&
                errorProgreso == null &&
                titulo.isNotBlank() &&
                !guardando
}