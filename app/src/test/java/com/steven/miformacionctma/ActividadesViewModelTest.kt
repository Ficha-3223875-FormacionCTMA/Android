package com.steven.miformacionctma.ui

import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.ListadoUiState
import com.steven.miformacionctma.domain.OperacionUiState
import com.steven.miformacionctma.domain.Prioridad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ActividadesViewModelTest {

    private val dispatcherDePrueba = StandardTestDispatcher()
    private lateinit var repositorioFalso: FakeActividadRepository
    private lateinit var preferenciasFalsas: FakePreferenciasRepository
    private lateinit var viewModel: ActividadesViewModel

    @Before
    fun preparar() {
        Dispatchers.setMain(dispatcherDePrueba)
        repositorioFalso = FakeActividadRepository()
        preferenciasFalsas = FakePreferenciasRepository()
        viewModel = ActividadesViewModel(repositorioFalso, preferenciasFalsas)
    }

    @After
    fun limpiar() {
        Dispatchers.resetMain()
    }

    @Test
    fun `sin actividades el estado inicial resulta en Vacio`() = runTest {
        backgroundScope.launch { viewModel.uiState.collect { } }
        advanceUntilIdle()

        val estado = viewModel.uiState.value
        assertTrue(estado is ListadoUiState.Vacio)
    }

    @Test
    fun `agregar una actividad cambia el estado a Contenido`() = runTest {
        backgroundScope.launch { viewModel.uiState.collect { } }
        advanceUntilIdle()

        viewModel.agregar(
            ActividadFormativa(1, "Laboratorio Compose", null, 50, 3, Prioridad.ALTA)
        )
        advanceUntilIdle()

        val estado = viewModel.uiState.value
        assertTrue(estado is ListadoUiState.Contenido)
        assertEquals(1, (estado as ListadoUiState.Contenido).actividades.size)
    }

    @Test
    fun `si el repositorio falla el estado resulta en Error`() = runTest {
        repositorioFalso.debeFallar = true
        val viewModelConFallo = ActividadesViewModel(repositorioFalso, preferenciasFalsas)

        backgroundScope.launch { viewModelConFallo.uiState.collect { } }
        advanceUntilIdle()

        val estado = viewModelConFallo.uiState.value
        assertTrue(estado is ListadoUiState.Error)
    }

    @Test
    fun `agregar deja la operacion en Exitosa`() = runTest {
        viewModel.agregar(
            ActividadFormativa(1, "Prueba", null, 0, 1, Prioridad.MEDIA)
        )
        advanceUntilIdle()

        assertEquals(OperacionUiState.Exitosa, viewModel.operacion.value)
    }

    @Test
    fun `dos agregar seguidos mientras uno esta en curso solo ejecuta uno`() = runTest {
        backgroundScope.launch { viewModel.uiState.collect { } }
        runCurrent()

        viewModel.agregar(ActividadFormativa(1, "Primera", null, 0, 1, Prioridad.BAJA))
        runCurrent()

        viewModel.agregar(ActividadFormativa(2, "Segunda", null, 0, 1, Prioridad.BAJA))

        advanceUntilIdle()

        val estado = viewModel.uiState.value as ListadoUiState.Contenido
        assertEquals(1, estado.actividades.size)
    }
}