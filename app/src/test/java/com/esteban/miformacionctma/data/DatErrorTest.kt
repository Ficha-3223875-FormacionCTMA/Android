package com.esteban.miformacionctma.data

import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import org.junit.Assert.assertEquals
import org.junit.Test

class DatErrorTest {

    @Test
    fun `unknown host se clasifica como NoNetwork`() {
        assertEquals(DatError.NoNetwork, UnknownHostException().classify())
    }

    @Test
    fun `socket timeout se clasifica como Timeout`() {
        assertEquals(DatError.Timeout, SocketTimeoutException().classify())
    }

    @Test
    fun `io exception se clasifica como NoNetwork`() {
        assertEquals(DatError.NoNetwork, IOException("sin red").classify())
    }

    @Test
    fun `excepcion generica se clasifica como Unknown con mensaje`() {
        assertEquals(DatError.Unknown("boom"), IllegalStateException("boom").classify())
    }

    @Test
    fun `unknown mantiene el mensaje original`() {
        assertEquals("detalle", DatError.Unknown("detalle").message)
    }
}