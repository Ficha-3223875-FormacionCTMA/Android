package com.steven.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items as itemsLista
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items as itemsGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.ListadoUiState
import com.steven.miformacionctma.domain.OperacionUiState
import com.steven.miformacionctma.domain.Prioridad
import com.steven.miformacionctma.ui.components.TarjetaActividad
import com.steven.miformacionctma.ui.theme.MiFormacionCTMATheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaActividades(
    uiState: ListadoUiState,
    operacion: OperacionUiState = OperacionUiState.Inactiva,
    modoGridManual: Boolean = false,
    onCambiarModoGrid: (Boolean) -> Unit = {},
    onBuscar: (String) -> Unit = {},
    onActividadClick: (ActividadFormativa) -> Unit = {},
    onAgregarClick: () -> Unit = {},
    onRefrescarClick: () -> Unit = {}
) {
    var textoBusqueda by remember { mutableStateOf("") }
    val refrescando = operacion is OperacionUiState.EnCurso

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis actividades") },
                actions = {
                    IconButton(onClick = onRefrescarClick) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Refrescar desde el servidor")
                    }
                    IconButton(onClick = { onCambiarModoGrid(!modoGridManual) }) {
                        Icon(
                            imageVector = if (modoGridManual) Icons.Filled.ViewList else Icons.Filled.GridView,
                            contentDescription = if (modoGridManual) "Cambiar a lista" else "Cambiar a grid"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregarClick) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar actividad")
            }
        }
    ) { paddingInterno ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingInterno)) {
            if (refrescando) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            if (operacion is OperacionUiState.Fallida) {
                Text(
                    text = operacion.mensaje,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }

            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = {
                    textoBusqueda = it
                    onBuscar(it)
                },
                label = { Text("Buscar actividad") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            when (uiState) {
                is ListadoUiState.Cargando -> EstadoCargando()
                is ListadoUiState.Vacio -> EstadoVacio()
                is ListadoUiState.Error -> EstadoError(
                    mensaje = uiState.mensaje,
                    onReintentar = {
                        textoBusqueda = ""
                        onBuscar("")
                    }
                )
                is ListadoUiState.Contenido -> ContenidoAdaptable(
                    actividades = uiState.actividades,
                    forzarGrid = modoGridManual,
                    onActividadClick = onActividadClick,
                    contentPadding = PaddingValues(0.dp)
                )
            }
        }
    }
}

@Composable
private fun ContenidoAdaptable(
    actividades: List<ActividadFormativa>,
    forzarGrid: Boolean,
    onActividadClick: (ActividadFormativa) -> Unit,
    contentPadding: PaddingValues
) {
    if (forzarGrid) {
        CuadriculaActividades(actividades, onActividadClick, contentPadding)
    } else {
        ListaActividades(actividades, onActividadClick, contentPadding)
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
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
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
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsGrid(items = actividades, key = { it.id }) { actividad ->
            TarjetaActividad(actividad = actividad, onClick = onActividadClick)
        }
    }
}

@Composable
private fun EstadoCargando() {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text(
            text = "Cargando actividades…",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Composable
private fun EstadoVacio() {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Aún no tienes actividades", style = MaterialTheme.typography.titleMedium)
        Text(
            text = "Cuando tu instructor asigne una actividad, aparecerá aquí.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun EstadoError(mensaje: String, onReintentar: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Ocurrió un problema", style = MaterialTheme.typography.titleMedium)
        Text(
            text = mensaje,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error
        )
        Button(onClick = onReintentar, modifier = Modifier.padding(top = 12.dp)) {
            Text("Reintentar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaActividadesPreview() {
    MiFormacionCTMATheme {
        PantallaActividades(
            uiState = ListadoUiState.Contenido(
                listOf(
                    ActividadFormativa(1, "Configurar Android Studio", null, 100, -3, Prioridad.BAJA),
                    ActividadFormativa(2, "Laboratorio Compose", null, 65, 2, Prioridad.ALTA)
                )
            )
        )
    }
}

@Preview(showBackground = true, name = "Vacío")
@Composable
fun PantallaActividadesVaciaPreview() {
    MiFormacionCTMATheme {
        PantallaActividades(uiState = ListadoUiState.Vacio)
    }
}

@Preview(showBackground = true, name = "Cargando")
@Composable
fun PantallaActividadesCargandoPreview() {
    MiFormacionCTMATheme {
        PantallaActividades(uiState = ListadoUiState.Cargando)
    }
}

@Preview(showBackground = true, name = "Error")
@Composable
fun PantallaActividadesErrorPreview() {
    MiFormacionCTMATheme {
        PantallaActividades(uiState = ListadoUiState.Error("No se pudo conectar con la base de datos"))
    }
}