package com.esteban.miformacionctma.data.remote

import com.esteban.miformacionctma.data.dto.ActividadDTO
import retrofit2.Response

class RemoteDatasource(private val api: ActividadesApi) {

    suspend fun getActividades(): Result<List<ActividadDTO>> {
        return safeApiCall { api.getActividades() }
    }

    suspend fun getActividadById(id: Int): Result<ActividadDTO> {
        return safeApiCall { api.getActividadById(id) }
    }

    suspend fun createActividad(actividad: ActividadDTO): Result<ActividadDTO> {
        return safeApiCall { api.createActividad(actividad) }
    }

    suspend fun updateActividad(id: Int, actividad: ActividadDTO): Result<ActividadDTO> {
        return safeApiCall { api.updateActividad(id, actividad) }
    }

    suspend fun deleteActividad(id: Int): Result<Unit> {
        return safeApiCall { api.deleteActividad(id) }
    }

    private suspend fun <T> safeApiCall(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Respuesta vacia del servidor"))
                }
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Error desconocido"
                Result.failure(Exception("Error ${response.code()}: $errorMsg"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexion: ${e.message}"))
        }
    }
}
