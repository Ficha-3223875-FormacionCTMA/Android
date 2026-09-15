package com.steven.miformacionctma.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitModule {

    private val json = Json { ignoreUnknownKeys = true }

    fun crearApi(
        baseUrl: String,
        debug: Boolean,
        timeoutSegundos: Long = 10
    ): ActividadApi {
        val clienteHttp = OkHttpClient.Builder()
            .connectTimeout(timeoutSegundos, TimeUnit.SECONDS)
            .readTimeout(timeoutSegundos, TimeUnit.SECONDS)
            .writeTimeout(timeoutSegundos, TimeUnit.SECONDS)
            .apply {
                if (debug) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                }
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(clienteHttp)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()

        return retrofit.create(ActividadApi::class.java)
    }
}