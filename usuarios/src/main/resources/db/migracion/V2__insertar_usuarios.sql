INSERT INTO usuario(rut, nombre, correo, password, rol, activo, region, comuna, fecha_creacion)
VALUES
('11111111-1', 'Admin Test', 'admin@sonidovivo.cl','$2a$10$aHMKsS9QKQESooUcoMKeIuVtokQK7TkV4.2v.6aTZi8SCzbHp8olK', 'ADMINISTRADOR', b'1', NULL, NULL, NOW(6)),
('12345678-5', 'Cliente Test', 'cliente@sonidovivo.cl', '$2a$10$OjLwlo5TPV.AsOIvcmpocektzmNyTbzsZnt5BEUwvrZf2792TaP7K', 'CLIENTE', b'1', 'Región Metropolitana', 'Santiago', NOW(6));
