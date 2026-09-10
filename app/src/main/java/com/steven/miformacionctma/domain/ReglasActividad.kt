package com.steven.miformacionctma.domain

fun validarActividad(titulo: String, progreso: Int): List<String> {
    val errores = mutableListOf<String>()
    if (titulo.isBlank()) errores.add("El título es obligatorio")
    if (progreso !in 0..100) errores.add("El progreso debe estar entre 0 y 100")
    return errores
}

fun estadoActividad(progreso: Int, diasRestantes: Int): String = when {
    progreso == 100 -> "Completada"
    diasRestantes < 0 -> "Vencida"
    progreso > 0 -> "En proceso"
    else -> "Pendiente"
}

fun actividadesUrgentes(actividades: List<ActividadFormativa>): List<ActividadFormativa> =
    actividades.filter { it.progreso < 100 && it.diasRestantes in 0..2 }

fun promedioProgreso(actividades: List<ActividadFormativa>): Double {
    if (actividades.isEmpty()) return 0.0
    return actividades.map { it.progreso }.average()
}

fun buscarPorTitulo(actividades: List<ActividadFormativa>, texto: String): List<ActividadFormativa> {
    val textoNormalizado = texto.trim().lowercase()
    return actividades.filter { it.titulo.lowercase().contains(textoNormalizado) }
}

fun ordenarActividades(actividades: List<ActividadFormativa>): List<ActividadFormativa> =
    actividades.sortedWith(
        compareBy(
            { it.diasRestantes >= 0 },       // false (vencidas) va primero
            { -it.prioridad.ordinal },        // ALTA (ordinal más alto) primero
            { it.diasRestantes }              // menor número de días primero
        )
    )