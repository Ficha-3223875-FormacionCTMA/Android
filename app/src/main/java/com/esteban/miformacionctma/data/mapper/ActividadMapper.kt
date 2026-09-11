package com.esteban.miformacionctma.data.mapper

import com.esteban.miformacionctma.Actividad
import com.esteban.miformacionctma.data.dto.ActividadDTO

fun Actividad.toDTO(): ActividadDTO = ActividadDTO(
    id = id,
    titulo = titulo,
    descripcion = descripcion,
    fecha = fecha,
    prioridad = prioridad,
    progreso = progreso
)

fun ActividadDTO.toEntity(): Actividad = Actividad(
    id = id,
    titulo = titulo,
    descripcion = descripcion,
    fecha = fecha,
    prioridad = prioridad,
    progreso = progreso
)

fun List<Actividad>.toDTOList(): List<ActividadDTO> = map { it.toDTO() }

fun List<ActividadDTO>.toEntityList(): List<Actividad> = map { it.toEntity() }
