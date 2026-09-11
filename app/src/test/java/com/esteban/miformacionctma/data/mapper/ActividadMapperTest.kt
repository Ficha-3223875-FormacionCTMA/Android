package com.esteban.miformacionctma.data.mapper

import com.esteban.miformacionctma.Actividad
import com.esteban.miformacionctma.data.dto.ActividadDTO
import org.junit.Assert.assertEquals
import org.junit.Test

class ActividadMapperTest {

    private val actividad = Actividad(
        id = 1,
        titulo = "Crear API",
        descripcion = "Exponer endpoints REST",
        fecha = "2026-09-10",
        prioridad = "Alta",
        progreso = 75
    )

    private val dto = ActividadDTO(
        id = 1,
        titulo = "Crear API",
        descripcion = "Exponer endpoints REST",
        fecha = "2026-09-10",
        prioridad = "Alta",
        progreso = 75
    )

    @Test
    fun `toDTO mapea todos los campos`() {
        assertEquals(dto, actividad.toDTO())
    }

    @Test
    fun `toEntity mapea todos los campos`() {
        assertEquals(actividad, dto.toEntity())
    }

    @Test
    fun `toDTOList mapea una lista completa`() {
        assertEquals(listOf(dto), listOf(actividad).toDTOList())
    }

    @Test
    fun `toEntityList mapea una lista completa`() {
        assertEquals(listOf(actividad), listOf(dto).toEntityList())
    }

    @Test
    fun `listas vacias se mapean como vacias`() {
        assertEquals(emptyList<ActividadDTO>(), emptyList<Actividad>().toDTOList())
        assertEquals(emptyList<Actividad>(), emptyList<ActividadDTO>().toEntityList())
    }

    @Test
    fun `toDTO y toEntity son inversos`() {
        assertEquals(actividad, actividad.toDTO().toEntity())
        assertEquals(dto, dto.toEntity().toDTO())
    }
}