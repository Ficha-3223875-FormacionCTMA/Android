package com.esteban.miformacionctma.ui.screens

data class Valor(
    val titulo: String,
    val descripcion: String
)

data class Principio(
    val titulo: String,
    val descripcion: String
)

val valores = listOf(

    Valor(
        "Personas e interacciones",
        "Lo más importante es que las personas trabajen en equipo y se comuniquen bien. Las herramientas ayudan, pero las personas hacen la diferencia."
    ),

    Valor(
        "Software funcionando",
        "Es mejor entregar un programa que funcione correctamente que tener mucha documentación sin resultados."
    ),

    Valor(
        "Colaboración con el cliente",
        "El cliente debe participar durante el desarrollo para asegurar que el producto cumpla con sus necesidades."
    ),

    Valor(
        "Responder al cambio",
        "Los cambios son normales en un proyecto y es importante adaptarse para mejorar el resultado final."
    )

)

val principios = listOf(

    Principio("Satisfacer al cliente", "Entregar un producto útil que realmente resuelva las necesidades del cliente."),

    Principio("Aceptar cambios", "Los cambios pueden mejorar el proyecto y deben ser aprovechados."),

    Principio("Entregar frecuentemente", "Es mejor entregar avances pequeños y constantes que esperar hasta el final."),

    Principio("Trabajar con el cliente", "El cliente y el equipo deben mantenerse comunicados durante todo el proyecto."),

    Principio("Personas motivadas", "Un equipo motivado trabaja mejor y consigue mejores resultados."),

    Principio("Comunicación directa", "Hablar directamente evita errores y facilita el trabajo."),

    Principio("Software funcionando", "El progreso del proyecto se demuestra cuando la aplicación funciona correctamente."),

    Principio("Desarrollo sostenible", "El equipo debe mantener un ritmo constante para garantizar calidad."),

    Principio("Excelencia técnica", "Un código limpio y organizado facilita el mantenimiento del proyecto."),

    Principio("Simplicidad", "Es mejor hacer solo lo necesario y evitar complicar el desarrollo."),

    Principio("Equipos autoorganizados", "Cada integrante puede aportar ideas y tomar decisiones para mejorar el trabajo."),

    Principio("Mejora continua", "Después de cada etapa el equipo debe analizar qué puede mejorar.")
)
