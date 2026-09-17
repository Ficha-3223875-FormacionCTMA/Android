package com.esteban.miformacionctma.repository

import com.esteban.miformacionctma.data.evidencia.EvidenciaEstados
import com.esteban.miformacionctma.data.local.entity.EvidenciaDao
import com.esteban.miformacionctma.data.local.entity.EvidenciaEntity
import com.esteban.miformacionctma.data.remote.EvidenciasRemoto
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow

class EvidenciaRepository(
    private val evidenciaDao: EvidenciaDao,
    private val remoto: EvidenciasRemoto
) {

    suspend fun registrar(evidencia: EvidenciaEntity) =
        evidenciaDao.insertar(evidencia)

    suspend fun obtenerPorId(id: String): EvidenciaEntity? =
        evidenciaDao.obtenerPorId(id)

    fun observarPorActividad(actividadId: Int): Flow<List<EvidenciaEntity>> =
        evidenciaDao.observarPorActividad(actividadId)

    fun observarTodas(): Flow<List<EvidenciaEntity>> =
        evidenciaDao.observarTodas()

    suspend fun eliminar(evidenciaId: String) =
        evidenciaDao.eliminarPorId(evidenciaId)

    suspend fun subirEvidencia(evidenciaId: String): Result<Unit> {
        val evidencia = evidenciaDao.obtenerPorId(evidenciaId)
            ?: return Result.failure(NoSuchElementException("Evidencia inexistente"))
        evidenciaDao.actualizarEstado(evidenciaId, EvidenciaEstados.SUBIENDO)
        return try {
            remoto.subir(evidencia)
            evidenciaDao.actualizarEstado(evidenciaId, EvidenciaEstados.SINCRONIZADA)
            Result.success(Unit)
        } catch (cancelado: CancellationException) {
            evidenciaDao.actualizarEstado(evidenciaId, EvidenciaEstados.LOCAL)
            throw cancelado
        } catch (fallo: Throwable) {
            evidenciaDao.actualizarEstado(evidenciaId, EvidenciaEstados.FALLIDA)
            Result.failure(fallo)
        }
    }
}