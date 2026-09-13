package com.steven.miformacionctma

import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.steven.miformacionctma.data.AppDatabase
import com.steven.miformacionctma.data.MIGRACION_1_A_2
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MigracionTest {

    private val nombreBase = "migracion-manual-test.db"
    private lateinit var contexto: android.content.Context

    @Before
    fun prepararEntorno() {
        contexto = InstrumentationRegistry.getInstrumentation().targetContext
        contexto.deleteDatabase(nombreBase)
    }

    @After
    fun limpiarEntorno() {
        contexto.deleteDatabase(nombreBase)
    }

    @Test
    fun migracion1a2_agregaColumnaCompletadaSinPerderDatos() {
        val rutaBase = contexto.getDatabasePath(nombreBase).path

        val dbVieja = SQLiteDatabase.openOrCreateDatabase(rutaBase, null)

        // Tabla categorias primero (actividades depende de ella por llave foránea)
        dbVieja.execSQL(
            """
            CREATE TABLE categorias (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                nombre TEXT NOT NULL
            )
            """.trimIndent()
        )

        // Tabla actividades SIN la columna completada (así estaba en la versión 1 real)
        dbVieja.execSQL(
            """
            CREATE TABLE actividades (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                titulo TEXT NOT NULL,
                descripcion TEXT,
                progreso INTEGER NOT NULL,
                diasRestantes INTEGER NOT NULL,
                prioridad TEXT NOT NULL,
                categoriaId INTEGER,
                FOREIGN KEY(categoriaId) REFERENCES categorias(id) ON UPDATE NO ACTION ON DELETE SET NULL
            )
            """.trimIndent()
        )

        dbVieja.execSQL(
            "CREATE INDEX index_actividades_categoriaId ON actividades (categoriaId)"
        )

        dbVieja.execSQL(
            """
            INSERT INTO actividades (id, titulo, descripcion, progreso, diasRestantes, prioridad)
            VALUES (1, 'Actividad previa a la migracion', NULL, 40, 2, 'MEDIA')
            """.trimIndent()
        )
        dbVieja.version = 1
        dbVieja.close()

        val database = Room.databaseBuilder(contexto, AppDatabase::class.java, nombreBase)
            .addMigrations(MIGRACION_1_A_2)
            .build()

        val actividad = kotlinx.coroutines.runBlocking {
            database.actividadDao().obtenerPorId(1L)
        }

        assertEquals("Actividad previa a la migracion", actividad?.titulo)
        assertEquals(false, actividad?.completada)

        database.close()
    }
}