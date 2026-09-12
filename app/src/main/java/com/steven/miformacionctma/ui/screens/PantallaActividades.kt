package com.steven.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items as itemsLista
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items as itemsGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.Prioridad
import com.steven.miformacionctma.ui.components.TarjetaActividad
import com.steven.miformacionctma.ui.theme.MiFormacionCTMATheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaActividades(
    actividades: List<ActividadFormativa>,
    onActividadClick: (ActividadFormativa) -> Unit = {},
    onAgregarClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis actividades") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregarClick) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar actividad")
            }
        }
    ) { paddingInterno ->
        if (actividades.isEmpty()) {
            EstadoVacio(modifier = Modifier.padding(paddingInterno))
        } else {
            ContenidoAdaptable(
                actividades = actividades,
                onActividadClick = onActividadClick,
                contentPadding = paddingInterno
            )
        }
    }
}

@Composable
private fun ContenidoAdaptable(
    actividades: List<ActividadFormativa>,
    onActividadClick: (ActividadFormativa) -> Unit,
    contentPadding: PaddingValues
) {
    BoxWithConstraints {
        // Umbral de 600.dp: decisión de este ejercicio, no una regla universal.
        // Por debajo, una columna angosta (teléfono) se ve mejor en lista simple;
        // por encima (tablet, pantalla grande), aprovechamos el ancho con un grid.
        if (maxWidth < 600.dp) {
            ListaActividades(
                actividades = actividades,
                onActividadClick = onActividadClick,
                contentPadding = contentPadding
            )
        } else {
            CuadriculaActividades(
                actividades = actividades,
                onActividadClick = onActividadClick,
                contentPadding = contentPadding
            )
        }
    }
}

@Composable
private fun ListaActividades(
    actividades: List<ActividadFormativa>,
    onActividadClick: (ActividadFormativa) -> Unit,
    contentPadding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = contentPadding.calculateTopPadding() + 8.dp,
            bottom = 16.dp
        )
    ) {
        itemsLista(items = actividades, key = { it.id }) { actividad ->
            TarjetaActividad(actividad = actividad, onClick = onActividadClick)
        }
    }
}

@Composable
private fun CuadriculaActividades(
    actividades: List<ActividadFormativa>,
    onActividadClick: (ActividadFormativa) -> Unit,
    contentPadding: PaddingValues
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = contentPadding.calculateTopPadding() + 8.dp,
            bottom = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsGrid(items = actividades, key = { it.id }) { actividad ->
            TarjetaActividad(actividad = actividad, onClick = onActividadClick)
        }
    }
}

@Composable
private fun EstadoVacio(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Aún no tienes actividades",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Cuando tu instructor asigne una actividad, aparecerá aquí.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PantallaActividadesPreview() {
    MiFormacionCTMATheme {
        PantallaActividades(
            actividades = listOf(
                ActividadFormativa(1, "Configurar Android Studio", null, 100, -3, Prioridad.BAJA),
                ActividadFormativa(2, "Laboratorio Compose", null, 65, 2, Prioridad.ALTA),
                ActividadFormativa(3, "Documentar alcance inicial", null, 30, 5, Prioridad.MEDIA),
                ActividadFormativa(4, "Núcleo Kotlin de dominio", null, 100, -1, Prioridad.MEDIA),
                ActividadFormativa(
                    5,
                    "Documentar el alcance inicial del proyecto integrador completo de la ficha",
                    null, 45, 1, Prioridad.ALTA
                )
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Estado vacío")
@Composable
fun PantallaActividadesVaciaPreview() {
    MiFormacionCTMATheme {
        PantallaActividades(actividades = emptyList())
    }
}