package com.esteban.miformacionctma.data.local.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.esteban.miformacionctma.data.local.entity.CategoriaEntity
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

@RunWith(AndroidJUnit4::class)
class CategoriaDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var categoriaDao: com.esteban.miformacionctma.data.local.entity.CategoriaDao

    @Before
    fun crearDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        categoriaDao = db.categoriaDao()
    }

    @After
    @Throws(IOException::class)
    fun cerrarDb() {
        db.close()
    }

    @Test
    fun insertarYObtenerPorId() = runBlocking {
        val id = categoriaDao.insertar(CategoriaEntity(nombre = "Trabajo")).toInt()

        val obtenida = categoriaDao.obtenerPorId(id)

        assertNotNull(obtenida)
        assertEquals("Trabajo", obtenida?.nombre)
    }

    @Test
    fun obtenerPorIdInexistenteDevuelveNull() = runBlocking {
        val resultado = categoriaDao.obtenerPorId(999)
        assertNull(resultado)
    }

    @Test
    fun insertarConIdDuplicadoSeIgnora() = runBlocking {
        val primeraId = categoriaDao.insertar(CategoriaEntity(id = 1, nombre = "Estudio")).toInt()

        // Mismo id, distinto nombre: OnConflictStrategy.IGNORE debe descartar este insert
        val segundoResultado = categoriaDao.insertar(CategoriaEntity(id = 1, nombre = "Personal"))

        val guardada = categoriaDao.obtenerPorId(primeraId)

        assertEquals(-1L, segundoResultado) // Room devuelve -1 cuando IGNORE descarta la fila
        assertEquals("Estudio", guardada?.nombre) // el nombre original no cambió
    }

    @Test
    fun observarTodasOrdenaPorNombreAscendente() = runBlocking {
        categoriaDao.insertar(CategoriaEntity(nombre = "Trabajo"))
        categoriaDao.insertar(CategoriaEntity(nombre = "Estudio"))
        categoriaDao.insertar(CategoriaEntity(nombre = "Personal"))

        val lista = categoriaDao.observarTodas().first()

        assertEquals(3, lista.size)
        assertEquals("Estudio", lista[0].nombre)
        assertEquals("Personal", lista[1].nombre)
        assertEquals("Trabajo", lista[2].nombre)
    }
}