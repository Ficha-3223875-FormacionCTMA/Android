package com.esteban.miformacionctma.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.util.UUID

fun nuevoUriEvidencia(context: Context): Uri {
    val directorio = File(context.filesDir, "evidencias").apply { mkdirs() }
    val archivo = File(directorio, "evidencia_${UUID.randomUUID()}.jpg").apply {
        createNewFile()
    }
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        archivo
    )
}

@SuppressLint("WrongConstant")
fun persistirAccesoLectura(context: Context, uri: Uri) {
    runCatching {
        context.contentResolver.takePersistableUriPermission(
            uri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
    }
}

fun eliminarArchivoTemporalSiVacio(context: Context, uri: Uri) {
    runCatching {
        val archivo = File(uri.path.orEmpty())
        val ruta = archivo.canonicalPath
        if (ruta.startsWith(File(context.filesDir, "evidencias").canonicalPath)) {
            if (archivo.exists() && archivo.length() == 0L) {
                archivo.delete()
            }
        }
    }
}