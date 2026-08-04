package com.esteban.miformacionctma.ui.screens

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


data class InfoItem(
    val titulo: String,
    val descripcion: String
)


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
