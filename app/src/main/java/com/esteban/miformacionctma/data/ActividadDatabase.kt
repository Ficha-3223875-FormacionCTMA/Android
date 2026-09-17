package com.esteban.miformacionctma.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.esteban.miformacionctma.Actividad
import com.esteban.miformacionctma.Evidencia

@Database(entities = [Actividad::class, Evidencia::class], version = 2, exportSchema = false)
abstract class ActividadDatabase : RoomDatabase() {

    abstract fun actividadDao(): ActividadDao

    abstract fun evidenciaDao(): EvidenciaDao

    companion object {
        @Volatile
        private var INSTANCE: ActividadDatabase? = null

        fun getInstance(context: Context): ActividadDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ActividadDatabase::class.java,
                    "miformacion_ctma.db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}