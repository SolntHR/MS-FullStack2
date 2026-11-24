CREATE TABLE noticia (
    id BIGINT NOT NULL AUTO_INCREMENT,
    categoria VARCHAR(80) NOT NULL,
    titulo VARCHAR(180) NOT NULL,
    fecha DATE NOT NULL,
    resumen VARCHAR(500) NOT NULL,
    contenido LONGTEXT NOT NULL,
    imagen VARCHAR(500) NULL,
    PRIMARY KEY (id)
);
