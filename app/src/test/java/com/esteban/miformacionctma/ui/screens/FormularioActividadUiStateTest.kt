package com.esteban.miformacionctma.ui.screens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Test

class FormularioActividadUiStateTest {

    @Test
    fun `estado inicial usa valores por defecto`() {
        val estado = FormularioActividadUiState()

        assertEquals("", estado.titulo)
        assertEquals("", estado.descripcion)
        assertEquals("", estado.fecha)
        assertEquals("Media", estado.prioridad)
        assertEquals(0, estado.progreso)
        assertNull(estado.errorTitulo)
        assertNull(estado.errorFecha)
        assertFalse(estado.puedeGuardar)
    }

    @Test
    fun `copy conserva los campos no modificados`() {
        val estado = FormularioActividadUiState().copy(titulo = "Tarea")

        assertEquals("Tarea", estado.titulo)
        assertEquals("Media", estado.prioridad)
        assertFalse(estado.puedeGuardar)
    }

    @Test
    fun `copy permite activar puedeGuardar`() {
        val estado = FormularioActividadUiState().copy(titulo = "Tarea", fecha = "2026-09-10", puedeGuardar = true)

        assertEquals("Tarea", estado.titulo)
        assertEquals("2026-09-10", estado.fecha)
        assertEquals(true, estado.puedeGuardar)
    }
}