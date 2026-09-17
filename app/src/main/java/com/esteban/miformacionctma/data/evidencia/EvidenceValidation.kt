package com.esteban.miformacionctma.data.evidencia

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns

data class EvidencePolicy(
    val maxBytes: Long = 5L * 1024 * 1024
)

data class EvidenceMetadata(
    val mimeType: String,
    val sizeBytes: Long
)

fun validarMetadatos(
    mimeType: String?,
    sizeBytes: Long,
    policy: EvidencePolicy = EvidencePolicy()
): Result<EvidenceMetadata> = runCatching {
    val tipo = requireNotNull(mimeType) { "No se pudo determinar el tipo del archivo" }
    require(tipo in setOf("image/jpeg", "image/png", "image/webp")) {
        "Tipo de imagen no permitido: $tipo"
    }
    require(sizeBytes in 1..policy.maxBytes) {
        "Tamaño fuera del límite permitido (máximo ${policy.maxBytes} bytes)"
    }
    EvidenceMetadata(tipo, sizeBytes)
}

fun ContentResolver.querySize(uri: Uri): Long =
    query(uri, arrayOf(OpenableColumns.SIZE), null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            val index = cursor.getColumnIndex(OpenableColumns.SIZE)
            if (index >= 0 && !cursor.isNull(index)) cursor.getLong(index) else -1L
        } else {
            -1L
        }
    } ?: -1L

fun validateImage(
    resolver: ContentResolver,
    uri: Uri,
    policy: EvidencePolicy = EvidencePolicy()
): Result<EvidenceMetadata> = runCatching {
    val tipo = resolver.getType(uri)
    val metadatos = validarMetadatos(tipo, resolver.querySize(uri), policy).getOrThrow()
    resolver.openInputStream(uri)?.use { stream ->
        require(stream.read() != -1) { "El archivo está vacío o no es legible" }
    } ?: error("No fue posible leer la imagen")
    metadatos
}