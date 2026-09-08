package com.steven.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.steven.miformacionctma.model.ActividadFormativa
import com.steven.miformacionctma.model.EstadoActividad
import com.steven.miformacionctma.ui.screens.PantallaActividades
import com.steven.miformacionctma.ui.theme.MiFormacionCTMATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val listaInicial = listOf(
            ActividadFormativa(
                id = "1",
                titulo = "Guía 3: Interfaces declarativas con Jetpack Compose",
                descripcion = "Construir una pantalla declarativa, reutilizable y adaptable.",
                fechaLimite = "2026-09-15",
                estado = EstadoActividad.EN_PROCESO,
                progreso = 65
            ),
            ActividadFormativa(
                id = "2",
                titulo = "Guía 6: Persistencia Room",
                descripcion = "Implementar la base de datos local SQLite con Room.",
                fechaLimite = "2026-09-22",
                estado = EstadoActividad.PENDIENTE,
                progreso = 0
            ),
            ActividadFormativa(
                id = "3",
                titulo = "Guía 7: Corrutinas y Flow",
                descripcion = "Concurrencia, StateFlow y manejo del ciclo de vida.",
                fechaLimite = "2026-09-30",
                estado = EstadoActividad.PENDIENTE,
                progreso = 0
            ),
            ActividadFormativa(
                id = "4",
                titulo = "Guía 1: Configuración del Entorno Android",
                descripcion = "Instalación de Android Studio, SDKs y configuración de Git.",
                fechaLimite = "2026-08-30",
                estado = EstadoActividad.COMPLETADA,
                progreso = 100
            )
        )

        setContent {
            MiFormacionCTMATheme {
                val actividades = remember { mutableStateOf(listaInicial) }

                PantallaActividades(
                    actividades = actividades.value,
                    onActividadClick = { },
                    onRecargar = { actividades.value = listaInicial }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    MiFormacionCTMATheme {
        PantallaActividades(
            actividades = listOf(
                ActividadFormativa(
                    id = "1",
                    titulo = "Guía 3: Jetpack Compose",
                    descripcion = "Construir una pantalla declarativa y adaptable.",
                    fechaLimite = "2026-09-15",
                    estado = EstadoActividad.EN_PROCESO,
                    progreso = 65
                )
            ),
            onActividadClick = {},
            onRecargar = {}
        )
    }
}