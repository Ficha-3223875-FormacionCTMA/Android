# CU-01 - Confirmar entrega

## Actor
Mensajero

## Objetivo
Confirmar que una orden fue entregada correctamente y registrar la evidencia correspondiente.

## Precondiciones
- El mensajero está autenticado.
- La orden está asignada.
- La orden está disponible para entrega.

## Flujo principal

1. El mensajero selecciona una orden asignada.
2. El sistema muestra la información de la orden.
3. El mensajero inicia la confirmación.
4. Registra una fotografía de la entrega.
5. Identifica al receptor.
6. El sistema valida la información.
7. La orden cambia a `ENTREGADA`.
8. Se registra la fecha.
9. Se registra la auditoría.
10. El sistema confirma la entrega.

## Flujo alterno A1 - Sin fotografía

1. El mensajero intenta confirmar la entrega.
2. No proporciona fotografía.
3. El sistema detecta la ausencia de evidencia.
4. La confirmación es rechazada.
5. La orden conserva su estado anterior.

## Flujo alterno A2 - Receptor no identificado

1. El mensajero intenta confirmar la entrega.
2. No identifica al receptor.
3. El sistema detecta la información faltante.
4. La confirmación es rechazada.
5. La orden no cambia a `ENTREGADA`.

## Resultado esperado

La orden solamente pasa a `ENTREGADA` cuando se cumplen las condiciones requeridas.