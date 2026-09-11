package com.esteban.miformacionctma.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    // Emulador Android -> 10.0.2.2
    // Dispositivo fisico -> IP de tu PC (ej: 192.168.1.100)
    const val BASE_URL_EMULADOR = "http://10.0.2.2:8000/"
    const val BASE_URL_FISICO = "http://192.168.1.100:8000/"

    const val BASE_URL = BASE_URL_EMULADOR

    fun createApi(client: OkHttpClient, baseUrl: String = BASE_URL): ActividadesApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ActividadesApi::class.java)
    }
}