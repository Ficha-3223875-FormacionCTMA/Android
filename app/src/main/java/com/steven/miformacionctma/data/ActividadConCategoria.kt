package com.steven.miformacionctma.data

import androidx.room.Embedded
import androidx.room.Relation

data class ActividadConCategoria(
    @Embedded val actividad: ActividadEntity,
    @Relation(
        parentColumn = "categoriaId",
        entityColumn = "id"
    )
    val categoria: CategoriaEntity?
)