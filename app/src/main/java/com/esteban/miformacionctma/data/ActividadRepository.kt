package com.esteban.miformacionctma.data

import com.esteban.miformacionctma.Actividad
import com.esteban.miformacionctma.data.dto.ActividadDTO
import com.esteban.miformacionctma.data.mapper.toDTO
import com.esteban.miformacionctma.data.mapper.toDTOList
import com.esteban.miformacionctma.data.mapper.toEntity
import com.esteban.miformacionctma.data.mapper.toEntityList
import com.esteban.miformacionctma.data.remote.RemoteDatasource
import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ActividadRepository(
    private val dao: ActividadDao,
    private val remote: RemoteDatasource,
    private val db: ActividadDatabase
) {

    val actividades: Flow<List<Actividad>> = dao.getAllActividades()

    val actividadesDTO: Flow<List<ActividadDTO>> = dao.getAllActividades()
        .map { list -> list.toDTOList() }

    suspend fun syncFromRemote(): Result<Unit> {
        return remote.getActividades().fold(
            onSuccess = { list ->
                try {
                    val entities = list.toEntityList()
                    db.withTransaction {
                        dao.replaceRemoteSnapshot(entities)
                    }
                    Result.success(Unit)
                } catch (e: Exception) {
                    Result.failure(e.classify())
                }
            },
            onFailure = { e ->
                Result.failure(e.classify())
            }
        )
    }

    suspend fun pushToRemote(actividad: Actividad): Result<ActividadDTO> {
        return remote.createActividad(actividad.toDTO())
    }

    suspend fun insert(actividad: Actividad): Result<Unit> {
        return try {
            dao.insert(actividad)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e.classify())
        }
    }

    suspend fun insertDTO(dto: ActividadDTO): Result<Unit> {
        return try {
            dao.insert(dto.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e.classify())
        }
    }

    suspend fun update(actividad: Actividad): Result<Unit> {
        return try {
            dao.update(actividad)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e.classify())
        }
    }

    suspend fun updateDTO(dto: ActividadDTO): Result<Unit> {
        return try {
            dao.update(dto.toEntity())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e.classify())
        }
    }

    suspend fun delete(actividad: Actividad): Result<Unit> {
        return try {
            dao.delete(actividad)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e.classify())
        }
    }

    suspend fun getDTOById(id: Int): Result<ActividadDTO?> {
        return try {
            Result.success(dao.getActividadById(id)?.toDTO())
        } catch (e: Exception) {
            Result.failure(e.classify())
        }
    }
}