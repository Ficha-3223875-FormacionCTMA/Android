package com.esteban.miformacionctma.data.dto

data class ActividadDTO(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val prioridad: String,
    val progreso: Int
)
