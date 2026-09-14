package com.steven.miformacionctma.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.steven.miformacionctma.data.AppDatabase
import com.steven.miformacionctma.data.MIGRACION_1_A_2
import com.steven.miformacionctma.data.PreferenciasRepository
import com.steven.miformacionctma.data.RoomActividadRepository
import com.steven.miformacionctma.domain.OperacionUiState
import com.steven.miformacionctma.ui.ActividadesViewModel

private const val RUTA_LISTA = "lista"
private const val RUTA_CREAR = "crear"
private const val RUTA_DETALLE = "detalle/{actividadId}"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val viewModel = remember {
        val database = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "miformacionctma.db"
        )
            .addMigrations(MIGRACION_1_A_2)
            .build()

        val actividadRepository = RoomActividadRepository(database.actividadDao())
        val preferenciasRepository = PreferenciasRepository(context.applicationContext)
        ActividadesViewModel(actividadRepository, preferenciasRepository)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val operacion by viewModel.operacion.collectAsStateWithLifecycle()
    val modoGrid by viewModel.modoGridPreferido.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = RUTA_LISTA) {
        composable(RUTA_LISTA) {
            PantallaActividades(
                uiState = uiState,
                modoGridManual = modoGrid,
                onCambiarModoGrid = { activo -> viewModel.alternarModoGrid(activo) },
                onBuscar = { texto -> viewModel.buscar(texto) },
                onActividadClick = { actividad ->
                    navController.navigate("detalle/${actividad.id}")
                },
                onAgregarClick = {
                    navController.navigate(RUTA_CREAR)
                }
            )
        }

        composable(RUTA_CREAR) {
            // Cuando la operación de guardado termina bien, vuelve automáticamente
            // a la lista y limpia el estado para la próxima vez que se abra el formulario.
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