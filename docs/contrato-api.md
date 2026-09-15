# Contrato de API — Mi Formación CTMA

Servidor simulado (MockWebServer embebido, no hay backend real).

## GET /v1/actividades

**200 OK**
```json
[
  {
    "id": 1,
    "titulo": "Laboratorio Compose",
    "descripcion": "Construir la pantalla de detalle",
    "progreso": 65,
    "diasRestantes": 2,
    "prioridad": "ALTA"
  }
]
```

**200 OK con arreglo vacío**: `[]` — es un estado válido, no un error.

**401 Unauthorized**: sesión vencida, sin cuerpo relevante.

**500 Internal Server Error**: error del servidor.

**Timeout**: sin respuesta dentro del tiempo configurado (10s conexión, 10s lectura).

## Códigos manejados

| Código | Significado | Acción de la app |
|---|---|---|
| 200 | Éxito | Actualiza Room, la UI refleja el nuevo dato |
| 401 | Sesión vencida | Pide renovar sesión, no expone el token |
| 404 | No encontrado | Error clasificado, no borra el caché |
| 500 | Error del servidor | Error clasificado, conserva el caché |
| (timeout) | Sin respuesta | Error clasificado, conserva el caché |
| (JSON inválido) | Respuesta corrupta | Error clasificado, conserva el caché |
