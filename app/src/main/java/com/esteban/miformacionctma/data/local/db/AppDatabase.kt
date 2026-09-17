package com.esteban.miformacionctma.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.esteban.miformacionctma.data.local.entity.ActividadDao
import com.esteban.miformacionctma.data.local.entity.ActividadEntity
import com.esteban.miformacionctma.data.local.entity.CategoriaDao
import com.esteban.miformacionctma.data.local.entity.CategoriaEntity
import com.esteban.miformacionctma.data.local.entity.EvidenciaDao
import com.esteban.miformacionctma.data.local.entity.EvidenciaEntity

@Database(
    entities = [ActividadEntity::class, CategoriaEntity::class, EvidenciaEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun actividadDao(): ActividadDao
    abstract fun categoriaDao(): CategoriaDao
    abstract fun evidenciaDao(): EvidenciaDao

    companion object {
        const val NOMBRE_DB = "miformacionctma.db"

        val MIGRACION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `evidencias` (
                        `id` TEXT NOT NULL PRIMARY KEY,
                        `actividadId` INTEGER NOT NULL,
                        `localUri` TEXT NOT NULL,
                        `mimeType` TEXT NOT NULL,
                        `sizeBytes` INTEGER NOT NULL,
                        `estado` TEXT NOT NULL,
                        `creadaEnEpochMillis` INTEGER NOT NULL,
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

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun obtenerInstancia(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    NOMBRE_DB
                )
                    .addMigrations(MIGRACION_1_2)
                    .build()

                INSTANCE = instancia
                instancia
            }
        }
    }
}