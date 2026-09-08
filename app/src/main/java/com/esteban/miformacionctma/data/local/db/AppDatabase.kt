package com.esteban.miformacionctma.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.esteban.miformacionctma.data.local.entity.ActividadDao
import com.esteban.miformacionctma.data.local.entity.ActividadEntity
import com.esteban.miformacionctma.data.local.entity.CategoriaDao
import com.esteban.miformacionctma.data.local.entity.CategoriaEntity

@Database(
    entities = [ActividadEntity::class, CategoriaEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun actividadDao(): ActividadDao
    abstract fun categoriaDao(): CategoriaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun obtenerInstancia(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "miformacionctma.db"
                ).build()

                INSTANCE = instancia
                instancia
            }
        }
    }
}