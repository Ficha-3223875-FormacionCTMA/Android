package com.esteban.miformacionctma

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actividades")
data class Actividad(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val prioridad: String,
    val progreso: Int
)