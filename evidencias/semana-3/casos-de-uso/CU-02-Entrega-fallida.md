# CU-02 - Registrar observación de entrega fallida

## Actor
Mensajero

## Objetivo
Registrar el motivo por el cual una entrega no pudo realizarse.

## Precondiciones
- El mensajero está autenticado.
- Existe una orden en proceso de entrega.
- La entrega no pudo realizarse.

## Flujo principal

1. El mensajero selecciona la orden.
2. Selecciona `Registrar entrega fallida`.
3. El sistema muestra el campo de observación.
4. El mensajero escribe el motivo.
5. El sistema valida que tenga mínimo 10 caracteres.
6. El sistema guarda la observación.
7. Se registra la incidencia.
8. El sistema confirma el registro.

## Flujo alterno A1 - Observación menor a 10 caracteres

1. El mensajero escribe una observación menor a 10 caracteres.
2. El sistema valida la longitud.
3. El sistema rechaza el registro.
4. Muestra un mensaje indicando el mínimo requerido.
5. La observación no se guarda.

## Flujo alterno A2 - Observación vacía

1. El mensajero intenta guardar sin escribir una observación.
2. El sistema detecta que el campo está vacío.
3. El sistema rechaza el registro.
4. Solicita completar la observación.

## Resultado esperado

El sistema solamente permite registrar una observación cuando cumple las condiciones establecidas.