package com.steven.miformacionctma.data

import com.steven.miformacionctma.data.remote.RemoteActividadDataSource
import com.steven.miformacionctma.data.remote.ResultadoRed
import com.steven.miformacionctma.data.remote.TipoErrorRed
import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.ActividadRepository
import com.steven.miformacionctma.domain.ResultadoRefresh
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomActividadRepository(
    private val dao: ActividadDao,
    private val remoto: RemoteActividadDataSource
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

    override suspend fun refrescar(): ResultadoRefresh {
        return when (val resultado = remoto.obtenerActividades()) {
            is ResultadoRed.Exito -> {
                val entidades = resultado.datos.map { dto ->
                    ActividadEntity(
                        id = dto.id,
                        titulo = dto.titulo,
                        descripcion = dto.descripcion,
                        progreso = dto.progreso,
                        diasRestantes = dto.diasRestantes,
                        prioridad = dto.prioridad
                    )
                }
                dao.reemplazarTodas(entidades)
                ResultadoRefresh.Exitoso
            }
            is ResultadoRed.Error -> {
                val mensaje = mensajeParaError(resultado.tipo)
                ResultadoRefresh.Fallido(
                    mensaje = mensaje,
                    sesionVencida = resultado.tipo == TipoErrorRed.SESION_VENCIDA
                )
            }
        }
    }

    private fun mensajeParaError(tipo: TipoErrorRed): String = when (tipo) {
        TipoErrorRed.SIN_CONEXION -> "Sin conexión a internet. Mostrando datos guardados."
        TipoErrorRed.TIMEOUT -> "El servidor tardó demasiado en responder."
        TipoErrorRed.SESION_VENCIDA -> "Tu sesión venció. Inicia sesión de nuevo."
        TipoErrorRed.NO_ENCONTRADO -> "No se encontró el recurso solicitado."
        TipoErrorRed.ERROR_SERVIDOR -> "El servidor tuvo un problema. Intenta más tarde."
        TipoErrorRed.JSON_INVALIDO -> "La respuesta del servidor no se pudo interpretar."
        TipoErrorRed.DESCONOCIDO -> "Ocurrió un error inesperado."
    }
}