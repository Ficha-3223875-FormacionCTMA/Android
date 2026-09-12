package com.steven.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.Prioridad
import com.steven.miformacionctma.ui.screens.FormularioActividadContenedor
import com.steven.miformacionctma.ui.screens.PantallaActividades
import com.steven.miformacionctma.ui.screens.PantallaDetalleActividad
import com.steven.miformacionctma.ui.theme.MiFormacionCTMATheme

private const val RUTA_LISTA = "lista"
private const val RUTA_CREAR = "crear"
private const val RUTA_DETALLE = "detalle/{actividadId}"

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MiFormacionCTMATheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
private fun AppNavigation() {
    val navController = rememberNavController()

    // Fuente de verdad en memoria. No sobrevive a que el proceso muera,
    // pero sí a la navegación entre pantallas y a rotaciones simples.
    // Persistencia real (Room) llega en semanas futuras.
    val actividades = remember {
        mutableStateListOf(
            ActividadFormativa(1, "Configurar Android Studio", null, 100, -5, Prioridad.BAJA),
            ActividadFormativa(2, "Laboratorio Compose semana 1", null, 100, -3, Prioridad.BAJA),
            ActividadFormativa(3, "Núcleo Kotlin de dominio", null, 100, -1, Prioridad.MEDIA),
            ActividadFormativa(4, "Pantalla de actividades Compose", null, 80, 1, Prioridad.ALTA)
        )
    }

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
                    actividades.add(nuevaActividad)
                    navController.popBackStack()
                },
                onCancelar = {
                    navController.popBackStack()
                },
                siguienteId = {
                    (actividades.maxOfOrNull { it.id } ?: 0L) + 1L
                }
            )
        }

        composable(
            route = RUTA_DETALLE,
            arguments = listOf(navArgument("actividadId") { type = NavType.LongType })
        ) { backStackEntry ->
            val actividadId = backStackEntry.arguments?.getLong("actividadId")
            val actividad = actividades.find { it.id == actividadId }

            PantallaDetalleActividad(
                actividad = actividad,
                onVolverClick = { navController.popBackStack() }
            )
        }
    }
}