package com.steven.miformacionctma.domain

import kotlinx.coroutines.flow.StateFlow

interface ActividadRepository {
    val actividades: StateFlow<List<ActividadFormativa>>
    fun agregar(actividad: ActividadFormativa)
    fun buscarPorId(id: Long): ActividadFormativa?
}