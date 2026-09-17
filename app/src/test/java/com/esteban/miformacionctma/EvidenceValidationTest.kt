package com.esteban.miformacionctma

import com.esteban.miformacionctma.data.evidencia.EvidencePolicy
import com.esteban.miformacionctma.data.evidencia.validarMetadatos
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class EvidenceValidationTest {

    @Test
    fun aceptaImagenPngDentroDelLimite() {
        val resultado = validarMetadatos(
            mimeType = "image/png",
            sizeBytes = 1_024,
            policy = EvidencePolicy()
        )
        assertTrue(resultado.isSuccess)
        assertEquals("image/png", resultado.getOrNull()?.mimeType)
    }

    @Test
    fun rechazaMimeDesconocido() {
        val resultado = validarMetadatos(
            mimeType = "image/gif",
            sizeBytes = 1_024,
            policy = EvidencePolicy()
        )
        assertTrue(resultado.isFailure)
    }

    @Test
    fun rechazaMimeNulo() {
        val resultado = validarMetadatos(
            mimeType = null,
            sizeBytes = 1_024,
            policy = EvidencePolicy()
        )
        assertTrue(resultado.isFailure)
    }

    @Test
    fun rechazaArchivoVacio() {
        val resultado = validarMetadatos(
            mimeType = "image/jpeg",
            sizeBytes = 0,
            policy = EvidencePolicy()
        )
        assertTrue(resultado.isFailure)
    }

    @Test
    fun rechazaArchivoSobreElLimite() {
        val resultado = validarMetadatos(
            mimeType = "image/webp",
            sizeBytes = EvidencePolicy().maxBytes + 1,
            policy = EvidencePolicy()
        )
        assertTrue(resultado.isFailure)
    }

    @Test
    fun aceptaTamanioExactamenteEnElLimite() {
        val resultado = validarMetadatos(
            mimeType = "image/jpeg",
            sizeBytes = EvidencePolicy().maxBytes,
            policy = EvidencePolicy()
        )
        assertTrue(resultado.isSuccess)
    }
}