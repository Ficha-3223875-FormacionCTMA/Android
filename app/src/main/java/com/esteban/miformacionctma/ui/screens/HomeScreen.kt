package com.esteban.miformacionctma.ui.screens

<<<<<<< HEAD
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

=======
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.esteban.miformacionctma.ui.theme.AzulAcento
import com.esteban.miformacionctma.ui.theme.AzulGradiente
import com.esteban.miformacionctma.ui.theme.AzulPrimario
>>>>>>> dbc20b7 (actualizacion de proyecto)

data class InfoItem(
    val titulo: String,
    val descripcion: String
)

<<<<<<< HEAD

@Composable
fun HomeScreen() {


    var seccion by remember {
        mutableStateOf("inicio")
    }


    var seleccionado by remember {
        mutableStateOf<InfoItem?>(null)
    }


    var opcionScrum by remember {
        mutableStateOf("")
    }


    var opcionPrueba by remember {
        mutableStateOf("")
    }



    val valores = listOf(

        InfoItem(
            "Personas e interacciones",
            "Se valora la comunicación y colaboración entre personas más que depender únicamente de procesos y herramientas."
        ),

        InfoItem(
            "Software funcionando",
            "Un software funcional aporta más valor que una documentación extensa."
        ),

        InfoItem(
            "Colaboración con el cliente",
            "El cliente participa durante el desarrollo para asegurar que el producto cumpla sus necesidades."
        ),

        InfoItem(
            "Responder al cambio",
            "Los equipos ágiles aceptan cambios para mejorar el producto final."
        )

    )



    val principios = listOf(

        InfoItem(
            "Principio 1",
            "Satisfacer al cliente mediante entregas tempranas y continuas de software con valor."
        ),

        InfoItem(
            "Principio 2",
            "Aceptar cambios en los requisitos incluso en etapas avanzadas."
        ),

        InfoItem(
            "Principio 3",
            "Entregar software funcional frecuentemente."
        ),

        InfoItem(
            "Principio 4",
            "Negocio y desarrolladores trabajan juntos."
        ),

        InfoItem(
            "Principio 5",
            "Construir proyectos alrededor de personas motivadas."
        ),

        InfoItem(
            "Principio 6",
            "La comunicación directa es la más efectiva."
        ),

        InfoItem(
            "Principio 7",
            "El software funcionando es la principal medida del progreso."
        ),

        InfoItem(
            "Principio 8",
            "Promover un desarrollo sostenible."
        ),

        InfoItem(
            "Principio 9",
            "Buscar excelencia técnica continuamente."
        ),

        InfoItem(
            "Principio 10",
            "La simplicidad es fundamental."
        ),

        InfoItem(
            "Principio 11",
            "Los mejores resultados vienen de equipos organizados."
        ),

        InfoItem(
            "Principio 12",
            "El equipo debe mejorar constantemente."
        )

    )
    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(
                rememberScrollState()
            )

    ) {


        Text(
            text = "📚 Mi Formación CTMA",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        Text(
            text = "Bienvenido, Esteban 👋",
            style = MaterialTheme.typography.titleMedium
        )


        Spacer(
            modifier = Modifier.height(20.dp)
        )



        Button(
            onClick = {
                seccion = "manifiesto"
                seleccionado = null
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("¿Qué es el Manifiesto Ágil?")

        }



        Spacer(
            modifier = Modifier.height(10.dp)
        )



        Button(
            onClick = {
                seccion = "valores"
                seleccionado = null
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Valores")

        }



        Spacer(
            modifier = Modifier.height(10.dp)
        )



        Button(
            onClick = {
                seccion = "principios"
                seleccionado = null
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Principios")

        }



        Spacer(
            modifier = Modifier.height(10.dp)
        )



        Button(
            onClick = {
                seccion = "scrum"
                opcionScrum = ""
                seleccionado = null
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("🔄 Scrum")

        }



        Spacer(
            modifier = Modifier.height(10.dp)
        )



        Button(
            onClick = {
                seccion = "pruebas"
                opcionPrueba = ""
                seleccionado = null
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("🧪 Pruebas de Software")

        }



        Spacer(
            modifier = Modifier.height(25.dp)
        )



        when(seccion) {



            "inicio" -> {

                Text(
                    "Selecciona una opción para comenzar."
                )

            }




            "manifiesto" -> {

                Text(
                    "¿Qué es el Manifiesto Ágil?",
                    style = MaterialTheme.typography.titleLarge
                )


                Spacer(
                    modifier = Modifier.height(10.dp)
                )


                Text(
                    "Es un documento creado en 2001 que establece valores y principios para desarrollar software de forma flexible, colaborativa y adaptable."
                )

            }





            "valores" -> {


                Text(
                    "Los 4 Valores Ágiles",
                    style = MaterialTheme.typography.titleLarge
                )


                Spacer(
                    modifier = Modifier.height(10.dp)
                )


                valores.forEach { valor ->


                    Button(
                        onClick = {
                            seleccionado = valor
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(valor.titulo)

                    }


                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )


                }


            }





            "principios" -> {


                Text(
                    "Los 12 Principios Ágiles",
                    style = MaterialTheme.typography.titleLarge
                )


                Spacer(
                    modifier = Modifier.height(10.dp)
                )


                principios.forEach { principio ->


                    Button(
                        onClick = {
                            seleccionado = principio
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(principio.titulo)

                    }


                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                }


            }             "scrum" -> {


            Text(
                "🔄 Scrum",
                style = MaterialTheme.typography.titleLarge
            )


            Spacer(
                modifier = Modifier.height(10.dp)
            )


            Button(
                onClick = {
                    opcionScrum = "quees"
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("📘 ¿Qué es Scrum?")

            }



            Spacer(
                modifier = Modifier.height(8.dp)
            )


            Button(
                onClick = {
                    opcionScrum = "roles"
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("👥 Roles Scrum")

            }



            Spacer(
                modifier = Modifier.height(8.dp)
            )


            Button(
                onClick = {
                    opcionScrum = "artefactos"
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("📦 Artefactos Scrum")

            }



            Spacer(
                modifier = Modifier.height(8.dp)
            )


            Button(
                onClick = {
                    opcionScrum = "ceremonias"
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("🔄 Ceremonias Scrum")

            }



            Spacer(
                modifier = Modifier.height(20.dp)
            )



            when(opcionScrum){


                "quees" -> {

                    Text(
                        """
Scrum es un marco de trabajo ágil utilizado para desarrollar productos mediante equipos colaborativos.

Trabaja con ciclos cortos llamados Sprints y se basa en transparencia, inspección y adaptación.

Permite entregar valor constantemente y mejorar el proceso de desarrollo.
                            """.trimIndent()
                    )

                }



                "roles" -> {

                    Text(
                        """
👤 Product Owner

Representa al cliente.
Administra y prioriza el Product Backlog.


👨‍🏫 Scrum Master

Facilita Scrum, elimina impedimentos y ayuda al equipo.


👨‍💻 Developers

Diseñan, programan, prueban y entregan el producto.
                            """.trimIndent()
                    )

                }



                "artefactos" -> {

                    Text(
                        """
📦 Product Backlog

Lista ordenada de requisitos y funcionalidades.


📦 Sprint Backlog

Tareas seleccionadas para realizar durante el Sprint.


📦 Incremento

Versión funcional del producto creada durante el Sprint.
                            """.trimIndent()
                    )

                }



                "ceremonias" -> {

                    Text(
                        """
🔄 Sprint

Periodo donde el equipo desarrolla un incremento.


📅 Sprint Planning

Reunión donde se planifica el trabajo.


⏱ Daily Scrum

Reunión diaria de máximo 15 minutos.


👀 Sprint Review

Presentación del trabajo realizado.


🔍 Sprint Retrospective

Reunión para encontrar mejoras.
                            """.trimIndent()
                    )

                }

            }


        }





            "pruebas" -> {


                Text(
                    "🧪 Pruebas de Software",
                    style = MaterialTheme.typography.titleLarge
                )


                Spacer(
                    modifier = Modifier.height(10.dp)
                )



                Button(
                    onClick = {
                        opcionPrueba = "unitarias"
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text("✅ Pruebas Unitarias")

                }



                Spacer(
                    modifier = Modifier.height(8.dp)
                )



                Button(
                    onClick = {
                        opcionPrueba = "integracion"
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text("🔗 Pruebas de Integración")

                }



                Spacer(
                    modifier = Modifier.height(8.dp)
                )



                Button(
                    onClick = {
                        opcionPrueba = "funcionales"
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text("🖥️ Pruebas Funcionales")

                }



                Spacer(
                    modifier = Modifier.height(20.dp)
                )



                when(opcionPrueba){


                    "unitarias" -> {

                        Text(
                            """
✅ Pruebas Unitarias

Evalúan pequeñas partes del código como funciones, métodos o clases.

Permiten verificar que cada componente funciona correctamente de manera independiente.

Ejemplo:
Comprobar que una función de cálculo entregue el resultado esperado.
                            """.trimIndent()
                        )

                    }



                    "integracion" -> {

                        Text(
                            """
🔗 Pruebas de Integración

Verifican que diferentes módulos del sistema trabajen correctamente juntos.

Ejemplo:
Comprobar que un formulario envíe información y la guarde correctamente en la base de datos.
                            """.trimIndent()
                        )

                    }



                    "funcionales" -> {

                        Text(
                            """
🖥️ Pruebas Funcionales

Comprueban que el sistema cumpla las funciones solicitadas por el usuario.

Ejemplo:
Verificar que un usuario pueda iniciar sesión y utilizar las opciones principales.
                            """.trimIndent()
                        )

                    }


                }


            }


        }



        seleccionado?.let { item ->


            Spacer(
                modifier = Modifier.height(25.dp)
            )


            Text(
                text = item.titulo,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            Text(
                text = item.descripcion
            )


        }



    }


}
=======
private data class SeccionMenu(
    val id: String,
    val titulo: String,
    val subtitulo: String,
    val icono: ImageVector
)

private val menuSecciones = listOf(
    SeccionMenu("manifiesto", "Manifiesto Ágil", "El documento que dio origen a Agile", Icons.Filled.Book),
    SeccionMenu("valores", "4 Valores", "Los principios fundamentales", Icons.Filled.WorkspacePremium),
    SeccionMenu("principios", "12 Principios", "Las reglas para aplicar Agile", Icons.Filled.Psychology),
    SeccionMenu("scrum", "Scrum", "El marco de trabajo ágil", Icons.Filled.Recycling),
    SeccionMenu("pruebas", "Pruebas de Software", "Calidad y verificación", Icons.Filled.BugReport)
)

private val valores = listOf(
    InfoItem(
        "Personas e interacciones",
        "Se valora la comunicación y colaboración entre personas más que depender únicamente de procesos y herramientas."
    ),
    InfoItem(
        "Software funcionando",
        "Un software funcional aporta más valor que una documentación extensa."
    ),
    InfoItem(
        "Colaboración con el cliente",
        "El cliente participa durante el desarrollo para asegurar que el producto cumpla sus necesidades."
    ),
    InfoItem(
        "Responder al cambio",
        "Los equipos ágiles aceptan cambios para mejorar el producto final."
    )
)

private val principios = listOf(
    InfoItem("Principio 1", "Satisfacer al cliente mediante entregas tempranas y continuas de software con valor."),
    InfoItem("Principio 2", "Aceptar cambios en los requisitos incluso en etapas avanzadas."),
    InfoItem("Principio 3", "Entregar software funcional frecuentemente."),
    InfoItem("Principio 4", "Negocio y desarrolladores trabajan juntos."),
    InfoItem("Principio 5", "Construir proyectos alrededor de personas motivadas."),
    InfoItem("Principio 6", "La comunicación directa es la más efectiva."),
    InfoItem("Principio 7", "El software funcionando es la principal medida del progreso."),
    InfoItem("Principio 8", "Promover un desarrollo sostenible."),
    InfoItem("Principio 9", "Buscar excelencia técnica continuamente."),
    InfoItem("Principio 10", "La simplicidad es fundamental."),
    InfoItem("Principio 11", "Los mejores resultados vienen de equipos organizados."),
    InfoItem("Principio 12", "El equipo debe mejorar constantemente.")
)

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var seccion by remember { mutableStateOf("inicio") }
    var seleccionado by remember { mutableStateOf<InfoItem?>(null) }
    var opcionScrum by remember { mutableStateOf("") }
    var opcionPrueba by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HeaderBienvenida()

        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Explora los temas",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 20.dp, bottom = 12.dp)
            )

            when (seccion) {
                "inicio" -> MenuPrincipal(
                    onSeleccionar = { nueva ->
                        seccion = nueva
                        seleccionado = null
                        opcionScrum = ""
                        opcionPrueba = ""
                    }
                )
                "manifiesto" -> ContenidoManifiesto()
                "valores" -> ListaSeleccionable(
                    titulo = "Los 4 Valores Ágiles",
                    contador = "${valores.size} conceptos",
                    items = valores,
                    seleccionado = seleccionado,
                    onSeleccionar = { seleccionado = it }
                )
                "principios" -> ListaSeleccionable(
                    titulo = "Los 12 Principios Ágiles",
                    contador = "${principios.size} conceptos",
                    items = principios,
                    seleccionado = seleccionado,
                    onSeleccionar = { seleccionado = it }
                )
                "scrum" -> ContenidoScrum(
                    opcion = opcionScrum,
                    onOpcion = { opcionScrum = it }
                )
                "pruebas" -> ContenidoPruebas(
                    opcion = opcionPrueba,
                    onOpcion = { opcionPrueba = it }
                )
            }

            seleccionado?.let { item ->
                Spacer(Modifier.height(16.dp))
                CardDetalle(item)
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun HeaderBienvenida() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(AzulPrimario, AzulGradiente, AzulAcento)
                )
            )
            .padding(horizontal = 24.dp, vertical = 28.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White.copy(alpha = 0.22f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(Modifier.width(14.dp))
                Column {
                    Text(
                        text = "Bienvenido, Esteban 👋",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Text(
                        text = "Mi Formación CTMA",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Aprende sobre metodologías ágiles, Scrum y pruebas de software, y registra tus avances.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}

@Composable
private fun MenuPrincipal(onSeleccionar: (String) -> Unit) {
    Column {
        menuSecciones.forEach { seccion ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .clickable { onSeleccionar(seccion.id) },
                shape = RoundedCornerShape(18.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            seccion.icono,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = seccion.titulo,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = seccion.subtitulo,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Icon(
                        Icons.Filled.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun ContenidoManifiesto() {
    Text(
        text = "Manifiesto Ágil",
        style = MaterialTheme.typography.titleLarge
    )
    Spacer(Modifier.height(4.dp))
    Text(
        text = "Publicado en 2001",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(Modifier.height(12.dp))
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Text(
            text = "Es un documento creado en 2001 que establece valores y principios para desarrollar software de forma flexible, colaborativa y adaptable.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(20.dp)
        )
    }
    Spacer(Modifier.height(12.dp))
    Text(
        text = "Agrupa 4 valores y 12 principios que priorizan la entrega de valor, la colaboración y la respuesta al cambio por encima de los procesos rígidos.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun ListaSeleccionable(
    titulo: String,
    contador: String,
    items: List<InfoItem>,
    seleccionado: InfoItem?,
    onSeleccionar: (InfoItem) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )
        AssistChip(
            onClick = {},
            label = { Text(contador) }
        )
    }
    Spacer(Modifier.height(12.dp))

    items.forEach { item ->
        val estaSeleccionado = seleccionado == item
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .clickable { onSeleccionar(item) },
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (estaSeleccionado) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surface
                }
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.titulo,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun CardDetalle(item: InfoItem) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = item.titulo,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = item.descripcion,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun ContenidoScrum(
    opcion: String,
    onOpcion: (String) -> Unit
) {
    val opciones = listOf(
        Triple("quees", "📘 ¿Qué es Scrum?", "Marco de trabajo ágil en ciclos cortos"),
        Triple("roles", "👥 Roles Scrum", "Product Owner, Scrum Master y Developers"),
        Triple("artefactos", "📦 Artefactos Scrum", "Backlog, Sprint Backlog e Incremento"),
        Triple("ceremonias", "🔄 Ceremonias Scrum", "Reuniones que organizan el trabajo")
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Scrum",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )
        AssistChip(
            onClick = {},
            label = { Text("Marco ágil") }
        )
    }
    Spacer(Modifier.height(12.dp))

    opciones.forEach { (id, titulo, subtitulo) ->
        val seleccionada = opcion == id
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .clickable { onOpcion(id) },
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (seleccionada) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surface
                }
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitulo,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    when (opcion) {
        "quees" -> TextoInfo(
            "📘 ¿Qué es Scrum?",
            """
                Scrum es un marco de trabajo ágil utilizado para desarrollar productos mediante equipos colaborativos.
                
                Trabaja con ciclos cortos llamados Sprints y se basa en transparencia, inspección y adaptación.
                
                Permite entregar valor constantemente y mejorar el proceso de desarrollo.
            """.trimIndent()
        )
        "roles" -> TextoInfo(
            "👥 Roles Scrum",
            """
                👤 Product Owner
                Representa al cliente. Administra y prioriza el Product Backlog.
                
                👨🏫 Scrum Master
                Facilita Scrum, elimina impedimentos y ayuda al equipo.
                
                👨‍💻 Developers
                Diseñan, programan, prueban y entregan el producto.
            """.trimIndent()
        )
        "artefactos" -> TextoInfo(
            "📦 Artefactos Scrum",
            """
                📦 Product Backlog
                Lista ordenada de requisitos y funcionalidades.
                
                📦 Sprint Backlog
                Tareas seleccionadas para realizar durante el Sprint.
                
                📦 Incremento
                Versión funcional del producto creada durante el Sprint.
            """.trimIndent()
        )
        "ceremonias" -> TextoInfo(
            "🔄 Ceremonias Scrum",
            """
                🔄 Sprint
                Periodo donde el equipo desarrolla un incremento.
                
                📅 Sprint Planning
                Reunión donde se planifica el trabajo.
                
                ⏱ Daily Scrum
                Reunión diaria de máximo 15 minutos.
                
                👀 Sprint Review
                Presentación del trabajo realizado.
                
                🔍 Sprint Retrospective
                Reunión para encontrar mejoras.
            """.trimIndent()
        )
    }
}

@Composable
private fun ContenidoPruebas(
    opcion: String,
    onOpcion: (String) -> Unit
) {
    val opciones = listOf(
        Triple("unitarias", "✅ Pruebas Unitarias", "Verifican componentes individuales"),
        Triple("integracion", "🔗 Pruebas de Integración", "Verifican módulos trabajando juntos"),
        Triple("funcionales", "🖥️ Pruebas Funcionales", "Verifican funciones solicitadas por el usuario")
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Pruebas de Software",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )
        AssistChip(
            onClick = {},
            label = { Text("Calidad") }
        )
    }
    Spacer(Modifier.height(12.dp))

    opciones.forEach { (id, titulo, subtitulo) ->
        val seleccionada = opcion == id
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .clickable { onOpcion(id) },
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (seleccionada) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surface
                }
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitulo,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    when (opcion) {
        "unitarias" -> TextoInfo(
            "✅ Pruebas Unitarias",
            """
                Evalúan pequeñas partes del código como funciones, métodos o clases.
                
                Permiten verificar que cada componente funciona correctamente de manera independiente.
                
                Ejemplo:
                Comprobar que una función de cálculo entregue el resultado esperado.
            """.trimIndent()
        )
        "integracion" -> TextoInfo(
            "🔗 Pruebas de Integración",
            """
                Verifican que diferentes módulos del sistema trabajen correctamente juntos.
                
                Ejemplo:
                Comprobar que un formulario envíe información y la guarde correctamente en la base de datos.
            """.trimIndent()
        )
        "funcionales" -> TextoInfo(
            "🖥️ Pruebas Funcionales",
            """
                Comprueban que el sistema cumpla las funciones solicitadas por el usuario.
                
                Ejemplo:
                Verificar que un usuario pueda iniciar sesión y utilizar las opciones principales.
            """.trimIndent()
        )
    }
}

@Composable
private fun TextoInfo(titulo: String, contenido: String) {
    Spacer(Modifier.height(8.dp))
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = contenido,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}
>>>>>>> dbc20b7 (actualizacion de proyecto)
