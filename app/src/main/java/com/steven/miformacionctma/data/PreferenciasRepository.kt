package com.steven.miformacionctma.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.steven.miformacionctma.domain.PreferenciasRepositoryContrato
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "preferencias_usuario")

class PreferenciasRepository(
    private val context: Context
) : PreferenciasRepositoryContrato {

    private val CLAVE_ORDEN = stringPreferencesKey("orden_actividades")
    private val CLAVE_MODO_GRID = booleanPreferencesKey("modo_grid_preferido")

    override val ordenPreferido: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[CLAVE_ORDEN] ?: "id_asc"
    }

    override val modoGridPreferido: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[CLAVE_MODO_GRID] ?: false
    }

    override suspend fun guardarOrden(orden: String) {
        context.dataStore.edit { prefs ->
            prefs[CLAVE_ORDEN] = orden
        }
    }

    override suspend fun guardarModoGrid(activo: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[CLAVE_MODO_GRID] = activo
        }
    }
}