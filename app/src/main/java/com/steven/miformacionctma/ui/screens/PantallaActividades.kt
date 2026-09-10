package com.steven.miformacionctma.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.steven.miformacionctma.model.ActividadFormativa
import com.steven.miformacionctma.model.EstadoActividad
import com.steven.miformacionctma.ui.components.TarjetaActividad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaActividades(
    actividades: List<ActividadFormativa>,
    onActividadClick: (ActividadFormativa) -> Unit,
    onRecargar: () -> Unit,
    onAgregarActividad: (titulo: String, descripcion: String, fechaLimite: String) -> Unit = { _, _, _ -> }
) {
    var estadoFiltroSeleccionado by remember { mutableStateOf<EstadoActividad?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    val actividadesFiltradas = remember(actividades, estadoFiltroSeleccionado) {
        if (estadoFiltroSeleccionado == null) actividades
        else actividades.filter { it.estado == estadoFiltroSeleccionado }
    }

    val configuration = LocalConfiguration.current
    val esPantallaAncha = configuration.screenWidthDp >= 600

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Formación CTMA") },
                actions = {
                    IconButton(onClick = onRecargar) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Recargar actividades"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mostrarDialogo = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Actividad")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Barra de Filtros (Chips)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterChip(
                    selected = estadoFiltroSeleccionado == null,
                    onClick = { estadoFiltroSeleccionado = null },
                    label = { Text("Todas (${actividades.size})") }
                )
                FilterChip(
                    selected = estadoFiltroSeleccionado == EstadoActividad.PENDIENTE,
                    onClick = { estadoFiltroSeleccionado = EstadoActividad.PENDIENTE },
                    label = { Text("Pendiente (${actividades.count { it.estado == EstadoActividad.PENDIENTE }})") }
                )
                FilterChip(
                    selected = estadoFiltroSeleccionado == EstadoActividad.EN_PROCESO,
                    onClick = { estadoFiltroSeleccionado = EstadoActividad.EN_PROCESO },
                    label = { Text("En proceso (${actividades.count { it.estado == EstadoActividad.EN_PROCESO }})") }
                )
            }

            // Lista adaptable según el ancho de pantalla
            if (actividadesFiltradas.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay actividades en este estado.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else if (esPantallaAncha) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 300.dp),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.semantics {
                        contentDescription = "Grid de actividades formativas"
                    }
                ) {
                    items(actividadesFiltradas, key = { it.id }) { actividad ->
                        TarjetaActividad(
                            actividad = actividad,
                            onClick = { onActividadClick(actividad) }
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.semantics {
                        contentDescription = "Lista de actividades formativas"
                    }
                ) {
                    items(actividadesFiltradas, key = { it.id }) { actividad ->
                        TarjetaActividad(
                            actividad = actividad,
                            onClick = { onActividadClick(actividad) }
                        )
                    }
                }
            }
        }
    }

    // Modal para agregar una nueva actividad
    if (mostrarDialogo) {
        DialogoAgregarActividad(
            onDismiss = { mostrarDialogo = false },
            onGuardar = { titulo, descripcion, fechaLimite ->
                onAgregarActividad(titulo, descripcion, fechaLimite)
                mostrarDialogo = false
            }
        )
    }
}

@Composable
fun DialogoAgregarActividad(
    onDismiss: () -> Unit,
    onGuardar: (titulo: String, descripcion: String, fechaLimite: String) -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fechaLimite by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Nueva Actividad Formativa") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título de la guía/actividad") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = fechaLimite,
                    onValueChange = { fechaLimite = it },
                    label = { Text("Fecha límite (AAAA-MM-DD)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (titulo.isNotBlank()) {
                        onGuardar(titulo, descripcion, fechaLimite)
                    }
                },
                enabled = titulo.isNotBlank()
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}