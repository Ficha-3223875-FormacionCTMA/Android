package com.esteban.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
<<<<<<< HEAD
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
=======
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import com.esteban.miformacionctma.ui.screens.ActividadesScreen
import com.esteban.miformacionctma.ui.screens.ActividadViewModel
>>>>>>> dbc20b7 (actualizacion de proyecto)
import com.esteban.miformacionctma.ui.screens.HomeScreen
import com.esteban.miformacionctma.ui.theme.MiFormacionCTMATheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MiFormacionCTMATheme {
<<<<<<< HEAD
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    HomeScreen()
=======
                var tabSeleccionado by rememberSaveable { mutableIntStateOf(0) }

                val actividadViewModel: ActividadViewModel = viewModel(factory = ActividadViewModel.Factory)

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = tabSeleccionado == 0,
                                onClick = { tabSeleccionado = 0 },
                                icon = { Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null) },
                                label = { Text("Aprender") }
                            )
                            NavigationBarItem(
                                selected = tabSeleccionado == 1,
                                onClick = { tabSeleccionado = 1 },
                                icon = { Icon(Icons.Filled.Task, contentDescription = null) },
                                label = { Text("Actividades") }
                            )
                        }
                    }
                ) { innerPadding ->
                    when (tabSeleccionado) {
                        0 -> HomeScreen(Modifier.padding(innerPadding))
                        1 -> ActividadesScreen(
                            viewModel = actividadViewModel,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
>>>>>>> dbc20b7 (actualizacion de proyecto)
                }
            }
        }
    }
}