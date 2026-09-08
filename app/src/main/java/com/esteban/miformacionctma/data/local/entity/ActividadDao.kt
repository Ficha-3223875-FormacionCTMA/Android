package com.esteban.miformacionctma.data.local.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ActividadDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertar(actividad: ActividadEntity): Long

    @Update
    suspend fun actualizar(actividad: ActividadEntity)

    @Delete
    suspend fun eliminar(actividad: ActividadEntity)

    @Query("SELECT * FROM actividades WHERE id = :id")
    suspend fun obtenerPorId(id: Int): ActividadEntity?

    @Query("SELECT * FROM actividades ORDER BY fecha DESC")
    fun observarTodas(): Flow<List<ActividadEntity>>

    @Query("""
        SELECT * FROM actividades
        WHERE (:texto IS NULL OR titulo LIKE '%' || :texto || '%' OR descripcion LIKE '%' || :texto || '%')
        AND (:categoriaId IS NULL OR categoriaId = :categoriaId)
        ORDER BY fecha DESC
    """)
    fun buscar(texto: String?, categoriaId: Int?): Flow<List<ActividadEntity>>

    @Transaction
    @Query("SELECT * FROM actividades ORDER BY fecha DESC")
    fun observarConCategoria(): Flow<List<ActividadConCategoria>>
}
