@file:Suppress("SpellCheckingInspection")

package com.tuinstitucion.evaluacion

@Suppress("unused")
data class CrearUiState(
    val titulo: String = "",
    val errorTitulo: String? = null,
    val guardando: Boolean = false,
    val guardadoId: String? = null
)