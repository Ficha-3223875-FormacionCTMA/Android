package com.steven.miformacionctma.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ReglasActividadTest {

    @Test
    fun `titulo vacio produce error de validacion`() {
        val errores = validarActividad(" ", 50)
        assertTrue(errores.contains("El título es obligatorio"))
    }

    @Test
    fun `progreso fuera de rango produce error de validacion`() {
        val errores = validarActividad("Guía 3", 120)
        assertTrue(errores.contains("El progreso debe estar entre 0 y 100"))
    }

    @Test
    fun `actividad con dias negativos y progreso menor a 100 esta vencida`() {
        assertEquals("Vencida", estadoActividad(progreso = 80, diasRestantes = -1))
    }

    @Test
    fun `actividad con progreso 100 esta completada aunque dias sea negativo`() {
        assertEquals("Completada", estadoActividad(progreso = 100, diasRestantes = -2))
    }

    @Test
    fun `promedio de lista vacia es cero sin lanzar excepcion`() {
        assertEquals(0.0, promedioProgreso(emptyList()), 0.0)
    }

    @Test
    fun `busqueda por titulo ignora mayusculas y espacios externos`() {
        val actividades = listOf(
            ActividadFormativa(1, "Kotlin básico", null, 50, 5, Prioridad.MEDIA),
            ActividadFormativa(2, "Configurar Android Studio", null, 100, 0, Prioridad.BAJA)
        )
        val resultado = buscarPorTitulo(actividades, " kotlin ")
        assertEquals(1, resultado.size)
        assertEquals("Kotlin básico", resultado.first().titulo)
    }

    @Test
    fun `ordena vencidas primero luego prioridad alta y menos dias`() {
        val actividades = listOf(
            ActividadFormativa(1, "Baja prioridad, 5 días", null, 50, 5, Prioridad.BAJA),
            ActividadFormativa(2, "Vencida hace 1 día", null, 40, -1, Prioridad.MEDIA),
            ActividadFormativa(3, "Alta prioridad, 1 día", null, 60, 1, Prioridad.ALTA),
            ActividadFormativa(4, "Vencida hace 3 días", null, 20, -3, Prioridad.BAJA)
        )

        val resultado = ordenarActividades(actividades)

        // Entre las vencidas, la de mayor prioridad (MEDIA > BAJA) va primero
        assertEquals("Vencida hace 1 día", resultado[0].titulo)
        assertEquals("Vencida hace 3 días", resultado[1].titulo)
        // Luego las no vencidas, la de mayor prioridad primero
        assertEquals("Alta prioridad, 1 día", resultado[2].titulo)
        assertEquals("Baja prioridad, 5 días", resultado[3].titulo)
    }
}