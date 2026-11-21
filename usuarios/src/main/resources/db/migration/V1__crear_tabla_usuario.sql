CREATE TABLE usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    rut VARCHAR(12) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    password VARCHAR(50) NOT NULL,
    rol VARCHAR(20) NOT NULL DEFAULT 'CLIENTE',
    activo BIT(1) NOT NULL DEFAULT b'1',
    region VARCHAR(80) NOT NULL,
    comuna VARCHAR(80) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    foto LONGTEXT NULL,
    fecha_creacion DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_usuario_rut UNIQUE (rut),
    CONSTRAINT uk_usuario_correo UNIQUE (correo),
    CONSTRAINT uk_usuario_rol CHECK (rol IN ('CLIENTE','ADMINISTRADOR'))

);