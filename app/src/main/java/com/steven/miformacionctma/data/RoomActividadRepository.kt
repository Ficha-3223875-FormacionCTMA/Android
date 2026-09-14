package com.steven.miformacionctma.data

import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.ActividadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomActividadRepository(
    private val dao: ActividadDao
) : ActividadRepository {

    override fun observar(textoBusqueda: String): Flow<List<ActividadFormativa>> {
        val flowEntidades = if (textoBusqueda.isBlank()) {
            dao.obtenerTodas()
        } else {
            dao.buscarPorTitulo(textoBusqueda)
        }
        return flowEntidades.map { entidades -> entidades.map { it.aDominio() } }
    }

    override suspend fun agregar(actividad: ActividadFormativa) {
        dao.insertar(actividad.aEntidad())
    }

    override suspend fun eliminar(id: Long) {
        dao.eliminar(id)
    }

    override fun buscarPorId(id: Long): ActividadFormativa? = null
}