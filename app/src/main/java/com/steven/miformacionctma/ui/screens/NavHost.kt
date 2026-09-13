package com.steven.miformacionctma.ui.screens

import androidx.compose.runtime.Composable
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
import com.steven.miformacionctma.ui.ActividadesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

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

        val scope = CoroutineScope(SupervisorJob())
        val actividadRepository = RoomActividadRepository(database.actividadDao(), scope)
        val preferenciasRepository = PreferenciasRepository(context.applicationContext)
        ActividadesViewModel(actividadRepository, preferenciasRepository)
    }

    val actividades by viewModel.uiState.collectAsStateWithLifecycle()
    val modoGrid by viewModel.modoGridPreferido.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = RUTA_LISTA) {
        composable(RUTA_LISTA) {
            PantallaActividades(
                actividades = actividades,
                modoGridManual = modoGrid,
                onCambiarModoGrid = { activo -> viewModel.alternarModoGrid(activo) },
                onActividadClick = { actividad ->
                    navController.navigate("detalle/${actividad.id}")
                },
                onAgregarClick = {
                    navController.navigate(RUTA_CREAR)
                }
            )
        }

        composable(RUTA_CREAR) {
            FormularioActividadContenedor(
                onGuardar = { nuevaActividad ->
                    viewModel.agregar(nuevaActividad)
                    navController.popBackStack()
                },
                onCancelar = {
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