package com.esteban.miformacionctma.ui.screens

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esteban.miformacionctma.data.evidencia.EvidenceMetadata
import com.esteban.miformacionctma.data.evidencia.EvidenciaEstados
import com.esteban.miformacionctma.data.local.entity.EvidenciaEntity
import com.esteban.miformacionctma.repository.EvidenciaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID

class EvidenciaViewModel(
    private val repository: EvidenciaRepository,
    private val validarUri: (Uri) -> Result<EvidenceMetadata>
) : ViewModel() {

    var uiState by mutableStateOf(EvidenciaUiState())
        private set

    var actividadSeleccionada by mutableStateOf<Int?>(null)
        private set

    fun seleccionarActividad(actividadId: Int) {
        actividadSeleccionada = actividadId
        uiState = EvidenciaUiState(mensaje = null)
    }

    fun observarEvidencias(): Flow<List<EvidenciaEntity>> =
        actividadSeleccionada?.let(repository::observarPorActividad)
            ?: kotlinx.coroutines.flow.flowOf(emptyList())

    fun alSeleccionar(uri: Uri) {
        validarUri(uri)
            .onSuccess { metadatos ->
                uiState = EvidenciaUiState(uri = uri, metadatos = metadatos, mensaje = null)
            }
            .onFailure { error ->
                uiState = EvidenciaUiState(
                    mensaje = "Evidencia rechazada: ${error.message ?: "no válida"}"
                )
            }
    }

    fun alCancelarCaptura() {
        uiState = uiState.copy(
            uri = null,
            metadatos = null,
            mensaje = "Captura cancelada. El archivo temporal vacío se eliminó."
        )
    }

    fun registrar(actividadId: Int, onGuardado: () -> Unit = {}) {
        val uri = uiState.uri
        val metadatos = uiState.metadatos
        if (uri == null || metadatos == null) {
            uiState = uiState.copy(mensaje = "Selecciona o captura una imagen válida primero.")
            return
        }

        viewModelScope.launch {
            repository.registrar(
                EvidenciaEntity(
                    id = UUID.randomUUID().toString(),
                    actividadId = actividadId,
                    localUri = uri.toString(),
                    mimeType = metadatos.mimeType,
                    sizeBytes = metadatos.sizeBytes,
                    estado = EvidenciaEstados.LOCAL,
                    creadaEnEpochMillis = System.currentTimeMillis()
                )
            )
            uiState = EvidenciaUiState(mensaje = "Evidencia guardada en el dispositivo (LOCAL).")
            onGuardado()
        }
    }

    fun subir(evidenciaId: String) {
        viewModelScope.launch {
            uiState = uiState.copy(mensaje = "Subiendo evidencia…")
            repository.subirEvidencia(evidenciaId)
                .onSuccess {
                    uiState = uiState.copy(mensaje = "Evidencia sincronizada (SINCRONIZADA).")
                }
                .onFailure { error ->
                    uiState = uiState.copy(
                        mensaje = "Falla al subir: ${error.message ?: "error desconocido"}. Estado FALLIDA."
                    )
                }
        }
    }

    fun eliminar(evidenciaId: String) {
        viewModelScope.launch {
            repository.eliminar(evidenciaId)
            uiState = uiState.copy(mensaje = "Evidencia eliminada.")
        }
    }
}