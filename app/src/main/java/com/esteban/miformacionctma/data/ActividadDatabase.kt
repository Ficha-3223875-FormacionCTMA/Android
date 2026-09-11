package com.esteban.miformacionctma.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.esteban.miformacionctma.Actividad

@Database(entities = [Actividad::class], version = 1, exportSchema = false)
abstract class ActividadDatabase : RoomDatabase() {

    abstract fun actividadDao(): ActividadDao

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
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}