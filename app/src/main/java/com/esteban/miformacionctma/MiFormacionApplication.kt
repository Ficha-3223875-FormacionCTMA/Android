package com.esteban.miformacionctma

import android.app.Application
import com.esteban.miformacionctma.di.AppContainer

class MiFormacionApplication : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}