package com.esteban.miformacionctma.data.remote

import com.esteban.miformacionctma.data.dto.CatImageDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET("images/search")
    suspend fun getRandomCats(
        @Query("limit") limit: Int = 9
    ): Response<List<CatImageDTO>>
}