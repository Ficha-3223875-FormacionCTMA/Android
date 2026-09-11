package com.esteban.miformacionctma.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.esteban.miformacionctma.Actividad
import kotlinx.coroutines.flow.Flow

@Dao
interface ActividadDao {

    @Query("SELECT * FROM actividades ORDER BY CASE prioridad WHEN 'Alta' THEN 1 WHEN 'Media' THEN 2 ELSE 3 END, fecha DESC")
    fun getAllActividades(): Flow<List<Actividad>>

    @Query("SELECT * FROM actividades WHERE id = :id")
    suspend fun getActividadById(id: Int): Actividad?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(actividad: Actividad): Long

    @Update
    suspend fun update(actividad: Actividad)

    @Delete
    suspend fun delete(actividad: Actividad)
}