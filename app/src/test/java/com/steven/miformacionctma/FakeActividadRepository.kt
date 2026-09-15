package com.steven.miformacionctma.ui

import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.ActividadRepository
import com.steven.miformacionctma.domain.ResultadoRefresh
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeActividadRepository : ActividadRepository {

    private val actividades = MutableStateFlow<List<ActividadFormativa>>(emptyList())
    var debeFallar = false

    override fun observar(textoBusqueda: String): Flow<List<ActividadFormativa>> {
        if (debeFallar) {
            throw IllegalStateException("Fallo simulado del repositorio")
        }
        return actividades.map { lista ->
            if (textoBusqueda.isBlank()) lista
            else lista.filter { it.titulo.contains(textoBusqueda, ignoreCase = true) }
        }
    }

    override suspend fun agregar(actividad: ActividadFormativa) {
        delay(100)
        actividades.value = actividades.value + actividad
    }

    override suspend fun eliminar(id: Long) {
        actividades.value = actividades.value.filter { it.id != id }
    }

    override fun buscarPorId(id: Long): ActividadFormativa? =
        actividades.value.find { it.id == id }

    override suspend fun refrescar(): ResultadoRefresh {
        return if (debeFallar) {
            ResultadoRefresh.Fallido("Fallo simulado de refresh")
        } else {
            ResultadoRefresh.Exitoso
        }
    }
}