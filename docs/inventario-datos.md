# Inventario de datos — Mi Formación CTMA

| Dato | Dónde vive | Justificación |
|---|---|---|
| Actividades (título, descripción, progreso, días, prioridad) | Room (`ActividadEntity`) | Datos estructurados, consultables, que deben persistir entre sesiones y sobrevivir a reinicios de la app. |
| Categorías | Room (`CategoriaEntity`) | Relacionadas con actividades mediante llave foránea; se benefician de consultas SQL e integridad referencial. |
| Modo de vista preferido (lista/grid) | DataStore (`PreferenciasRepository`) | Preferencia pequeña, simple, sin necesidad de consultas ni relaciones. |
| Orden de actividades preferido | DataStore | Igual que el anterior: una sola clave-valor. |
| Texto del formulario sin guardar (borrador) | Estado efímero (`rememberSaveable`) | No necesita persistir más allá de la sesión de edición actual; solo debe sobrevivir a rotaciones de pantalla. |
| Estado de si el diálogo/menú desplegable está abierto | Estado efímero (`remember`) | Puramente visual, sin significado fuera de la pantalla actual. |
