package com.steven.miformacionctma.domain

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

private val FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd")

fun validarTitulo(titulo: String): String? = when {
    titulo.isBlank() -> "El título es obligatorio"
    titulo.length < 3 -> "El título debe tener al menos 3 caracteres"
    titulo.length > 80 -> "El título no puede superar 80 caracteres"
    else -> null
}

fun validarDescripcion(descripcion: String): String? = when {
    descripcion.length > 240 -> "La descripción no puede superar 240 caracteres"
    else -> null
}

fun validarFecha(fecha: String, hoy: LocalDate = LocalDate.now()): String? {
    if (fecha.isBlank()) return "La fecha es obligatoria"
    val fechaParseada = try {
        LocalDate.parse(fecha, FORMATO_FECHA)
    } catch (e: DateTimeParseException) {
        return "Formato de fecha inválido (usa AAAA-MM-DD)"
    }
    return if (fechaParseada.isBefore(hoy)) "La fecha no puede ser anterior a hoy" else null
}

fun validarProgreso(progreso: String): String? {
    val numero = progreso.toIntOrNull() ?: return "El progreso debe ser un número"
    return if (numero !in 0..100) "El progreso debe estar entre 0 y 100" else null
}