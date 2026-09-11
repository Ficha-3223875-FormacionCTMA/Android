package com.esteban.miformacionctma.data.local.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.esteban.miformacionctma.data.local.entity.ActividadEntity
import com.esteban.miformacionctma.data.local.entity.CategoriaEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ActividadDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var actividadDao: com.esteban.miformacionctma.data.local.entity.ActividadDao
    private lateinit var categoriaDao: com.esteban.miformacionctma.data.local.entity.CategoriaDao

    @Before
    fun crearDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        actividadDao = db.actividadDao()
        categoriaDao = db.categoriaDao()
    }

    @After
    @Throws(IOException::class)
    fun cerrarDb() {
        db.close()
    }

    @Test
    fun insertarYObtenerPorId() = runBlocking {
        val categoriaId = categoriaDao.insertar(CategoriaEntity(nombre = "Estudio")).toInt()

        val actividad = ActividadEntity(
            titulo = "Terminar informe",
            descripcion = "Redactar el informe de la semana 6",
            fecha = "2026-09-11",
            prioridad = "Alta",
            progreso = 0,
            categoriaId = categoriaId
        )
        val idGenerado = actividadDao.insertar(actividad).toInt()

        val obtenida = actividadDao.obtenerPorId(idGenerado)

        assertNotNull(obtenida)
        assertEquals("Terminar informe", obtenida?.titulo)
        assertEquals(categoriaId, obtenida?.categoriaId)
    }

    @Test
    fun actualizarActividad() = runBlocking {
        val id = actividadDao.insertar(
            ActividadEntity(
                titulo = "Original",
                descripcion = "desc",
                fecha = "2026-09-11",
                prioridad = "Media",
                progreso = 0
            )
        ).toInt()

        val guardada = actividadDao.obtenerPorId(id)!!
        actividadDao.actualizar(guardada.copy(progreso = 100))

        val actualizada = actividadDao.obtenerPorId(id)
        assertEquals(100, actualizada?.progreso)
    }

    @Test
    fun eliminarActividad() = runBlocking {
        val id = actividadDao.insertar(
            ActividadEntity(
                titulo = "Borrar esta",
                descripcion = "desc",
                fecha = "2026-09-11",
                prioridad = "Baja",
                progreso = 0
            )
        ).toInt()

        val guardada = actividadDao.obtenerPorId(id)!!
        actividadDao.eliminar(guardada)

        val resultado = actividadDao.obtenerPorId(id)
        assertNull(resultado)
    }
}