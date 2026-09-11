package com.esteban.miformacionctma.data.remote

import com.esteban.miformacionctma.data.dto.ActividadDTO
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RemoteDatasourceTest {

    private val api: ActividadesApi = mockk()
    private lateinit var datasource: RemoteDatasource

    private val dto = ActividadDTO(
        id = 1,
        titulo = "Terminar tarea",
        descripcion = "Terminar la actividad de la semana",
        fecha = "2026-09-10",
        prioridad = "Alta",
        progreso = 50
    )

    @Before
    fun setUp() {
        datasource = RemoteDatasource(api)
    }

    @Test
    fun `getActividades con respuesta exitosa devuelve la lista`() = runTest {
        coEvery { api.getActividades() } returns Response.success(listOf(dto))

        val result = datasource.getActividades()

        assertTrue(result.isSuccess)
        assertEquals(listOf(dto), result.getOrNull())
    }

    @Test
    fun `getActividades con error http devuelve fallo con codigo`() = runTest {
        coEvery { api.getActividades() } returns Response.error<List<ActividadDTO>>(500, "boom".toResponseBody())

        val result = datasource.getActividades()

        assertTrue(result.isFailure)
        assertEquals("Error 500: boom", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getActividades con cuerpo vacio devuelve fallo`() = runTest {
        coEvery { api.getActividades() } returns Response.success<List<ActividadDTO>?>(null)

        val result = datasource.getActividades()

        assertTrue(result.isFailure)
        assertEquals("Respuesta vacia del servidor", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getActividades ante excepcion de red devuelve fallo de conexion`() = runTest {
        coEvery { api.getActividades() } throws IOException("sin red")

        val result = datasource.getActividades()

        assertTrue(result.isFailure)
        assertEquals("Error de conexion: sin red", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getActividadById con respuesta exitosa devuelve el DTO`() = runTest {
        coEvery { api.getActividadById(1) } returns Response.success(dto)

        val result = datasource.getActividadById(1)

        assertTrue(result.isSuccess)
        assertEquals(dto, result.getOrNull())
    }

    @Test
    fun `getActividadById ante 404 devuelve fallo con codigo`() = runTest {
        coEvery { api.getActividadById(1) } returns Response.error<ActividadDTO>(404, "not found".toResponseBody())

        val result = datasource.getActividadById(1)

        assertTrue(result.isFailure)
        assertEquals("Error 404: not found", result.exceptionOrNull()?.message)
    }

    @Test
    fun `createActividad con respuesta exitosa devuelve el DTO`() = runTest {
        coEvery { api.createActividad(any()) } returns Response.success(dto)

        val result = datasource.createActividad(dto)

        assertTrue(result.isSuccess)
        assertEquals(dto, result.getOrNull())
    }

    @Test
    fun `updateActividad con respuesta exitosa devuelve el DTO`() = runTest {
        coEvery { api.updateActividad(1, any()) } returns Response.success(dto)

        val result = datasource.updateActividad(1, dto)

        assertTrue(result.isSuccess)
        assertEquals(dto, result.getOrNull())
    }

    @Test
    fun `deleteActividad con respuesta exitosa devuelve Unit`() = runTest {
        coEvery { api.deleteActividad(1) } returns Response.success(Unit)

        val result = datasource.deleteActividad(1)

        assertTrue(result.isSuccess)
        assertEquals(Unit, result.getOrNull())
    }

    @Test
    fun `deleteActividad ante fallo devuelve error con codigo`() = runTest {
        coEvery { api.deleteActividad(1) } returns Response.error<Unit>(400, "bad".toResponseBody())

        val result = datasource.deleteActividad(1)

        assertTrue(result.isFailure)
        assertEquals("Error 400: bad", result.exceptionOrNull()?.message)
    }
}