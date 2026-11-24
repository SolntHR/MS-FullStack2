-- Agrega la respuesta del administrador a la tabla contacto ya existente.
-- Los mensajes anteriores quedan sin respuesta (NULL).
ALTER TABLE contacto
    ADD COLUMN respuesta VARCHAR(1000) NULL,
    ADD COLUMN fecha_respuesta DATETIME(6) NULL;
