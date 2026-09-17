package com.esteban.miformacionctma.di

import android.content.Context
import com.esteban.miformacionctma.data.local.db.AppDatabase
import com.esteban.miformacionctma.data.remote.FakeEvidenciasRemoto
import com.esteban.miformacionctma.repository.ActividadRepository
import com.esteban.miformacionctma.repository.EvidenciaRepository

class AppContainer(context: Context) {

    private val database: AppDatabase = AppDatabase.obtenerInstancia(context)

    val actividadRepository: ActividadRepository by lazy {
        ActividadRepository(database.actividadDao())
    }

    val evidenciaRepository: EvidenciaRepository by lazy {
        EvidenciaRepository(
            evidenciaDao = database.evidenciaDao(),
            remoto = FakeEvidenciasRemoto()
        )
    }
}