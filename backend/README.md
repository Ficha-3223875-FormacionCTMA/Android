# ============================================================
# Mi Formacion CTMA - Backend FastAPI
# ============================================================

## Estructura

```
backend/
├── app/
│   ├── __init__.py
│   ├── main.py                  # Punto de entrada FastAPI
│   ├── database.py              # Conexion SQLAlchemy a PostgreSQL
│   ├── schemas.py               # Schemas Pydantic (Create/Update/Response)
│   ├── crud.py                  # Operaciones CRUD
│   ├── models/
│   │   ├── __init__.py
│   │   └── actividad.py         # Modelo SQLAlchemy
│   └── routers/
│       ├── __init__.py
│       └── actividades.py       # Endpoints /actividades
└── .env.example                 # Variables de entorno de ejemplo
```

## Requisitos

- Python 3.10+
- PostgreSQL 14+ (ejecutandose en localhost:5432)
- pip

## Instalacion

```bash
cd backend

# Crear el entorno virtual
python -m venv .venv

# Activar (Windows)
.venv\Scripts\activate

# Instalar dependencias
pip install fastapi uvicorn sqlalchemy psycopg2-binary python-dotenv pydantic

# Configurar las variables de entorno
copy .env.example .env
# Edita .env con tus credenciales de PostgreSQL

# Crear la base de datos (ejecutar el script SQL)
psql -U postgres -f ../database/actividades_postgres.sql

# Iniciar el servidor
uvicorn app.main:app --reload --port 8000
```

## Endpoints

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/actividades/` | Lista todas las actividades |
| GET | `/actividades/{id}` | Obtiene una actividad |
| POST | `/actividades/` | Crea una actividad |
| PUT | `/actividades/{id}` | Actualiza una actividad |
| DELETE | `/actividades/{id}` | Elimina una actividad |

## Documentacion interactiva

- Swagger UI: `http://localhost:8000/docs`
- ReDoc: `http://localhost:8000/redoc`

## Integracion con Android

1. El emulador de Android alcanza tu PC con `10.0.2.2`
2. Configura la BASE_URL en `app/src/main/java/com/esteban/miformacionctma/data/remote/RetrofitConfig.kt`:

```kotlin
private const val BASE_URL = "http://10.0.2.2:8000/"
```

3. Si usas un dispositivo fisico, usa la IP de tu PC:
   ```kotlin
   private const val BASE_URL = "http://192.168.1.100:8000/"
   ```

4. En AndroidManifest.xml agrega permiso de internet y permite cleartext:
   ```xml
   <uses-permission android:name="android.permission.INTERNET" />
   android:usesCleartextTraffic="true"   <!-- en <application> -->
   ```