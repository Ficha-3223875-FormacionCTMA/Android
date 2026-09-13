package com.steven.miformacionctma.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ActividadEntity::class, CategoriaEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun actividadDao(): ActividadDao
}