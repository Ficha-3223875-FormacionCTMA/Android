package com.esteban.miformacionctma.data

import com.esteban.miformacionctma.Actividad
import kotlinx.coroutines.flow.Flow

class ActividadRepository(private val dao: ActividadDao) {

    val actividades: Flow<List<Actividad>> = dao.getAllActividades()

    suspend fun insert(actividad: Actividad) {
        dao.insert(actividad)
    }

    suspend fun update(actividad: Actividad) {
        dao.update(actividad)
    }

    suspend fun delete(actividad: Actividad) {
        dao.delete(actividad)
    }
}