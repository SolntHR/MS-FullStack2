CREATE TABLE item_carrito (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    codigo VARCHAR(10) NOT NULL,
    nombre VARCHAR(120) NOT NULL,
    marca VARCHAR(60) NULL,
    modelo VARCHAR(60) NULL,
    precio INT NOT NULL,
    imagen LONGTEXT NULL,
    cantidad INT NOT NULL,
    fecha_agregado DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_item_carrito_usuario_producto UNIQUE (id_usuario, id_producto),
    CONSTRAINT ck_item_carrito_cantidad CHECK (cantidad >= 1),
    CONSTRAINT ck_item_carrito_precio CHECK (precio >= 1)
);
