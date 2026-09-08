package com.esteban.miformacionctma.data.di

import android.content.Context
import com.esteban.miformacionctma.data.local.db.AppDatabase

class AppContainer(context: Context) {

    private val database: AppDatabase = AppDatabase.obtenerInstancia(context)

    val actividadDao = database.actividadDao()
    val categoriaDao = database.categoriaDao()
}