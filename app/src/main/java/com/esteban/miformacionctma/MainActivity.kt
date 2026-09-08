package com.esteban.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.esteban.miformacionctma.ui.screens.HomeScreen
import com.esteban.miformacionctma.ui.theme.MiFormacionCTMATheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 🔌 AQUÍ se hace la conexión con la base de datos
        val app = application as MiFormacionApplication
        val repository = app.container.actividadRepository

        setContent {
            MiFormacionCTMATheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { padding ->
                    HomeScreen(
                        repository = repository,
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}