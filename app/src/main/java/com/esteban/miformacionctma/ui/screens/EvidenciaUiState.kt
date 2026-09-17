package com.esteban.miformacionctma.ui.screens

import android.net.Uri
import com.esteban.miformacionctma.data.evidencia.EvidenceMetadata

data class EvidenciaUiState(
    val uri: Uri? = null,
    val metadatos: EvidenceMetadata? = null,
    val mensaje: String? = null
)