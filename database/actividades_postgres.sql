-- ============================================================
-- Mi Formacion CTMA - Base de datos PostgreSQL
-- Compatible con la app Android y el backend FastAPI
-- ============================================================

-- Crear la base de datos
CREATE DATABASE miformacion_ctma;

-- Conectar a la base de datos
\c miformacion_ctma;

-- Tabla de actividades
CREATE TABLE IF NOT EXISTS actividades (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL CHECK (trim(titulo) <> ''),
    descripcion TEXT,
    fecha VARCHAR(10) NOT NULL,
    prioridad VARCHAR(10) NOT NULL DEFAULT 'Media'
        CHECK (prioridad IN ('Baja', 'Media', 'Alta')),
    progreso INTEGER NOT NULL DEFAULT 0
        CHECK (progreso >= 0 AND progreso <= 100)
);

-- Indice para el orden por prioridad y fecha
CREATE INDEX IF NOT EXISTS idx_actividades_orden
    ON actividades (prioridad, fecha DESC);

-- ============================================================
-- DATOS DE PRUEBA
-- ============================================================
INSERT INTO actividades (titulo, descripcion, fecha, prioridad, progreso) VALUES
    ('Estudiar Scrum', 'Leer la guia oficial de Scrum 2020 y resumir los 3 pilares', '2026-09-10', 'Alta', 40),
    ('Practicar SQL', 'Resolver los ejercicios de joins y subconsultas del modulo 2', '2026-09-12', 'Media', 20),
    ('Diagramas de secuencia', 'Crear los diagramas de secuencia del proyecto final', '2026-09-15', 'Alta', 0),
    ('Repasar Docker', 'Practicar los comandos de contenedores e imagenes', '2026-09-18', 'Baja', 65),
    ('Preparar sustentacion', 'Organizar las evidencias y slides para la sustentacion', '2026-09-20', 'Alta', 10);