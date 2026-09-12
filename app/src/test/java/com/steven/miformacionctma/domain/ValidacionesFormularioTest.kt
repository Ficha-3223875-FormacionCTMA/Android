package com.steven.miformacionctma.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDate

class ValidacionesFormularioTest {

    @Test
    fun `titulo vacio es invalido`() {
        assertEquals("El título es obligatorio", validarTitulo(""))
    }

    @Test
    fun `titulo muy corto es invalido`() {
        assertEquals("El título debe tener al menos 3 caracteres", validarTitulo("Ab"))
    }

    @Test
    fun `titulo muy largo es invalido`() {
        val tituloLargo = "A".repeat(81)
        assertEquals("El título no puede superar 80 caracteres", validarTitulo(tituloLargo))
    }

    @Test
    fun `titulo valido no produce error`() {
        assertNull(validarTitulo("Laboratorio Compose"))
    }

    @Test
    fun `descripcion muy larga es invalida`() {
        val descripcionLarga = "A".repeat(241)
        assertEquals(
            "La descripción no puede superar 240 caracteres",
            validarDescripcion(descripcionLarga)
        )
    }

    @Test
    fun `fecha con formato invalido es invalida`() {
        assertEquals(
            "Formato de fecha inválido (usa AAAA-MM-DD)",
            validarFecha("20-09-2026")
        )
    }

    @Test
    fun `fecha anterior a hoy es invalida`() {
        val hoy = LocalDate.of(2026, 9, 12)
        assertEquals(
            "La fecha no puede ser anterior a hoy",
            validarFecha("2026-09-01", hoy)
        )
    }

    @Test
    fun `fecha igual o posterior a hoy es valida`() {
        val hoy = LocalDate.of(2026, 9, 12)
        assertNull(validarFecha("2026-09-20", hoy))
    }

    @Test
    fun `progreso no numerico es invalido`() {
        assertEquals("El progreso debe ser un número", validarProgreso("abc"))
    }

    @Test
    fun `progreso fuera de rango es invalido`() {
        assertEquals("El progreso debe estar entre 0 y 100", validarProgreso("150"))
    }

    @Test
    fun `progreso valido no produce error`() {
        assertNull(validarProgreso("50"))
    }
}