CREATE TABLE categoria (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(60) NOT NULL,
    descripcion VARCHAR(100) NULL,
    imagen LONGTEXT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_categoria_nombre UNIQUE (nombre)
);

CREATE TABLE producto (
    id BIGINT NOT NULL AUTO_INCREMENT,
    codigo VARCHAR(10) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    id_categoria BIGINT NOT NULL,
    marca VARCHAR(60) NOT NULL,
    modelo VARCHAR(60) NOT NULL,
    precio INT NOT NULL,
    stock INT NOT NULL,
    descripcion VARCHAR(250) NULL,
    imagen LONGTEXT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_producto_codigo UNIQUE (codigo),
    CONSTRAINT fk_producto_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id),
    CONSTRAINT uk_producto_precio CHECK (precio >=1),
    CONSTRAINT uk_producto_stock CHECK (precio >= 0)
);