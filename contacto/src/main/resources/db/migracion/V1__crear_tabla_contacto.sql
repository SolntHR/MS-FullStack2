CREATE TABLE contacto (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    mensaje VARCHAR(500) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    fecha_creacion DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT ck_contacto_estado CHECK (estado IN ('PENDIENTE', 'ATENDIDO'))
);
