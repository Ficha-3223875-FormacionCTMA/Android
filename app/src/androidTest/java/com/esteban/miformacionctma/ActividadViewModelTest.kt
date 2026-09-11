package com.esteban.miformacionctma.ui.screens

import com.esteban.miformacionctma.repository.ActividadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ActividadViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeDao: FakeActividadDao
    private lateinit var repository: ActividadRepository
    private lateinit var viewModel: ActividadViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeDao = FakeActividadDao()
        repository = ActividadRepository(fakeDao)
        viewModel = ActividadViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun estadoInicialEsVacioYNoSePuedeGuardar() {
        assertEquals("", viewModel.uiState.titulo)
        assertEquals("Media", viewModel.uiState.prioridad)
        assertEquals(false, viewModel.uiState.puedeGuardar)
    }

    @Test
    fun onTituloChangeConTextoLimpiaError() {
        viewModel.onTituloChange("Terminar informe")

        assertEquals("Terminar informe", viewModel.uiState.titulo)
        assertNull(viewModel.uiState.errorTitulo)
    }

    @Test
    fun onTituloChangeVacioMuestraError() {
        viewModel.onTituloChange("")

        assertEquals("El título es obligatorio", viewModel.uiState.errorTitulo)
    }

    @Test
    fun guardarConTituloVacioNoInsertaNada() = runTest(testDispatcher) {
        viewModel.onFechaChange("2026-09-11")
        // título queda vacío a propósito

        viewModel.guardar()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("El título es obligatorio", viewModel.uiState.errorTitulo)
        assertTrue(fakeDao.snapshot().isEmpty())
    }

    @Test
    fun guardarConFechaVaciaNoInsertaNada() = runTest(testDispatcher) {
        viewModel.onTituloChange("Terminar informe")
        // fecha queda vacía a propósito

        viewModel.guardar()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("La fecha es obligatoria", viewModel.uiState.errorFecha)
        assertTrue(fakeDao.snapshot().isEmpty())
    }

    @Test
    fun guardarConDatosValidosInsertaYLimpiaFormulario() = runTest(testDispatcher) {
        var callbackLlamado = false

        viewModel.onTituloChange("Terminar informe")
        viewModel.onFechaChange("2026-09-11")
        viewModel.onPrioridadChange("Alta")
        viewModel.onProgresoChange(50)

        viewModel.guardar(onGuardado = { callbackLlamado = true })
        testDispatcher.scheduler.advanceUntilIdle()

        val guardadas = fakeDao.snapshot()
        assertEquals(1, guardadas.size)
        assertEquals("Terminar informe", guardadas[0].titulo)
        assertEquals("Alta", guardadas[0].prioridad)
        assertEquals(50, guardadas[0].progreso)

        // el formulario se resetea después de guardar
        assertEquals("", viewModel.uiState.titulo)
        assertEquals("Media", viewModel.uiState.prioridad)
        assertTrue(callbackLlamado)
    }
}