package com.esteban.miformacionctma.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.esteban.miformacionctma.data.dto.CatImageDTO
import com.esteban.miformacionctma.data.remote.CatApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

data class CameraUiState(
    val gatos: List<CatImageDTO> = emptyList(),
    val cargando: Boolean = false,
    val error: String? = null,
    val fotoUri: String? = null
)

class CameraViewModel(private val api: CatApi) : ViewModel() {

    private val _uiState = MutableStateFlow(CameraUiState())
    val uiState: StateFlow<CameraUiState> = _uiState.asStateFlow()

    fun cargarGatos() {
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true, error = null) }
            try {
                val response = api.getRandomCats(9)
                if (response.isSuccessful) {
                    val gatos = response.body().orEmpty()
                    _uiState.update { it.copy(gatos = gatos, cargando = false) }
                } else {
                    _uiState.update { it.copy(cargando = false, error = "Error del servidor") }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(cargando = false, error = "Sin conexion: ${e.message}")
                }
            }
        }
    }

    fun fotoTomada(uri: String) {
        _uiState.update { it.copy(fotoUri = uri) }
    }

    fun limpiarFoto() {
        _uiState.update { it.copy(fotoUri = null) }
    }

    fun limpiarError() {
        _uiState.update { it.copy(error = null) }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val api = Retrofit.Builder()
                    .baseUrl("https://api.thecatapi.com/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CatApi::class.java)
                CameraViewModel(api)
            }
        }
    }
}