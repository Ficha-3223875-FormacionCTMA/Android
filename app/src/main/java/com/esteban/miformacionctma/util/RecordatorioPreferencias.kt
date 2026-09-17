package com.esteban.miformacionctma.util

import android.content.Context

object RecordatorioPreferencias {
    private const val NOMBRE_ARCHIVO = "recordatorios_prefs"
    private const val CLAVE_ACTIVADOS = "activados"

    fun activados(context: Context): Boolean {
        val prefs = context.getSharedPreferences(NOMBRE_ARCHIVO, Context.MODE_PRIVATE)
        return prefs.getBoolean(CLAVE_ACTIVADOS, false)
    }

    fun guardar(context: Context, activados: Boolean) {
        val prefs = context.getSharedPreferences(NOMBRE_ARCHIVO, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(CLAVE_ACTIVADOS, activados).apply()
    }
}