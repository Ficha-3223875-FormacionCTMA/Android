package com.esteban.miformacionctma.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.esteban.miformacionctma.Actividad
import com.esteban.miformacionctma.EstadoEvidencia
import com.esteban.miformacionctma.Evidencia
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EvidenciaDaoTest {

    private lateinit var db: ActividadDatabase
    private lateinit var dao: EvidenciaDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ActividadDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.evidenciaDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    private suspend fun insertarActividad(id: Int = 1): Actividad {
        val actividad = Actividad(
            id = id,
            titulo = "Titulo",
            descripcion = "Descripcion",
            fecha = "2026-09-10",
            prioridad = "Media",
            progreso = 0
        )
        db.actividadDao().insert(actividad)
        return actividad
    }

    private fun evidencia(actividadId: Int = 1, uri: String = "content://e/imagen.jpg") = Evidencia(
        id = 0,
        actividadId = actividadId,
        uri = uri,
        tipoMime = "image/jpeg",
        tamanoBytes = 2048L,
        estado = EstadoEvidencia.LOCAL
    )

    @Test
    fun insert_y_flow_devuelven_evidencias_de_la_actividad() = runBlocking {
        insertarActividad()
        dao.insert(evidencia())
        dao.insert(evidencia(uri = "content://e/segunda.png"))

        val lista = dao.getEvidenciasByActividad(1).first()

        assertEquals(2, lista.size)
        assertTrue(lista.all { it.actividadId == 1 })
    }

    @Test
    fun insert_asigna_id_y_persiste_uri_tipo_tamano_y_estado() = runBlocking {
        insertarActividad()
        val id = dao.insert(evidencia())

        val guardada = dao.getEvidenciaById(id.toInt())
        assertEquals("content://e/imagen.jpg", guardada?.uri)
        assertEquals("image/jpeg", guardada?.tipoMime)
        assertEquals(2048L, guardada?.tamanoBytes)
        assertEquals(EstadoEvidencia.LOCAL, guardada?.estado)
    }

    @Test
    fun update_estado_cambia_el_estado_de_sincronizacion() = runBlocking {
        insertarActividad()
        val id = dao.insert(evidencia())

        dao.updateEstado(id.toInt(), EstadoEvidencia.SINCRONIZADA)

        assertEquals(EstadoEvidencia.SINCRONIZADA, dao.getEvidenciaById(id.toInt())?.estado)
    }

    @Test
    fun delete_elimina_la_evidencia() = runBlocking {
        insertarActividad()
        val id = dao.insert(evidencia())

        dao.deleteById(id.toInt())

        assertNull(dao.getEvidenciaById(id.toInt()))
    }

    @Test
    fun deleteByActividad_vacia_solo_esa_actividad() = runBlocking {
        insertarActividad(1)
        insertarActividad(2)
        dao.insert(evidencia(actividadId = 1))
        dao.insert(evidencia(actividadId = 2))

        dao.deleteByActividad(1)

        assertTrue(dao.getEvidenciasByActividadSusp(1).isEmpty())
        assertEquals(1, dao.getEvidenciasByActividadSusp(2).size)
    }

    @Test
    fun getEvidenciaById_devuelve_null_si_no_existe() = runBlocking {
        assertNull(dao.getEvidenciaById(999))
    }
}