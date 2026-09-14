package com.steven.miformacionctma.ui

import com.steven.miformacionctma.domain.PreferenciasRepositoryContrato
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakePreferenciasRepository : PreferenciasRepositoryContrato {

    private val orden = MutableStateFlow("id_asc")
    private val modoGrid = MutableStateFlow(false)

    override val ordenPreferido: Flow<String> = orden
    override val modoGridPreferido: Flow<Boolean> = modoGrid

    override suspend fun guardarOrden(nuevoOrden: String) {
        orden.value = nuevoOrden
    }

    override suspend fun guardarModoGrid(activo: Boolean) {
        modoGrid.value = activo
    }
}