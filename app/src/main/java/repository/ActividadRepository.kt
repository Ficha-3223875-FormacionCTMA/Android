package com.esteban.miformacionctma.repository

import com.esteban.miformacionctma.data.local.entity.ActividadEntity
import com.esteban.miformacionctma.data.local.entity.ActividadConCategoria
import com.esteban.miformacionctma.data.local.entity.ActividadDao
import kotlinx.coroutines.flow.Flow

class ActividadRepository(
    private val actividadDao: ActividadDao
) {

    suspend fun insertar(actividad: ActividadEntity): Long =
        actividadDao.insertar(actividad)

    suspend fun actualizar(actividad: ActividadEntity) =
        actividadDao.actualizar(actividad)

    suspend fun eliminar(actividad: ActividadEntity) =
        actividadDao.eliminar(actividad)

    suspend fun obtenerPorId(id: Int): ActividadEntity? =
        actividadDao.obtenerPorId(id)

    fun observarTodas(): Flow<List<ActividadEntity>> =
        actividadDao.observarTodas()

    fun buscar(
        texto: String?,
        categoriaId: Int?
    ): Flow<List<ActividadEntity>> =
        actividadDao.buscar(texto, categoriaId)

    fun observarConCategoria(): Flow<List<ActividadConCategoria>> =
        actividadDao.observarConCategoria()
}