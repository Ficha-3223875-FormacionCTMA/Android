package com.esteban.miformacionctma.data.remote

import com.esteban.miformacionctma.data.local.entity.EvidenciaEntity
import kotlinx.coroutines.delay
import java.io.IOException

interface EvidenciasRemoto {
    suspend fun subir(evidencia: EvidenciaEntity)
}

class FakeEvidenciasRemoto(
    private val delayMillis: Long = 1_500,
    private val debeFallar: () -> Boolean = { false }
) : EvidenciasRemoto {

    override suspend fun subir(evidencia: EvidenciaEntity) {
        delay(delayMillis)
        if (debeFallar()) {
            throw IOException("Falló la recepción en el servidor (simulación)")
        }
    }
}