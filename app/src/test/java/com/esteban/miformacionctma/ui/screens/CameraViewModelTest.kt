package com.esteban.miformacionctma.ui.screens

import com.esteban.miformacionctma.data.dto.CatImageDTO
import com.esteban.miformacionctma.data.remote.CatApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class CameraViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val api: CatApi = mockk()

    private val gato = CatImageDTO(
        id = "abc123",
        url = "https://cdn2.thecatapi.com/images/abc123.jpg",
        width = 500,
        height = 400
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun crearViewModel() = CameraViewModel(api)

    @Test
    fun `cargarGatos con respuesta exitosa expone gatos y deja de cargar`() = runTest(testDispatcher) {
        coEvery { api.getRandomCats(any()) } returns Response.success(listOf(gato))

        val vm = crearViewModel()
        vm.cargarGatos()
        advanceUntilIdle()

        val estado = vm.uiState.value
        assertEquals(listOf(gato), estado.gatos)
        assertFalse(estado.cargando)
        assertNull(estado.error)
        coVerify { api.getRandomCats(9) }
    }

    @Test
    fun `cargarGatos con respuesta vacia no rompe y expone lista vacia`() = runTest(testDispatcher) {
        coEvery { api.getRandomCats(any()) } returns Response.success(emptyList())

        val vm = crearViewModel()
        vm.cargarGatos()
        advanceUntilIdle()

        assertTrue(vm.uiState.value.gatos.isEmpty())
        assertFalse(vm.uiState.value.cargando)
        assertNull(vm.uiState.value.error)
    }

    @Test
    fun `cargarGatos ante error http expone el error`() = runTest(testDispatcher) {
        coEvery { api.getRandomCats(any()) } returns
            Response.error<List<CatImageDTO>>(500, "boom".toResponseBody())

        val vm = crearViewModel()
        vm.cargarGatos()
        advanceUntilIdle()

        assertEquals("Error del servidor", vm.uiState.value.error)
        assertFalse(vm.uiState.value.cargando)
    }

    @Test
    fun `cargarGatos ante excepcion de red expone error de conexion`() = runTest(testDispatcher) {
        coEvery { api.getRandomCats(any()) } throws IOException("sin red")

        val vm = crearViewModel()
        vm.cargarGatos()
        advanceUntilIdle()

        assertEquals("Sin conexion: sin red", vm.uiState.value.error)
        assertFalse(vm.uiState.value.cargando)
    }

    @Test
    fun `cargarGatos pasa por el estado cargando`() = runTest(testDispatcher) {
        val gate = kotlinx.coroutines.CompletableDeferred<Unit>()
        coEvery { api.getRandomCats(any()) } coAnswers {
            gate.await()
            Response.success(listOf(gato))
        }

        val vm = crearViewModel()
        vm.cargarGatos()
        advanceUntilIdle()
        assertTrue(vm.uiState.value.cargando)

        gate.complete(Unit)
        advanceUntilIdle()
        assertFalse(vm.uiState.value.cargando)
    }

    @Test
    fun `fotoTomada guarda la ruta de la foto en el estado`() = runTest(testDispatcher) {
        val vm = crearViewModel()

        vm.fotoTomada("/ruta/imagen.jpg")

        assertEquals("/ruta/imagen.jpg", vm.uiState.value.fotoUri)
    }

    @Test
    fun `limpiarFoto quita la foto del estado`() = runTest(testDispatcher) {
        val vm = crearViewModel()
        vm.fotoTomada("/ruta/imagen.jpg")
        vm.limpiarFoto()

        assertNull(vm.uiState.value.fotoUri)
    }

    @Test
    fun `limpiarError elimina el error del estado`() = runTest(testDispatcher) {
        coEvery { api.getRandomCats(any()) } throws IOException("sin red")

        val vm = crearViewModel()
        vm.cargarGatos()
        advanceUntilIdle()
        assertEquals("Sin conexion: sin red", vm.uiState.value.error)

        vm.limpiarError()
        assertNull(vm.uiState.value.error)
    }
}