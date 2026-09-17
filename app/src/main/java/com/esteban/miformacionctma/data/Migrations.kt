package com.esteban.miformacionctma.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `evidencias` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `actividadId` INTEGER NOT NULL,
                `uri` TEXT NOT NULL,
                `tipoMime` TEXT NOT NULL,
                `tamanoBytes` INTEGER NOT NULL,
                `estado` TEXT NOT NULL,
                FOREIGN KEY(`actividadId`) REFERENCES `actividades`(`id`)
                    ON UPDATE NO ACTION ON DELETE CASCADE
            )
            """.trimIndent()
        )
        db.execSQL(
            "CREATE INDEX IF NOT EXISTS `index_evidencias_actividadId` ON `evidencias` (`actividadId`)"
        )
    }
}