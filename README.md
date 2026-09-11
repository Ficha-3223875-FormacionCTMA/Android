# Mi Formación CTMA

Aplicación Android para el seguimiento de actividades de formación del programa CTMA. Permite registrar, editar, eliminar y sincronizar actividades con un backend.

## Requisitos

- Android Studio Ladybug o superior
- JDK 21
- Android SDK 36
- Min SDK 24 (Android 7.0)

## Configuracion

1. Clona el repositorio
2. Abre el proyecto en Android Studio
3. Sincroniza Gradle (Sync Project with Gradle Files)
4. Configura tu base URL de la API en `data/remote/RetrofitConfig.kt`

```kotlin
// Emulador de Android -> tu PC
const val BASE_URL = "http://10.0.2.2:8000/"

// Dispositivo fisico -> IP de tu PC
// const val BASE_URL = "http://192.168.1.100:8000/"
```

## Base de datos

El proyecto usa **dos bases de datos**:

### 1. Local (Room) - dentro de la app

- Archivo: `app/src/main/java/com/esteban/miformacionctma/data/ActividadDatabase.kt`
- Tabla: `actividades` (> `Actividad.kt`)
- Se crea automaticamente la primera vez que se ejecuta la app
- Nombre del archivo: `miformacion_ctma.db`

```kotlin
@Entity(tableName = "actividades")
data class Actividad(
    val id: Int,          // autoincrement
    val titulo: String,
    val descripcion: String,
    val fecha: String,    // "2026-09-10"
    val prioridad: String,// "Baja" | "Media" | "Alta"
    val progreso: Int     // 0..100
)
```

### 2. Remota (PostgreSQL) - backend

- Script: `database/actividades_postgres.sql`
- Backend: `backend/` (FastAPI + SQLAlchemy)

## Pasos para integrar la base de datos

### Paso 1 - Preparar PostgreSQL

```bash
# 1. Inicia PostgreSQL (Windows: Services -> postgresql-x64-18)
# 2. Ejecuta el script para crear la BD y datos de prueba
psql -U postgres -f database/actividades_postgres.sql
```

Esto crea:
- Base de datos: `miformacion_ctma`
- Tabla: `actividades`
- 5 registros de prueba

### Paso 2 - Configurar el backend

```bash
cd backend

# Crear entorno virtual
python -m venv .venv
.venv\Scripts\activate

# Instalar dependencias
pip install fastapi uvicorn sqlalchemy psycopg2-binary python-dotenv pydantic

# Configurar credenciales
copy .env.example .env
# Edita .env con tu password de PostgreSQL

# Iniciar el servidor
uvicorn app.main:app --reload --port 8000
```

Verifica que funcione en: `http://localhost:8000/docs`

### Paso 3 - Conectar la app Android

```kotlin
// RetrofitConfig.kt
// Emulador -> 10.0.2.2 (ya configurado por defecto)
```

El AndroidManifest ya tiene:
- `INTERNET` permission
- `usesCleartextTraffic="true"` (para HTTP local)

### Paso 4 - Ejecutar

1. Inicia PostgreSQL
2. Inicia el backend (`uvicorn`)
3. Compila la app en Android Studio
4. La app sincroniza automaticamente con la API al abrirse

## Endpoints de la API

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/actividades/` | Lista todas |
| GET | `/actividades/{id}` | Obtiene una |
| POST | `/actividades/` | Crea una (201) |
| PUT | `/actividades/{id}` | Actualiza una |
| DELETE | `/actividades/{id}` | Elimina una (204) |

## Estructura del proyecto

```
app/src/main/java/com/esteban/miformacionctma/
├── Actividad.kt                      # Room Entity de la base de datos
├── MainActivity.kt                   # Activity principal
├── data/
│   ├── DatError.kt                   # Errores tipados con classify()
│   ├── ActividadDao.kt               # Acceso a datos local (Room) + replaceRemoteSnapshot()
│   ├── ActividadDatabase.kt          # Configuracion de la base de datos Room
│   ├── ActividadRepository.kt        # Repositorio: combina local + remoto
│   ├── dto/
│   │   └── ActividadDTO.kt           # Modelo de transferencia de datos
│   ├── mapper/
│   │   └── ActividadMapper.kt        # Conversion Entity <-> DTO
│   └── remote/
│       ├── ActividadesApi.kt         # Interface Retrofit con @GET/@POST/@PUT/@DELETE
│       ├── BearerTokenInterceptor.kt # Inyecta token Bearer en cada request
│       ├── TokenProvider.kt          # Interface para proveer el token
│       ├── OkHttpConfig.kt           # OkHttpClient con timeouts (30s) + logging
│       ├── RetrofitConfig.kt         # Retrofit con baseUrl + GsonConverter
│       └── RemoteDatasource.kt       # Encapsula response.isSuccessful en Result<T>
└── ui/
    ├── screens/
    │   ├── ActividadesScreen.kt      # Lista de actividades con refresh + error banner
    │   ├── FormularioActividad.kt    # Formulario de alta/edicion
    │   ├── FormularioActividadUiState.kt # Estado del formulario
    │   ├── RefreshUiState.kt         # Estado de sincronizacion (Idle/Loading/Error/Success)
    │   ├── ActividadViewModel.kt     # ViewModel principal
    │   └── HomeScreen.kt             # Pantalla principal de aprendizaje
    └── theme/
        ├── Color.kt                  # Paleta de colores
        ├── Theme.kt                  # Tema Material3
        └── Type.kt                   # Tipografia
```

## Arquitectura

La app sigue una arquitectura en capas con observacion de Room en la UI:

```
┌─────────────┐   Flow<Room>   ┌───────────────────┐
│  UI (Compose)│ ─────────────▶ │  ViewModel        │
│             │ ◀───────────── │  RefreshUiState   │
└─────────────┘                └───────┬───────────┘
                                       │
                              ┌────────▼───────────┐
                              │  Repository        │
                              │  syncFromRemote()  │
                              │  withTransaction   │
                              └──┬────────────┬────┘
                                 │            │
                        ┌────────▼───┐  ┌─────▼────────┐
                        │ Remote       │  │ Local (Room) │
                        │Datasource   │  │ DAO          │
                        └────────┬───┘  └─────┬────────┘
                                 │            │
                     ┌───────────▼───┐  ┌─────▼────────────┐
                     │ Retrofit API  │  │ Actividad Database│
                     └───────────────┘  └──────────────────┘
```

### Flujo de sincronizacion

1. La pantalla observa **Room** directamente con `StateFlow<List<Actividad>>`
2. `syncFromRemote()` consulta la API remota via `RemoteDatasource`
3. Los datos remotos se clasifican con `Throwable.classify() -> DatError`
4. La escritura en la BD es transaccional: `db.withTransaction { dao.replaceRemoteSnapshot() }`
5. El estado de refresh es independiente del contenido (`RefreshUiState`)

### Manejo de errores

```kotlin
sealed interface DatError {
    NoNetwork      // Sin conexion
    Timeout        // Timeout de red
    Unauthorized   // Token invalido/expirado
    Server         // Error 5xx
    Empty          // Respuesta vacia
    Unknown(message) // Error no clasificado
}
```

## Dependencias

| Libreria | Version | Uso |
|----------|---------|-----|
| Jetpack Compose BOM | 2024.09.00 | UI |
| Room | 2.8.4 | Base de datos local |
| Retrofit | 2.11.0 | Cliente HTTP |
| OkHttp | 4.12.0 | Capa de red con interceptors |
| Gson | 2.11.0 | Serializacion JSON |
| Lifecycle | 2.6.1 | ViewModel + StateFlow |

## Documentacion

Los documentos del proyecto se encuentran en la carpeta `documentos/`:

- `Historias de Usuario.docx`
- `Matriz de Trazabilidad.docx`

Las evidencias de las actividades se encuentran en `evidencias/`.

## Directorios del repositorio

| Carpeta | Contenido |
|---------|-----------|
| `app/` | Codigo fuente Android (Compose + Room) |
| `backend/` | API FastAPI (Python) |
| `database/` | Scripts SQL para PostgreSQL |
| `documentos/` | Documentos del proyecto (.docx) |
| `evidencias/` | Evidencias de las actividades |

## Autor

**Esteban Bedoya Rojo**

Aplicacion de formacion del programa CTMA - SENA