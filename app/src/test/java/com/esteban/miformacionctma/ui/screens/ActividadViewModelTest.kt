package com.esteban.miformacionctma.ui.screens

import com.esteban.miformacionctma.Actividad
import com.esteban.miformacionctma.data.ActividadRepository
import com.esteban.miformacionctma.data.DatError
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ActividadViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val repository: ActividadRepository = mockk()

    private val actividad = Actividad(
        id = 1,
        titulo = "Terminar tarea",
        descripcion = "Terminar la actividad de la semana",
        fecha = "2026-09-10",
        prioridad = "Alta",
        progreso = 50
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun configurarRepositorioExitoso() {
        coEvery { repository.syncFromRemote() } returns Result.success(Unit)
        coEvery { repository.insert(any()) } returns Result.success(Unit)
        coEvery { repository.update(any()) } returns Result.success(Unit)
        coEvery { repository.delete(any()) } returns Result.success(Unit)
        every { repository.actividades } returns flowOf(emptyList())
    }

    private fun crearViewModel() = ActividadViewModel(repository)

    @Test
    fun `al iniciar sincroniza y llega a Success`() = runTest(testDispatcher) {
        configurarRepositorioExitoso()

        val vm = crearViewModel()
        advanceUntilIdle()

        assertEquals(RefreshUiState.Success, vm.refreshState.value)
        coVerify { repository.syncFromRemote() }
    }

    @Test
    fun `al iniciar pasa por el estado Loading`() = runTest(testDispatcher) {
        val gate = CompletableDeferred<Unit>()
        coEvery { repository.syncFromRemote() } coAnswers {
            gate.await()
            Result.success(Unit)
        }
        every { repository.actividades } returns flowOf(emptyList())

        val vm = crearViewModel()
        advanceUntilIdle()
        assertEquals(RefreshUiState.Loading, vm.refreshState.value)

        gate.complete(Unit)
        advanceUntilIdle()
        assertEquals(RefreshUiState.Success, vm.refreshState.value)
    }

    @Test
    fun `sync ante fallo tipado expone ese DatError`() = runTest(testDispatcher) {
        coEvery { repository.syncFromRemote() } returns Result.failure(DatError.Timeout)
        every { repository.actividades } returns flowOf(emptyList())

        val vm = crearViewModel()
        advanceUntilIdle()

        assertEquals(RefreshUiState.Error(DatError.Timeout), vm.refreshState.value)
    }

    @Test
    fun `sync ante fallo no tipado lo convierte a Unknown`() = runTest(testDispatcher) {
        coEvery { repository.syncFromRemote() } returns Result.failure(IOException("sin red"))
        every { repository.actividades } returns flowOf(emptyList())

        val vm = crearViewModel()
        advanceUntilIdle()

        assertEquals(RefreshUiState.Error(DatError.Unknown("sin red")), vm.refreshState.value)
    }

    @Test
    fun `clearRefreshError devuelve el estado a Idle`() = runTest(testDispatcher) {
        coEvery { repository.syncFromRemote() } returns Result.failure(DatError.Server)
        every { repository.actividades } returns flowOf(emptyList())

        val vm = crearViewModel()
        advanceUntilIdle()
        vm.clearRefreshError()

        assertEquals(RefreshUiState.Idle, vm.refreshState.value)
    }

    @Test
    fun `cargarActividad puebla el formulario y marca modo edicion`() = runTest(testDispatcher) {
        configurarRepositorioExitoso()
        val vm = crearViewModel()
        advanceUntilIdle()

        vm.cargarActividad(actividad)

        assertTrue(vm.estaEditando)
        val form = vm.formulario.value
        assertEquals(actividad.titulo, form.titulo)
        assertEquals(actividad.descripcion, form.descripcion)
        assertEquals(actividad.fecha, form.fecha)
        assertEquals(actividad.prioridad, form.prioridad)
        assertEquals(actividad.progreso, form.progreso)
        assertTrue(form.puedeGuardar)
    }

    @Test
    fun `onTituloChange actualiza el titulo y valida puedeGuardar`() = runTest(testDispatcher) {
        configurarRepositorioExitoso()
        val vm = crearViewModel()
        advanceUntilIdle()

        vm.onFechaChange("2026-09-10")
        assertFalse(vm.formulario.value.puedeGuardar)

        vm.onTituloChange(" Tarea")
        assertFalse(vm.formulario.value.puedeGuardar)

        vm.onTituloChange("Tarea")
        assertTrue(vm.formulario.value.puedeGuardar)
    }

    @Test
    fun `onDescripcionChange actualiza la descripcion`() = runTest(testDispatcher) {
        configurarRepositorioExitoso()
        val vm = crearViewModel()
        advanceUntilIdle()

        vm.onDescripcionChange("Nueva descripcion")

        assertEquals("Nueva descripcion", vm.formulario.value.descripcion)
    }

    @Test
    fun `onPrioridadChange actualiza la prioridad`() = runTest(testDispatcher) {
        configurarRepositorioExitoso()
        val vm = crearViewModel()
        advanceUntilIdle()

        vm.onPrioridadChange("Baja")

        assertEquals("Baja", vm.formulario.value.prioridad)
    }

    @Test
    fun `onProgresoChange actualiza el progreso`() = runTest(testDispatcher) {
        configurarRepositorioExitoso()
        val vm = crearViewModel()
        advanceUntilIdle()

        vm.onProgresoChange(80)

        assertEquals(80, vm.formulario.value.progreso)
    }

    @Test
    fun `guardar sin datos no inserta y muestra errores`() = runTest(testDispatcher) {
        configurarRepositorioExitoso()
        val vm = crearViewModel()
        advanceUntilIdle()

        vm.guardar()
        advanceUntilIdle()

        assertEquals("Escribe un titulo", vm.formulario.value.errorTitulo)
        assertEquals("Escribe una fecha", vm.formulario.value.errorFecha)
        coVerify(exactly = 0) { repository.insert(any()) }
    }

    @Test
    fun `guardar con datos validos inserta y limpia el formulario`() = runTest(testDispatcher) {
        configurarRepositorioExitoso()
        val vm = crearViewModel()
        advanceUntilIdle()

        vm.onTituloChange("Nueva actividad")
        vm.onDescripcionChange("Descripcion de prueba")
        vm.onFechaChange("2026-09-15")
        vm.onPrioridadChange("Media")
        vm.onProgresoChange(30)
        vm.guardar()
        advanceUntilIdle()

        coVerify { repository.insert(any()) }
        assertEquals(FormularioActividadUiState(), vm.formulario.value)
        assertFalse(vm.estaEditando)
    }

    @Test
    fun `guardar en modo edicion llama update en lugar de insert`() = runTest(testDispatcher) {
        configurarRepositorioExitoso()
        val vm = crearViewModel()
        advanceUntilIdle()

        vm.cargarActividad(actividad)
        vm.guardar()
        advanceUntilIdle()

        coVerify { repository.update(actividad) }
        coVerify(exactly = 0) { repository.insert(any()) }
        assertFalse(vm.estaEditando)
    }

    @Test
    fun `eliminar delega en el repositorio`() = runTest(testDispatcher) {
        configurarRepositorioExitoso()
        val vm = crearViewModel()
        advanceUntilIdle()

        vm.eliminar(actividad)
        advanceUntilIdle()

        coVerify { repository.delete(actividad) }
    }

    @Test
    fun `limpiarFormulario reinicia el estado de edicion`() = runTest(testDispatcher) {
        configurarRepositorioExitoso()
        val vm = crearViewModel()
        advanceUntilIdle()

        vm.cargarActividad(actividad)
        vm.limpiarFormulario()

        assertFalse(vm.estaEditando)
        assertEquals(FormularioActividadUiState(), vm.formulario.value)
    }
}