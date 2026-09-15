package com.steven.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.steven.miformacionctma.BuildConfig
import com.steven.miformacionctma.data.AppDatabase
import com.steven.miformacionctma.data.MIGRACION_1_A_2
import com.steven.miformacionctma.data.PreferenciasRepository
import com.steven.miformacionctma.data.RoomActividadRepository
import com.steven.miformacionctma.data.remote.RemoteActividadDataSource
import com.steven.miformacionctma.data.remote.RetrofitModule
import com.steven.miformacionctma.domain.OperacionUiState
import com.steven.miformacionctma.ui.ActividadesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

private const val RUTA_LISTA = "lista"
private const val RUTA_CREAR = "crear"
private const val RUTA_DETALLE = "detalle/{actividadId}"

private const val JSON_ACTIVIDADES_INICIAL = """
[
  {"id": 1, "titulo": "Consultar servicio web", "descripcion": "Actividad traida desde el servidor simulado", "progreso": 20, "diasRestantes": 4, "prioridad": "ALTA"},
  {"id": 2, "titulo": "Validar manejo de errores", "descripcion": null, "progreso": 0, "diasRestantes": 6, "prioridad": "MEDIA"}
]
"""

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    var viewModel by remember { mutableStateOf<ActividadesViewModel?>(null) }

    // Arranca el servidor simulado y arma el ViewModel en un hilo de fondo,
    // porque abrir un socket de red (MockWebServer) NUNCA puede hacerse
    // en el hilo principal — Android lo prohíbe con NetworkOnMainThreadException.
    LaunchedEffect(Unit) {
        val nuevoViewModel = withContext(Dispatchers.IO) {
            val database = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "miformacionctma.db"
            )
                .addMigrations(MIGRACION_1_A_2)
                .build()

            val servidorSimulado = MockWebServer().apply {
                enqueue(
                    MockResponse()
                        .setResponseCode(200)
                        .setBody(JSON_ACTIVIDADES_INICIAL)
                )
                start()
            }

            val api = RetrofitModule.crearApi(
                baseUrl = servidorSimulado.url("/").toString(),
                debug = BuildConfig.DEBUG
            )
            val remoteDataSource = RemoteActividadDataSource(api)
            val actividadRepository = RoomActividadRepository(database.actividadDao(), remoteDataSource)
            val preferenciasRepository = PreferenciasRepository(context.applicationContext)
            ActividadesViewModel(actividadRepository, preferenciasRepository)
        }
        viewModel = nuevoViewModel
    }

    val viewModelActual = viewModel
    if (viewModelActual == null) {
        PantallaInicializando()
    } else {
        ContenidoNavegacion(viewModelActual)
    }
}

@Composable
private fun PantallaInicializando() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text(
                text = "Iniciando…",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun ContenidoNavegacion(viewModel: ActividadesViewModel) {
    val navController = rememberNavController()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val operacion by viewModel.operacion.collectAsStateWithLifecycle()
    val modoGrid by viewModel.modoGridPreferido.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = RUTA_LISTA) {
        composable(RUTA_LISTA) {
            PantallaActividades(
                uiState = uiState,
                operacion = operacion,
                modoGridManual = modoGrid,
                onCambiarModoGrid = { activo -> viewModel.alternarModoGrid(activo) },
                onBuscar = { texto -> viewModel.buscar(texto) },
                onActividadClick = { actividad ->
                    navController.navigate("detalle/${actividad.id}")
                },
                onAgregarClick = {
                    navController.navigate(RUTA_CREAR)
                },
                onRefrescarClick = { viewModel.refrescar() }
            )
        }

        composable(RUTA_CREAR) {
            LaunchedEffect(operacion) {
                if (operacion is OperacionUiState.Exitosa) {
                    viewModel.reiniciarOperacion()
                    navController.popBackStack()
                }
            }

            FormularioActividadContenedor(
                operacion = operacion,
                onGuardar = { nuevaActividad -> viewModel.agregar(nuevaActividad) },
                onCancelar = {
                    viewModel.reiniciarOperacion()
                    navController.popBackStack()
                },
                siguienteId = { viewModel.siguienteId() }
            )
        }

        composable(
            route = RUTA_DETALLE,
            arguments = listOf(navArgument("actividadId") { type = NavType.LongType })
        ) { backStackEntry ->
            val actividadId = backStackEntry.arguments?.getLong("actividadId")
            val actividad = actividadId?.let { viewModel.buscarPorId(it) }

            PantallaDetalleActividad(
                actividad = actividad,
                onVolverClick = { navController.popBackStack() }
            )
        }
    }
}