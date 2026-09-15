package com.steven.miformacionctma.domain

import kotlinx.coroutines.flow.Flow

interface ActividadRepository {
    fun observar(textoBusqueda: String): Flow<List<ActividadFormativa>>
    suspend fun agregar(actividad: ActividadFormativa)
    suspend fun eliminar(id: Long)
    fun buscarPorId(id: Long): ActividadFormativa?
    suspend fun refrescar(): ResultadoRefresh
}

sealed interface ResultadoRefresh {
    data object Exitoso : ResultadoRefresh
    data class Fallido(val mensaje: String, val sesionVencida: Boolean = false) : ResultadoRefresh
}