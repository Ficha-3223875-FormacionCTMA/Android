package com.esteban.miformacionctma.data.local.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EvidenciaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(evidencia: EvidenciaEntity)

    @Query("SELECT * FROM evidencias WHERE id = :id")
    suspend fun obtenerPorId(id: String): EvidenciaEntity?

    @Query("SELECT * FROM evidencias WHERE actividadId = :actividadId ORDER BY creadaEnEpochMillis DESC")
    fun observarPorActividad(actividadId: Int): Flow<List<EvidenciaEntity>>

    @Query("SELECT * FROM evidencias ORDER BY creadaEnEpochMillis DESC")
    fun observarTodas(): Flow<List<EvidenciaEntity>>

    @Query("UPDATE evidencias SET estado = :estado WHERE id = :id")
    suspend fun actualizarEstado(id: String, estado: String)

    @Query("DELETE FROM evidencias WHERE id = :id")
    suspend fun eliminarPorId(id: String)
}