package com.esteban.miformacionctma.data.remote

import com.esteban.miformacionctma.data.dto.ActividadDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ActividadesApi {

    @GET("actividades")
    suspend fun getActividades(): Response<List<ActividadDTO>>

    @GET("actividades/{id}")
    suspend fun getActividadById(@Path("id") id: Int): Response<ActividadDTO>

    @POST("actividades")
    suspend fun createActividad(@Body actividad: ActividadDTO): Response<ActividadDTO>

    @PUT("actividades/{id}")
    suspend fun updateActividad(
        @Path("id") id: Int,
        @Body actividad: ActividadDTO
    ): Response<ActividadDTO>

    @DELETE("actividades/{id}")
    suspend fun deleteActividad(@Path("id") id: Int): Response<Unit>
}
