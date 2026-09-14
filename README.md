# Mi Formación CTMA

App Android para que aprendices del SENA organicen sus actividades formativas: consultar compromisos, registrar avance y ver criterios de evaluación.

## Arquitectura

- **UI**: Jetpack Compose + Material 3, con `ViewModel` y flujo unidireccional (`StateFlow`).
- **Persistencia**:
  - **Room**: datos estructurados y relacionales (`ActividadEntity`, `CategoriaEntity`), con esquema versionado y migraciones controladas.
  - **DataStore**: preferencias simples del usuario (modo de vista lista/grid).
  - **Estado efímero** (`remember`/`rememberSaveable`): datos de UI que no necesitan sobrevivir más allá de la sesión actual (borradores de formulario, menús abiertos).

Ver `docs/inventario-datos.md` para el detalle de qué vive dónde y por qué.

## Estructura de paquetes

com.steven.miformacionctma/
├── data/ → Entidades Room, DAO, migraciones, repositorios, DataStore
├── domain/ → Modelos y reglas de negocio puras (sin Android)
├── ui/ → ViewModel, pantallas y componentes Compose


## Decisiones de diseño relevantes

- El `Repository` se definió como interfaz desde la Semana 5, lo que permitió cambiar de una implementación en memoria a una respaldada en Room (Semana 6) sin tocar el `ViewModel` ni las pantallas.
- Se agregó una entidad `CategoriaEntity` relacionada con `ActividadEntity` mediante llave foránea, para cumplir con el requisito de modelar una relación entre entidades.
- La migración v1→v2 agrega la columna `completada` sin borrar datos existentes, validada con una prueba instrumentada que simula el esquema anterior con SQL directo.

## Pruebas

- **Unitarias** (`test/`): reglas de negocio y validaciones del formulario.
- **Instrumentadas** (`androidTest/`): operaciones del DAO (CRUD, búsqueda, relación con categoría) y la migración v1→v2.

## Estado del proyecto

Semanas 1 a 6 completadas: pantalla base, núcleo de dominio en Kotlin, UI con Compose (lista/detalle/formulario adaptable), navegación, estado con ViewModel y persistencia con Room + DataStore.


## Semana 7: Corrutinas y Flow

- `ListadoUiState` (Cargando/Contenido/Vacío/Error) y `OperacionUiState` (Inactiva/EnCurso/Exitosa/Fallida) como estados sellados, exhaustivos en cada `when`.
- `flatMapLatest` sobre el texto de búsqueda para cancelar automáticamente consultas obsoletas.
- `combine` entre el flujo de actividades y la preferencia de orden guardada en DataStore.
- Todas las excepciones no relacionadas con cancelación se capturan y transforman en `ListadoUiState.Error` o `OperacionUiState.Fallida`; las `CancellationException` siempre se relanzan.
- Pruebas del `ViewModel` con `StandardTestDispatcher`, `runTest`, `backgroundScope` y tiempo virtual (sin `Thread.sleep`).
