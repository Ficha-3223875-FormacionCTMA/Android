package com.esteban.miformacionctma.data.remote

interface TokenProvider {
    suspend fun getToken(): String?
}
