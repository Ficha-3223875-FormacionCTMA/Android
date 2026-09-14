package com.steven.miformacionctma.domain

import kotlinx.coroutines.flow.Flow

interface ActividadRepository {
    fun observar(textoBusqueda: String): Flow<List<ActividadFormativa>>
    suspend fun agregar(actividad: ActividadFormativa)
    suspend fun eliminar(id: Long)
    fun buscarPorId(id: Long): ActividadFormativa?
}