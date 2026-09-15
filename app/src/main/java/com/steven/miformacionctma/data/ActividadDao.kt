package com.steven.miformacionctma.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ActividadDao {

    @Query("SELECT * FROM actividades ORDER BY id ASC")
    fun obtenerTodas(): Flow<List<ActividadEntity>>

    @Query("SELECT * FROM actividades WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: Long): ActividadEntity?

    @Insert
    suspend fun insertar(actividad: ActividadEntity): Long

    @Query("DELETE FROM actividades WHERE id = :id")
    suspend fun eliminar(id: Long)

    @Query("SELECT * FROM actividades WHERE titulo LIKE '%' || :texto || '%' ORDER BY id ASC")
    fun buscarPorTitulo(texto: String): Flow<List<ActividadEntity>>

    @Transaction
    @Query("SELECT * FROM actividades ORDER BY id ASC")
    fun obtenerTodasConCategoria(): Flow<List<ActividadConCategoria>>

    @Insert
    suspend fun insertarCategoria(categoria: CategoriaEntity): Long

    @Query("SELECT * FROM categorias ORDER BY nombre ASC")
    fun obtenerCategorias(): Flow<List<CategoriaEntity>>

    @Transaction
    suspend fun reemplazarTodas(actividades: List<ActividadEntity>) {
        borrarTodas()
        actividades.forEach { insertarOActualizar(it) }
    }

    @Query("DELETE FROM actividades")
    suspend fun borrarTodas()

    @Upsert
    suspend fun insertarOActualizar(actividad: ActividadEntity)
}