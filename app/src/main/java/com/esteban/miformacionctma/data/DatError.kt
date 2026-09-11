package com.esteban.miformacionctma.data

import android.database.sqlite.SQLiteException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class DatError : Exception() {
    data object NoNetwork : DatError()
    data object Timeout : DatError()
    data object Unauthorized : DatError()
    data object Server : DatError()
    data object Empty : DatError()
    data class Unknown(override val message: String?) : DatError()
}

fun Throwable.classify(): DatError = when (this) {
    is UnknownHostException -> DatError.NoNetwork
    is SocketTimeoutException -> DatError.Timeout
    is IOException -> DatError.NoNetwork
    is SQLiteException -> DatError.Unknown(message)
    else -> DatError.Unknown(message)
}
