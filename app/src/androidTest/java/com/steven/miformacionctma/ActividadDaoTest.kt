package com.steven.miformacionctma

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.steven.miformacionctma.data.ActividadDao
import com.steven.miformacionctma.data.ActividadEntity
import com.steven.miformacionctma.data.AppDatabase
import com.steven.miformacionctma.data.CategoriaEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ActividadDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: ActividadDao

    @Before
    fun crearBaseEnMemoria() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.actividadDao()
    }

    @After
    @Throws(IOException::class)
    fun cerrarBase() {
        db.close()
    }

    @Test
    fun insertarYObtenerPorId_devuelveLaActividadCorrecta() = runBlocking {
        val entidad = ActividadEntity(
            titulo = "Prueba DAO",
            descripcion = null,
            progreso = 50,
            diasRestantes = 3,
            prioridad = "MEDIA"
        )
        val id = dao.insertar(entidad)

        val resultado = dao.obtenerPorId(id)

        Assert.assertNotNull(resultado)
        Assert.assertEquals("Prueba DAO", resultado?.titulo)
    }

    @Test
    fun obtenerPorId_conIdInexistente_devuelveNull() = runBlocking {
        val resultado = dao.obtenerPorId(999L)
        Assert.assertNull(resultado)
    }

    @Test
    fun obtenerTodas_reflejaInsercionesSinRecargaManual() = runBlocking {
        dao.insertar(
            ActividadEntity(
                titulo = "Uno",
                descripcion = null,
                progreso = 0,
                diasRestantes = 1,
                prioridad = "BAJA"
            )
        )
        dao.insertar(
            ActividadEntity(
                titulo = "Dos",
                descripcion = null,
                progreso = 0,
                diasRestantes = 1,
                prioridad = "BAJA"
            )
        )

        val lista = dao.obtenerTodas().first()

        Assert.assertEquals(2, lista.size)
    }

    @Test
    fun buscarPorTitulo_encuentraCoincidenciasParciales() = runBlocking {
        dao.insertar(
            ActividadEntity(
                titulo = "Laboratorio Compose",
                descripcion = null,
                progreso = 0,
                diasRestantes = 1,
                prioridad = "ALTA"
            )
        )
        dao.insertar(
            ActividadEntity(
                titulo = "Documentar alcance",
                descripcion = null,
                progreso = 0,
                diasRestantes = 1,
                prioridad = "BAJA"
            )
        )

        val resultado = dao.buscarPorTitulo("compose").first()

        Assert.assertEquals(1, resultado.size)
        Assert.assertEquals("Laboratorio Compose", resultado.first().titulo)
    }

    @Test
    fun insertarCategoriaYRelacionarConActividad_devuelveLaCategoriaCorrecta() = runBlocking {
        val categoriaId = dao.insertarCategoria(CategoriaEntity(nombre = "Compose"))
        dao.insertar(
            ActividadEntity(
                titulo = "Con categoría",
                descripcion = null,
                progreso = 0,
                diasRestantes = 1,
                prioridad = "MEDIA",
                categoriaId = categoriaId
            )
        )

        val resultado = dao.obtenerTodasConCategoria().first()

        Assert.assertEquals(1, resultado.size)
        Assert.assertEquals("Compose", resultado.first().categoria?.nombre)
    }
}