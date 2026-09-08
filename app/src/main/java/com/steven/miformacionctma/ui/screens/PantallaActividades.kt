package com.steven.miformacionctma.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
    modifier: Modifier = Modifier
) {
    var filtroSeleccionado by remember { mutableStateOf<EstadoActividad?>(null) }

    val actividadesFiltradas = remember(actividades, filtroSeleccionado) {
        if (filtroSeleccionado == null) actividades
        else actividades.filter { it.estado == filtroSeleccionado }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mi Formación CTMA",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = onRecargar) {
                        Icon(Icons.Default.Refresh, contentDescription = "Recargar actividades")
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
                onClick = { /* Próxima implementación: Crear actividad */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nueva Actividad")
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Seccion de Filtros Rápidos
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = filtroSeleccionado == null,
                        onClick = { filtroSeleccionado = null },
                        label = { Text("Todas (${actividades.size})") }
                    )
                }
                items(EstadoActividad.values().toList()) { estado ->
                    val cantidad = actividades.count { it.estado == estado }
                    FilterChip(
                        selected = filtroSeleccionado == estado,
                        onClick = {
                            filtroSeleccionado = if (filtroSeleccionado == estado) null else estado
                        },
                        label = { Text("${estado.etiqueta} ($cantidad)") }
                    )
                }
            }

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                if (actividadesFiltradas.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No hay actividades en esta categoría.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedButton(onClick = { filtroSeleccionado = null }) {
                            Text("Limpiar filtros")
                        }
                    }
                } else if (maxWidth < 600.dp) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 80.dp, top = 4.dp)
                    ) {
                        items(
                            items = actividadesFiltradas,
                            key = { it.id }
                        ) { actividad ->
                            TarjetaActividad(
                                actividad = actividad,
                                onClick = { onActividadClick(actividad) }
                            )
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 80.dp, top = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = actividadesFiltradas,
                            key = { it.id }
                        ) { actividad ->
                            TarjetaActividad(
                                actividad = actividad,
                                onClick = { onActividadClick(actividad) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Vista Celular", showBackground = true)
@Composable
fun PantallaActividadesPreview() {
    PantallaActividades(
        actividades = listOf(
            ActividadFormativa("1", "Guía 3: Compose", "Pantalla declarativa y adaptable.", "2026-09-15", EstadoActividad.EN_PROCESO, 65),
            ActividadFormativa("2", "Guía 6: Room", "Persistencia de datos.", "2026-09-22", EstadoActividad.PENDIENTE, 0),
            ActividadFormativa("3", "Guía 2: Kotlin Fundamental", "Sintaxis básica y POO.", "2026-09-01", EstadoActividad.COMPLETADA, 100)
        ),
        onActividadClick = {},
        onRecargar = {}
    )
}