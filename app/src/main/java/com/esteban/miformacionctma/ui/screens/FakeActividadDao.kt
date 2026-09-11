package com.esteban.miformacionctma.ui.screens

import com.esteban.miformacionctma.data.local.entity.ActividadConCategoria
import com.esteban.miformacionctma.data.local.entity.ActividadDao
import com.esteban.miformacionctma.data.local.entity.ActividadEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeActividadDao : ActividadDao {

    private val actividades = mutableListOf<ActividadEntity>()
    private val flujo = MutableStateFlow<List<ActividadEntity>>(emptyList())
    private var siguienteId = 1

    /** Snapshot directo para verificar en los tests, sin pasar por Flow. */
    fun snapshot(): List<ActividadEntity> = actividades.toList()

    private fun emitir() {
        flujo.value = actividades.toList()
    }

    override suspend fun insertar(actividad: ActividadEntity): Long {
        val nueva = actividad.copy(id = siguienteId++)
        actividades.add(nueva)
        emitir()
        return nueva.id.toLong()
    }

    override suspend fun actualizar(actividad: ActividadEntity) {
        val index = actividades.indexOfFirst { it.id == actividad.id }
        if (index != -1) {
            actividades[index] = actividad
            emitir()
        }
    }

    override suspend fun eliminar(actividad: ActividadEntity) {
        actividades.removeAll { it.id == actividad.id }
        emitir()
    }

    override suspend fun obtenerPorId(id: Int): ActividadEntity? =
        actividades.find { it.id == id }

    override fun observarTodas(): Flow<List<ActividadEntity>> = flujo.asStateFlow()

    override fun buscar(texto: String?, categoriaId: Int?): Flow<List<ActividadEntity>> =
        flujo.asStateFlow()

    override fun observarConCategoria(): Flow<List<ActividadConCategoria>> {
        throw NotImplementedError("No se usa en estos tests")
    }
}