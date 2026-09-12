package com.steven.miformacionctma.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.steven.miformacionctma.data.InMemoryActividadRepository
import com.steven.miformacionctma.ui.ActividadesViewModel

private const val RUTA_LISTA = "lista"
private const val RUTA_CREAR = "crear"
private const val RUTA_DETALLE = "detalle/{actividadId}"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // El repositorio se crea una sola vez y vive mientras viva este composable.
    // En Semana 6 esto cambiará por RoomActividadRepository sin tocar el ViewModel.
    val repository = remember { InMemoryActividadRepository() }
    val viewModel = remember { ActividadesViewModel(repository) }

    // collectAsStateWithLifecycle: recolecta el StateFlow respetando el ciclo
    // de vida de la pantalla (se pausa cuando la app va a background),
    // tal como preguntaba la pregunta 8 de tu examen.
    val actividades by viewModel.uiState.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = RUTA_LISTA) {
        composable(RUTA_LISTA) {
            PantallaActividades(
                actividades = actividades,
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