package com.esteban.miformacionctma.di

import android.content.Context
import androidx.room.Room
import com.esteban.miformacionctma.data.local.db.AppDatabase
import com.esteban.miformacionctma.repository.ActividadRepository

class AppContainer(context: Context) {

    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "miformacionctma.db"
    ).build()

    val actividadRepository: ActividadRepository by lazy {
        ActividadRepository(database.actividadDao())
    }
}