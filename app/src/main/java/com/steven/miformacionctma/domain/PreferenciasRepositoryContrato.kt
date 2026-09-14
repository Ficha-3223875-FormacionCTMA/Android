package com.steven.miformacionctma.domain

import kotlinx.coroutines.flow.Flow

interface PreferenciasRepositoryContrato {
    val ordenPreferido: Flow<String>
    val modoGridPreferido: Flow<Boolean>
    suspend fun guardarOrden(orden: String)
    suspend fun guardarModoGrid(activo: Boolean)
}