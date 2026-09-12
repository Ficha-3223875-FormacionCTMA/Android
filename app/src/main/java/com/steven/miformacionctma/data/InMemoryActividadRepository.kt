package com.steven.miformacionctma.data

import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.ActividadRepository
import com.steven.miformacionctma.domain.Prioridad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryActividadRepository : ActividadRepository {

    private val _actividades = MutableStateFlow(datosIniciales())
    override val actividades: StateFlow<List<ActividadFormativa>> = _actividades.asStateFlow()

    override fun agregar(actividad: ActividadFormativa) {
        _actividades.value = _actividades.value + actividad
    }

    override fun buscarPorId(id: Long): ActividadFormativa? =
        _actividades.value.find { it.id == id }

    private fun datosIniciales(): List<ActividadFormativa> = listOf(
        ActividadFormativa(1, "Configurar Android Studio", null, 100, -5, Prioridad.BAJA),
        ActividadFormativa(2, "Laboratorio Compose semana 1", null, 100, -3, Prioridad.BAJA),
        ActividadFormativa(3, "Núcleo Kotlin de dominio", null, 100, -1, Prioridad.MEDIA),
        ActividadFormativa(4, "Pantalla de actividades Compose", null, 80, 1, Prioridad.ALTA)
    )
}