# 📱 Módulo de Gestión de Reportes · Evaluación Android Semana 5

Módulo interactivo para la creación y validación de reportes desarrollado con Jetpack Compose y arquitectura MVVM (Ficha 3223875).

## 🛠️ Tecnologías
* Kotlin & Jetpack Compose (Material 3)
* MVVM & Unidirectional Data Flow (UDF)
* StateFlow & collectAsStateWithLifecycle

## 🏗️ Estructura del Módulo
* CrearReporteScreen.kt: Componente UI (Route Stateless)
* CrearReporteViewModel.kt: Manejo de lógica y estado
* ReporteRepository.kt: Interfaz de abstracción de datos
* CrearUiState.kt: Estado inmutable de la pantalla

## ❓ Justificación Arquitectónica (Parte C)
El ViewModel depende de la interfaz ReporteRepository aplicando el Principio de Inversión de Dependencias (SOLID), lo que permite desacoplar la capa de presentación de la fuente de datos y facilitar pruebas unitarias.

## 👤 Autor
* Héctor Steven Cuesta Benítez - Ficha 3223875
