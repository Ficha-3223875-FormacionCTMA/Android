package com.esteban.miformacionctma.data

data class Actividad(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val prioridad: String,
    val progreso: Int
)