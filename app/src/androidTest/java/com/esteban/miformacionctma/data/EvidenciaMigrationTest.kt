package com.esteban.miformacionctma.data

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EvidenciaMigrationTest {

    private lateinit var db: SupportSQLiteDatabase

    @Before
    fun setUp() {
        val factory = FrameworkSQLiteOpenHelperFactory()
        val config = SupportSQLiteOpenHelper.Configuration.builder(
            ApplicationProvider.getApplicationContext()
        )
            .name("migracion-test.db")
            .callback(object : SupportSQLiteOpenHelper.Callback(1) {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    db.execSQL(CREATE_ACTIVIDADES_V1)
                }

                override fun onUpgrade(
                    db: SupportSQLiteDatabase,
                    oldVersion: Int,
                    newVersion: Int
                ) = Unit
            })
            .build()

        db = factory.create(config).writableDatabase
        db.execSQL(
            "INSERT INTO actividades (id, titulo, descripcion, fecha, prioridad, progreso) " +
                "VALUES (1, 'Tarea inicial', 'Detalle', '2026-09-10', 'Alta', 0)"
        )
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun migracion_1_2_crea_tabla_evidencias_con_columnas_esperadas() {
        MIGRATION_1_2.migrate(db)

        val existe = existsTable("evidencias")
        assertTrue(existe)

        val columnas = columnNames("evidencias")
        assertTrue("id" in columnas)
        assertTrue("actividadId" in columnas)
        assertTrue("uri" in columnas)
        assertTrue("tipoMime" in columnas)
        assertTrue("tamanoBytes" in columnas)
        assertTrue("estado" in columnas)
    }

    @Test
    fun migracion_1_2_crea_indice_y_no_pierde_datos_existentes() {
        MIGRATION_1_2.migrate(db)

        val indice = db.query(
            "SELECT name FROM sqlite_master WHERE type='index' AND name='index_evidencias_actividadId'"
        ).use { cursor ->
            cursor.moveToFirst() && cursor.count == 1
        }
        assertTrue(indice)

        val actividad = db.query("SELECT titulo FROM actividades WHERE id = 1").use { cursor ->
            cursor.moveToFirst()
            cursor.getString(0)
        }
        assertEquals("Tarea inicial", actividad)
    }

    @Test
    fun migracion_1_2_permita_insertar_y_leer_evidencias() {
        MIGRATION_1_2.migrate(db)

        db.execSQL(
            "INSERT INTO evidencias (id, actividadId, uri, tipoMime, tamanoBytes, estado) " +
                "VALUES (1, 1, 'content://evidencia/imagen.jpg', 'image/jpeg', 2048, 'LOCAL')"
        )

        val uri = db.query("SELECT uri, tipoMime, tamanoBytes, estado FROM evidencias WHERE id = 1")
            .use { cursor ->
                cursor.moveToFirst()
                Triple(cursor.getString(0), cursor.getString(1), cursor.getLong(2))
            }
        assertEquals("content://evidencia/imagen.jpg", uri.first)
        assertEquals("image/jpeg", uri.second)
        assertEquals(2048L, uri.third)
    }

    @Test
    fun migracion_1_2_es_idempotente() {
        MIGRATION_1_2.migrate(db)
        MIGRATION_1_2.migrate(db)

        assertTrue(existsTable("evidencias"))
    }

    @Test
    fun migracion_1_2_aplica_borrado_en_cascada() {
        MIGRATION_1_2.migrate(db)
        db.execSQL(
            "INSERT INTO evidencias (id, actividadId, uri, tipoMime, tamanoBytes, estado) " +
                "VALUES (1, 1, 'content://e', 'image/png', 100, 'LOCAL')"
        )
        db.execSQL("PRAGMA foreign_keys = ON")
        db.execSQL("DELETE FROM actividades WHERE id = 1")

        val restantes = db.query("SELECT COUNT(*) FROM evidencias").use { cursor ->
            cursor.moveToFirst()
            cursor.getInt(0)
        }
        assertEquals(0, restantes)
    }

    private fun existsTable(tabla: String): Boolean {
        return db.query(
            "SELECT name FROM sqlite_master WHERE type='table' AND name='$tabla'"
        ).use { cursor ->
            cursor.moveToFirst() && cursor.count == 1
        }
    }

    private fun columnNames(tabla: String): List<String> {
        return db.query("PRAGMA table_info('$tabla')").use { cursor ->
            val nombres = mutableListOf<String>()
            while (cursor.moveToNext()) {
                nombres.add(cursor.getString(1))
            }
            nombres
        }
    }

    private companion object {
        const val CREATE_ACTIVIDADES_V1 =
            "CREATE TABLE IF NOT EXISTS `actividades` (" +
                "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "`titulo` TEXT NOT NULL, `descripcion` TEXT NOT NULL, " +
                "`fecha` TEXT NOT NULL, `prioridad` TEXT NOT NULL, `progreso` INTEGER NOT NULL)"
    }
}