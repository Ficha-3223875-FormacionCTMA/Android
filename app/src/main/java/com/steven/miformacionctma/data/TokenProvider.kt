package com.steven.miformacionctma.data

class TokenProvider {
    fun obtenerToken(): String? = null

    fun limpiarSesion() {
        // Aquí se borraría el token guardado cuando el servidor responde 401.
    }
}