package com.steven.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.steven.miformacionctma.model.ActividadFormativa
import com.steven.miformacionctma.model.EstadoActividad
import com.steven.miformacionctma.ui.screens.PantallaActividades
import com.steven.miformacionctma.ui.theme.MiFormacionCTMATheme

@Suppress("SpellCheckingInspection")
private val listaInicial = listOf(
    ActividadFormativa(
        id = "1",
        titulo = "Guía 3: Jetpack Compose",
        descripcion = "Construir una pantalla declarativa y adaptable.",
        fechaLimite = "2026-09-15",
        estado = EstadoActividad.EN_PROCESO,
        progreso = 65
    )
)

class MainActivity : ComponentActivity() {
    @Suppress("SpellCheckingInspection")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiFormacionCTMATheme {
                val actividades = remember { mutableStateOf(listaInicial) }

                PantallaActividades(
                    actividades = actividades.value,
                    onActividadClick = { },
                    onRecargar = { actividades.value = listaInicial },
                    onAgregarActividad = { titulo, descripcion, fechaLimite ->
                        val nuevaActividad = ActividadFormativa(
                            id = (actividades.value.size + 1).toString(),
                            titulo = titulo,
                            descripcion = descripcion,
                            fechaLimite = fechaLimite.ifBlank { "2026-09-30" },
                            estado = EstadoActividad.PENDIENTE,
                            progreso = 0
                        )
                        actividades.value += nuevaActividad
                    }
                )
            }
        }
    }
}

@Suppress("SpellCheckingInspection")
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