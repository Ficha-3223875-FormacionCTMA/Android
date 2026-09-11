package com.steven.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.Prioridad
import com.steven.miformacionctma.ui.screens.PantallaActividades
import com.steven.miformacionctma.ui.theme.MiFormacionCTMATheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actividadesEjemplo = listOf(
            ActividadFormativa(1, "Configurar Android Studio", null, 100, -5, Prioridad.BAJA),
            ActividadFormativa(2, "Laboratorio Compose semana 1", null, 100, -3, Prioridad.BAJA),
            ActividadFormativa(3, "Núcleo Kotlin de dominio", null, 100, -1, Prioridad.MEDIA),
            ActividadFormativa(4, "Tests de reglas de negocio", null, 100, 0, Prioridad.MEDIA),
            ActividadFormativa(5, "Pantalla de actividades Compose", null, 80, 1, Prioridad.ALTA),
            ActividadFormativa(6, "Validar accesibilidad con TalkBack", null, 40, 2, Prioridad.ALTA),
            ActividadFormativa(7, "Diseño adaptable con BoxWithConstraints", null, 20, 3, Prioridad.MEDIA),
            ActividadFormativa(
                8,
                "Documentar el alcance inicial del proyecto integrador completo de la ficha",
                null, 60, 4, Prioridad.MEDIA
            ),
            ActividadFormativa(9, "Prueba cruzada con un compañero", null, 0, 6, Prioridad.BAJA),
            ActividadFormativa(10, "Commit y README de la semana", null, 0, 6, Prioridad.BAJA)
        )

        setContent {
            MiFormacionCTMATheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaActividades(
                        actividades = actividadesEjemplo,
                        onActividadClick = { /* por ahora sin acción, se añade en semanas futuras */ }
                    )
                }
            }
        }
    }
}