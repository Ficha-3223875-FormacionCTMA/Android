package com.steven.miformacionctma.model

enum class EstadoActividad(val etiqueta: String) {
    PENDIENTE("Pendiente"),
    EN_PROCESO("En proceso"),
    COMPLETADA("Completada")
}

data class ActividadFormativa(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val fechaLimite: String,
    val estado: EstadoActividad,
    val progreso: Int
)