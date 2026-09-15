package com.steven.miformacionctma

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.steven.miformacionctma.data.ActividadEntity
import com.steven.miformacionctma.data.AppDatabase
import com.steven.miformacionctma.data.RoomActividadRepository
import com.steven.miformacionctma.data.remote.RemoteActividadDataSource
import com.steven.miformacionctma.data.remote.RetrofitModule
import com.steven.miformacionctma.domain.ResultadoRefresh
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RoomActividadRepositoryRefrescarTest {

    private lateinit var db: AppDatabase
    private lateinit var servidor: MockWebServer
    private lateinit var repository: RoomActividadRepository

    @Before
    fun preparar() {
        val contexto = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(contexto, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        servidor = MockWebServer()
        servidor.start()

        val api = RetrofitModule.crearApi(
            baseUrl = servidor.url("/").toString(),
            debug = false,
            timeoutSegundos = 2
        )
        val remoto = RemoteActividadDataSource(api)
        repository = RoomActividadRepository(db.actividadDao(), remoto)
    }

    @After
    fun limpiar() {
        db.close()
        servidor.shutdown()
    }

    @Test
    fun refrescarConExito_reemplazaElContenidoDeRoom() = runBlocking {
        servidor.enqueue(
            MockResponse().setResponseCode(200).setBody(
                """[{"id":1,"titulo":"Desde el servidor","descripcion":null,"progreso":30,"diasRestantes":5,"prioridad":"MEDIA"}]"""
            )
        )

        val resultado = repository.refrescar()

        assertTrue(resultado is ResultadoRefresh.Exitoso)
        val actividades = repository.observar("").first()
        assertEquals(1, actividades.size)
        assertEquals("Desde el servidor", actividades.first().titulo)
    }

    @Test
    fun refrescarConError_conservaElCacheExistente() = runBlocking {
        // Precarga el caché directamente en Room, simulando datos ya guardados
        // de una sesión anterior exitosa.
        db.actividadDao().insertar(
            ActividadEntity(
                titulo = "Actividad ya guardada",
                descripcion = null,
                progreso = 80,
                diasRestantes = 1,
                prioridad = "ALTA"
            )
        )

        // El servidor responde con error, sin encolar ninguna respuesta válida.
        servidor.enqueue(MockResponse().setResponseCode(500))

        val resultado = repository.refrescar()

        assertTrue(resultado is ResultadoRefresh.Fallido)

        // Lo importante: el caché de ANTES sigue intacto, no se borró.
        val actividades = repository.observar("").first()
        assertEquals(1, actividades.size)
        assertEquals("Actividad ya guardada", actividades.first().titulo)
    }
}