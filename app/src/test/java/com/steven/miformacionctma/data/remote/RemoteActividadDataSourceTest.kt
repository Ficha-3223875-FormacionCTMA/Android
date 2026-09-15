package com.steven.miformacionctma.data.remote

import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RemoteActividadDataSourceTest {

    private lateinit var servidor: MockWebServer
    private lateinit var dataSource: RemoteActividadDataSource

    @Before
    fun preparar() {
        servidor = MockWebServer()
        servidor.start()
        val api = RetrofitModule.crearApi(
            baseUrl = servidor.url("/").toString(),
            debug = false,
            timeoutSegundos = 1
        )
        dataSource = RemoteActividadDataSource(api)
    }

    @After
    fun limpiar() {
        servidor.shutdown()
    }

    @Test
    fun `respuesta 200 valida devuelve la lista de actividades`() = runTest {
        servidor.enqueue(
            MockResponse().setResponseCode(200).setBody(
                """[{"id":1,"titulo":"Prueba","descripcion":null,"progreso":50,"diasRestantes":2,"prioridad":"ALTA"}]"""
            )
        )

        val resultado = dataSource.obtenerActividades()

        assertTrue(resultado is ResultadoRed.Exito)
        assertEquals(1, (resultado as ResultadoRed.Exito).datos.size)
    }

    @Test
    fun `respuesta 200 con arreglo vacio es un exito, no un error`() = runTest {
        servidor.enqueue(MockResponse().setResponseCode(200).setBody("[]"))

        val resultado = dataSource.obtenerActividades()

        assertTrue(resultado is ResultadoRed.Exito)
        assertEquals(0, (resultado as ResultadoRed.Exito).datos.size)
    }

    @Test
    fun `respuesta 401 se clasifica como sesion vencida`() = runTest {
        servidor.enqueue(MockResponse().setResponseCode(401))

        val resultado = dataSource.obtenerActividades()

        assertTrue(resultado is ResultadoRed.Error)
        assertEquals(TipoErrorRed.SESION_VENCIDA, (resultado as ResultadoRed.Error).tipo)
    }

    @Test
    fun `respuesta 404 se clasifica como no encontrado`() = runTest {
        servidor.enqueue(MockResponse().setResponseCode(404))

        val resultado = dataSource.obtenerActividades()

        assertTrue(resultado is ResultadoRed.Error)
        assertEquals(TipoErrorRed.NO_ENCONTRADO, (resultado as ResultadoRed.Error).tipo)
    }

    @Test
    fun `respuesta 500 se clasifica como error del servidor`() = runTest {
        servidor.enqueue(MockResponse().setResponseCode(500))

        val resultado = dataSource.obtenerActividades()

        assertTrue(resultado is ResultadoRed.Error)
        assertEquals(TipoErrorRed.ERROR_SERVIDOR, (resultado as ResultadoRed.Error).tipo)
    }

    @Test
    fun `sin respuesta del servidor se clasifica como timeout`() = runTest {
        servidor.enqueue(
            MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE)
        )

        val resultado = dataSource.obtenerActividades()

        assertTrue(resultado is ResultadoRed.Error)
        assertEquals(TipoErrorRed.TIMEOUT, (resultado as ResultadoRed.Error).tipo)
    }

    @Test
    fun `json invalido se clasifica correctamente`() = runTest {
        servidor.enqueue(
            MockResponse().setResponseCode(200).setBody("""{"esto": "no es una lista"}""")
        )

        val resultado = dataSource.obtenerActividades()

        assertTrue(resultado is ResultadoRed.Error)
        assertEquals(TipoErrorRed.JSON_INVALIDO, (resultado as ResultadoRed.Error).tipo)
    }
}