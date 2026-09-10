package com.steven.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.steven.miformacionctma.domain.ActividadFormativa
import com.steven.miformacionctma.domain.Prioridad
import com.steven.miformacionctma.domain.actividadesUrgentes
import com.steven.miformacionctma.domain.promedioProgreso
import com.steven.miformacionctma.ui.theme.MiFormacionCTMATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actividadesEjemplo = listOf(
            ActividadFormativa(1, "Configurar Android Studio", null, 100, -3, Prioridad.BAJA),
            ActividadFormativa(2, "Laboratorio Compose", null, 65, 2, Prioridad.ALTA),
            ActividadFormativa(3, "Documentar alcance inicial", null, 30, 5, Prioridad.MEDIA)
        )
        val resumen = "Promedio de avance: %.0f%% · Urgentes: %d".format(
            promedioProgreso(actividadesEjemplo),
            actividadesUrgentes(actividadesEjemplo).size
        )

        setContent {
            MiFormacionCTMATheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaInicio(resumen = resumen)
                }
            }
        }
    }
}

@Composable
fun PantallaInicio(nombre: String = "Aprendiz", resumen: String = "") {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Mi Formación CTMA",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Hola, $nombre 👋",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Aquí organizarás actividades y evidencias de tu proceso formativo.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (resumen.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = resumen,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "PRÓXIMO COMPROMISO",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Laboratorio Compose",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Fecha límite: 15 sept. 2026",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaInicioPreview() {
    MiFormacionCTMATheme {
        PantallaInicio(resumen = "Promedio de avance: 65% · Urgentes: 1")
    }
}