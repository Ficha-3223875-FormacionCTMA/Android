package com.esteban.miformacionctma

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

enum class EstadoEvidencia(val texto: String) {
    LOCAL("Local"),
    SUBIENDO("Subiendo"),
    SINCRONIZADA("Sincronizada"),
    FALLIDA("Fallida")
}

@Entity(
    tableName = "evidencias",
    foreignKeys = [
        ForeignKey(
            entity = Actividad::class,
            parentColumns = ["id"],
            childColumns = ["actividadId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["actividadId"])]
)
data class Evidencia(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val actividadId: Int,
    val uri: String,
    val tipoMime: String,
    val tamanoBytes: Long,
    val estado: EstadoEvidencia
)