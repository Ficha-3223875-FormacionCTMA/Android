package com.steven.miformacionctma.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRACION_1_A_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "ALTER TABLE actividades ADD COLUMN completada INTEGER NOT NULL DEFAULT 0"
        )
    }
}