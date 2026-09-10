package com.steven.miformacionctma.domain

fun main() {
    // Escenario: Título vacío
    println("Título vacío -> ${validarActividad(" ", 50)}")

    // Escenario: Progreso imposible
    println("Progreso 120 -> ${validarActividad("Guía 3", 120)}")

    // Escenario: Actividad vencida
    println("Progreso 80, días -1 -> ${estadoActividad(80, -1)}")

    // Escenario: Actividad completa (no debe marcar vencida aunque días sea negativo)
    println("Progreso 100, días -2 -> ${estadoActividad(100, -2)}")

    // Escenario: Lista vacía
    println("Promedio lista vacía -> ${promedioProgreso(emptyList())}")

    // Escenario: Búsqueda flexible
    val actividades = listOf(
        ActividadFormativa(1, "Kotlin básico", null, 50, 5, Prioridad.MEDIA),
        ActividadFormativa(2, "Configurar Android Studio", null, 100, 0, Prioridad.BAJA)
    )
    println("Buscar ' kotlin ' -> ${buscarPorTitulo(actividades, " kotlin ")}")
}