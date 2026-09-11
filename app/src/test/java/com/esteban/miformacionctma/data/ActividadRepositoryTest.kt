package com.esteban.miformacionctma.data

import androidx.room.withTransaction
import com.esteban.miformacionctma.Actividad
import com.esteban.miformacionctma.data.dto.ActividadDTO
import com.esteban.miformacionctma.data.remote.RemoteDatasource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import java.io.IOException
import java.net.SocketTimeoutException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ActividadRepositoryTest {

    private lateinit var dao: ActividadDao
    private lateinit var remote: RemoteDatasource
    private lateinit var db: ActividadDatabase
    private lateinit var repo: ActividadRepository

    private val dto = ActividadDTO(
        id = 1,
        titulo = "Terminar tarea",
        descripcion = "Terminar la actividad de la semana",
        fecha = "2026-09-10",
        prioridad = "Alta",
        progreso = 50
    )

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
        dao = mockk()
        remote = mockk()
        db = mockk()

        val blockSlot = slot<suspend () -> Unit>()
        coEvery { db.withTransaction<Unit>(capture(blockSlot)) } coAnswers {
            blockSlot.captured()
        }

        repo = ActividadRepository(dao, remote, db)
    }

    @Test
    fun `actividades expone el flujo del dao`() = runTest {
        val list = listOf(actividad)
        every { dao.getAllActividades() } returns flowOf(list)

        assertEquals(list, repo.actividades.first())
    }

    @Test
    fun `actividadesDTO mapea entidades a DTOs`() = runTest {
        every { dao.getAllActividades() } returns flowOf(listOf(actividad))

        assertEquals(listOf(dto), repo.actividadesDTO.first())
    }

    @Test
    fun `syncFromRemote con exito reemplaza el snapshot local`() = runTest {
        coEvery { remote.getActividades() } returns Result.success(listOf(dto))

        val result = repo.syncFromRemote()

        assertTrue(result.isSuccess)
        coVerify { dao.replaceRemoteSnapshot(listOf(actividad)) }
    }

    @Test
    fun `syncFromRemote ante error de red devuelve NoNetwork`() = runTest {
        coEvery { remote.getActividades() } returns Result.failure(IOException("sin red"))

        val result = repo.syncFromRemote()

        assertTrue(result.isFailure)
        assertEquals(DatError.NoNetwork, result.exceptionOrNull())
    }

    @Test
    fun `syncFromRemote ante timeout devuelve Timeout`() = runTest {
        coEvery { remote.getActividades() } returns Result.failure(SocketTimeoutException("slow"))

        val result = repo.syncFromRemote()

        assertTrue(result.isFailure)
        assertEquals(DatError.Timeout, result.exceptionOrNull())
    }

    @Test
    fun `syncFromRemote ante fallo del dao devuelve Unknown con mensaje`() = runTest {
        coEvery { remote.getActividades() } returns Result.success(listOf(dto))
        coEvery { dao.replaceRemoteSnapshot(any()) } throws RuntimeException("db error")

        val result = repo.syncFromRemote()

        assertTrue(result.isFailure)
        val error = result.exceptionOrNull()
        assertTrue(error is DatError.Unknown)
        assertEquals("db error", (error as DatError.Unknown).message)
    }

    @Test
    fun `pushToRemote con exito devuelve el DTO`() = runTest {
        coEvery { remote.createActividad(any()) } returns Result.success(dto)

        val result = repo.pushToRemote(actividad)

        assertEquals(dto, result.getOrThrow())
    }

    @Test
    fun `pushToRemote ante fallo propaga el error remoto`() = runTest {
        coEvery { remote.createActividad(any()) } returns Result.failure(IOException("sin red"))

        val result = repo.pushToRemote(actividad)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IOException)
    }

    @Test
    fun `insert con exito delega en el dao`() = runTest {
        coEvery { dao.insert(any()) } returns 1L

        assertTrue(repo.insert(actividad).isSuccess)
        coVerify { dao.insert(actividad) }
    }

    @Test
    fun `insert ante fallo devuelve error clasificado`() = runTest {
        coEvery { dao.insert(any()) } throws RuntimeException("boom")

        val result = repo.insert(actividad)

        assertTrue(result.isFailure)
        assertEquals("boom", (result.exceptionOrNull() as? DatError.Unknown)?.message)
    }

    @Test
    fun `insertDTO con exito delega en el dao`() = runTest {
        coEvery { dao.insert(any()) } returns 1L

        assertTrue(repo.insertDTO(dto).isSuccess)
        coVerify { dao.insert(actividad) }
    }

    @Test
    fun `insertDTO ante fallo devuelve error clasificado`() = runTest {
        coEvery { dao.insert(any()) } throws IllegalStateException("boom")

        val result = repo.insertDTO(dto)

        assertTrue(result.isFailure)
        assertEquals("boom", (result.exceptionOrNull() as? DatError.Unknown)?.message)
    }

    @Test
    fun `update con exito delega en el dao`() = runTest {
        coEvery { dao.update(any()) } returns Unit

        assertTrue(repo.update(actividad).isSuccess)
        coVerify { dao.update(actividad) }
    }

    @Test
    fun `update ante fallo devuelve error clasificado`() = runTest {
        coEvery { dao.update(any()) } throws RuntimeException("boom")

        val result = repo.update(actividad)

        assertTrue(result.isFailure)
        assertEquals("boom", (result.exceptionOrNull() as? DatError.Unknown)?.message)
    }

    @Test
    fun `updateDTO con exito delega en el dao`() = runTest {
        coEvery { dao.update(any()) } returns Unit

        assertTrue(repo.updateDTO(dto).isSuccess)
        coVerify { dao.update(actividad) }
    }

    @Test
    fun `delete con exito delega en el dao`() = runTest {
        coEvery { dao.delete(any()) } returns Unit

        assertTrue(repo.delete(actividad).isSuccess)
        coVerify { dao.delete(actividad) }
    }

    @Test
    fun `delete ante fallo devuelve error clasificado`() = runTest {
        coEvery { dao.delete(any()) } throws RuntimeException("boom")

        val result = repo.delete(actividad)

        assertTrue(result.isFailure)
        assertEquals("boom", (result.exceptionOrNull() as? DatError.Unknown)?.message)
    }

    @Test
    fun `getDTOById devuelve el DTO cuando existe`() = runTest {
        coEvery { dao.getActividadById(1) } returns actividad

        val result = repo.getDTOById(1)

        assertEquals(dto, result.getOrNull())
    }

    @Test
    fun `getDTOById devuelve null cuando no existe`() = runTest {
        coEvery { dao.getActividadById(1) } returns null

        val result = repo.getDTOById(1)

        assertTrue(result.isSuccess)
        assertNull(result.getOrNull())
    }

    @Test
    fun `getDTOById ante fallo devuelve error clasificado`() = runTest {
        coEvery { dao.getActividadById(1) } throws RuntimeException("boom")

        val result = repo.getDTOById(1)

        assertTrue(result.isFailure)
        assertEquals("boom", (result.exceptionOrNull() as? DatError.Unknown)?.message)
    }
}