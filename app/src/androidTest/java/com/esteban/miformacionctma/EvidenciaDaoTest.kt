package com.esteban.miformacionctma.data.local.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.esteban.miformacionctma.data.evidencia.EvidenciaEstados
import com.esteban.miformacionctma.data.local.entity.ActividadEntity
import com.esteban.miformacionctma.data.local.entity.EvidenciaEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class EvidenciaDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var evidenciaDao: com.esteban.miformacionctma.data.local.entity.EvidenciaDao
    private lateinit var actividadDao: com.esteban.miformacionctma.data.local.entity.ActividadDao

    @Before
    fun crearDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        evidenciaDao = db.evidenciaDao()
        actividadDao = db.actividadDao()
    }

    @After
    @Throws(IOException::class)
    fun cerrarDb() {
        db.close()
    }

    private suspend fun crearActividad(): Int =
        actividadDao.insertar(
            ActividadEntity(
                titulo = "Evidenciar tarea",
                descripcion = "desc",
                fecha = "2026-09-14",
                prioridad = "Media",
                progreso = 10
            )
        ).toInt()

    private fun evidencia(actividadId: Int) = EvidenciaEntity(
        id = UUID.randomUUID().toString(),
        actividadId = actividadId,
        localUri = "content://com.esteban.miformacionctma.fileprovider/evidencias/evidencia_a.jpg",
        mimeType = "image/jpeg",
        sizeBytes = 4_096,
        estado = EvidenciaEstados.LOCAL,
        creadaEnEpochMillis = System.currentTimeMillis()
    )

    @Test
    fun insertarYObtenerPorId() = runBlocking {
        val actividadId = crearActividad()
        val evidencia = evidencia(actividadId)

        evidenciaDao.insertar(evidencia)

        val guardada = evidenciaDao.obtenerPorId(evidencia.id)
        assertNotNull(guardada)
        assertEquals(actividadId, guardada?.actividadId)
        assertEquals(EvidenciaEstados.LOCAL, guardada?.estado)
    }

    @Test
    fun actualizarEstado() = runBlocking {
        val actividadId = crearActividad()
        val evidencia = evidencia(actividadId)
        evidenciaDao.insertar(evidencia)

        evidenciaDao.actualizarEstado(evidencia.id, EvidenciaEstados.SINCRONIZADA)

        assertEquals(
            EvidenciaEstados.SINCRONIZADA,
            evidenciaDao.obtenerPorId(evidencia.id)?.estado
        )
    }

    @Test
    fun eliminarPorId() = runBlocking {
        val actividadId = crearActividad()
        val evidencia = evidencia(actividadId)
        evidenciaDao.insertar(evidencia)

        evidenciaDao.eliminarPorId(evidencia.id)

        assertNull(evidenciaDao.obtenerPorId(evidencia.id))
    }

    @Test
    fun cascadeBorraEvidenciasAlEliminarActividad() = runBlocking {
        val actividad = ActividadEntity(
            titulo = "Tarea a eliminar",
            descripcion = "desc",
            fecha = "2026-09-14",
            prioridad = "Baja",
            progreso = 0
        )
        val actividadId = actividadDao.insertar(actividad).toInt()
        val evidencia = evidencia(actividadId)
        evidenciaDao.insertar(evidencia)

        val guardada = actividadDao.obtenerPorId(actividadId)!!
        actividadDao.eliminar(guardada)

        assertNull(evidenciaDao.obtenerPorId(evidencia.id))
    }

    @Test
    fun observarPorActividadDevuelveListaOrdenada() = runBlocking {
        val actividadId = crearActividad()
        evidenciaDao.insertar(evidencia(actividadId).copy(id = "ev1"))
        evidenciaDao.insertar(evidencia(actividadId).copy(id = "ev2"))

        val lista = evidenciaDao.observarPorActividad(actividadId).first()

        assertEquals(2, lista.size)
        assertEquals(listOf("ev2", "ev1"), lista.map { it.id })
    }
}