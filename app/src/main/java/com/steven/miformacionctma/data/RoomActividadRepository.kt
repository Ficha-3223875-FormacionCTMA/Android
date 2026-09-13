package com.steven.miformacionctma.data

import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.ActividadRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RoomActividadRepository(
    private val dao: ActividadDao,
    private val scope: CoroutineScope
) : ActividadRepository {

    override val actividades: StateFlow<List<ActividadFormativa>> =
        dao.obtenerTodas()
            .map { entidades -> entidades.map { it.aDominio() } }
            .stateIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    override fun agregar(actividad: ActividadFormativa) {
        scope.launch {
            dao.insertar(actividad.aEntidad())
        }
    }

    override fun buscarPorId(id: Long): ActividadFormativa? =
        actividades.value.find { it.id == id }
}