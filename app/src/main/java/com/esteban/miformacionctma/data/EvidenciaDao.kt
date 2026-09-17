package com.esteban.miformacionctma.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.esteban.miformacionctma.Evidencia
import com.esteban.miformacionctma.EstadoEvidencia
import kotlinx.coroutines.flow.Flow

@Dao
interface EvidenciaDao {

    @Query("SELECT * FROM evidencias WHERE actividadId = :actividadId")
    fun getEvidenciasByActividad(actividadId: Int): Flow<List<Evidencia>>

    @Query("SELECT * FROM evidencias WHERE id = :id")
    suspend fun getEvidenciaById(id: Int): Evidencia?

    @Query("SELECT * FROM evidencias WHERE actividadId = :actividadId")
    suspend fun getEvidenciasByActividadSusp(actividadId: Int): List<Evidencia>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(evidencia: Evidencia): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(evidencias: List<Evidencia>)

    @Update
    suspend fun update(evidencia: Evidencia)

    @Query("UPDATE evidencias SET estado = :estado WHERE id = :id")
    suspend fun updateEstado(id: Int, estado: EstadoEvidencia)

    @Delete
    suspend fun delete(evidencia: Evidencia)

    @Query("DELETE FROM evidencias WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM evidencias WHERE actividadId = :actividadId")
    suspend fun deleteByActividad(actividadId: Int)

    @Query("DELETE FROM evidencias")
    suspend fun deleteAll()
}