package com.steven.miformacionctma.data.remote

import kotlinx.serialization.SerializationException
import java.io.IOException
import java.net.SocketTimeoutException

class RemoteActividadDataSource(private val api: ActividadApi) {

    suspend fun obtenerActividades(): ResultadoRed<List<ActividadDto>> {
        return try {
            val respuesta = api.obtenerActividades()
            if (respuesta.isSuccessful) {
                ResultadoRed.Exito(respuesta.body() ?: emptyList())
            } else {
                ResultadoRed.Error(clasificarCodigoHttp(respuesta.code()))
            }
        } catch (excepcion: SocketTimeoutException) {
            ResultadoRed.Error(TipoErrorRed.TIMEOUT)
        } catch (excepcion: SerializationException) {
            ResultadoRed.Error(TipoErrorRed.JSON_INVALIDO)
        } catch (excepcion: IOException) {
            ResultadoRed.Error(TipoErrorRed.SIN_CONEXION)
        }
    }

    private fun clasificarCodigoHttp(codigo: Int): TipoErrorRed = when (codigo) {
        401 -> TipoErrorRed.SESION_VENCIDA
        404 -> TipoErrorRed.NO_ENCONTRADO
        in 500..599 -> TipoErrorRed.ERROR_SERVIDOR
        else -> TipoErrorRed.DESCONOCIDO
    }
}

enum class TipoErrorRed {
    SIN_CONEXION, TIMEOUT, SESION_VENCIDA, NO_ENCONTRADO, ERROR_SERVIDOR, JSON_INVALIDO, DESCONOCIDO
}

sealed interface ResultadoRed<out T> {
    data class Exito<T>(val datos: T) : ResultadoRed<T>
    data class Error(val tipo: TipoErrorRed) : ResultadoRed<Nothing>
}