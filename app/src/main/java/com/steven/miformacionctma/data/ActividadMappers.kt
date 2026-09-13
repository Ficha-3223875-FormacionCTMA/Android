package com.steven.miformacionctma.data

import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.Prioridad

fun ActividadEntity.aDominio(): ActividadFormativa = ActividadFormativa(
    id = id,
    titulo = titulo,
    descripcion = descripcion,
    progreso = progreso,
    diasRestantes = diasRestantes,
    prioridad = Prioridad.valueOf(prioridad)
)

fun ActividadFormativa.aEntidad(categoriaId: Long? = null): ActividadEntity = ActividadEntity(
    id = id,
    titulo = titulo,
    descripcion = descripcion,
    progreso = progreso,
    diasRestantes = diasRestantes,
    prioridad = prioridad.name,
    categoriaId = categoriaId
)