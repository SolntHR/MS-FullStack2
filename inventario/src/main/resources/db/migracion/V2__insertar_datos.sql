INSERT INTO categoria (nombre, descripcion, imagen) VALUES
('Guitarras Eléctricas', 'Modelos sólidos, hollow y semi-hollow', '/img/guitarras/guitarra_e.avif'),
('Guitarras Acústicas', 'Clásicas, electroacústicas y folk', '/img/guitarras/guitarracatalogoHD.jpg'),
('Bajos Eléctricos', '4, 5 y 6 cuerdas', '/img/bajos/bajos.jpg'),
('Baterías', 'Acústicas, electrónicas y percusión', '/img/baterias/drumscatalogoHD.jpg'),
('Teclados y Pianos', 'Sintetizadores, controladores y pianos', '/img/teclados/pianocatalogoHD.jpg'),
('Amplificadores', 'Cabezales, combos y gabinetes', '/img/amplificadores/amplificadores.jpg'),
('Micrófonos', 'Dinámicos, condensador y cintas', '/img/microfonos/microfonos.jpg'),
('Pedales de Efectos', 'Distorsión, modulación y multiefectos', '/img/pedales/pedales.avif'),
('Accesorios', 'Cables, cuerdas, uñetas y fundas', '/img/accesorios/accesorios.avif'),
('Estudio y Grabación', 'Interfaces, monitores y software', '/img/estudio/audiocatalogoHD.jpg');

INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'GA001', 'Guitarra Acústica Folk', id, 'Yamaha', 'F310', 129990, 8, 'Tapa de abeto, aros y fondo de meranti. Ideal para iniciantes.', '/img/yamahaf310.png' FROM categoria WHERE nombre = 'Guitarras Acústicas';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'GA002', 'Guitarra Acústica Dreadnought', id, 'Fender', 'CD-60S', 189990, 5, 'Tapa de abeto macizo, brazo de caoba. Sonido cálido y proyectado.', '/img/guitarras/Fender CD-60S.jpg' FROM categoria WHERE nombre = 'Guitarras Acústicas';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'GA003', 'Guitarra Acústica Clásica 4/4', id, 'Yamaha', 'C40', 89990, 10, 'Nailon, tapa de abeto. Ideal para estudio y flamenco.', '/img/guitarras/Yamaha C40.jpg' FROM categoria WHERE nombre = 'Guitarras Acústicas';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'GA004', 'Guitarra Electroacústica', id, 'Takamine', 'GN20CE', 349990, 3, 'Pickup integrado, afinador incorporado.', '/img/guitarras/Guitarra Electroacústica.png' FROM categoria WHERE nombre = 'Guitarras Acústicas';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'GA005', 'Guitarra 3/4 Niños', id, 'Yamaha', 'JR1', 79990, 6, 'Tamaño reducido para niños de 6 a 10 años.', '/img/guitarras/Guitarra 3-4 Niños.png' FROM categoria WHERE nombre = 'Guitarras Acústicas';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'GE001', 'Guitarra Eléctrica Stratocaster', id, 'Squier', 'Affinity Strat', 249990, 5, 'Cuerpo de álamo, mástil de arce, pastillas SSS.', '/img/guitarras/Guitarra Eléctrica Stratocaster.png' FROM categoria WHERE nombre = 'Guitarras Eléctricas';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'GE002', 'Guitarra Eléctrica Les Paul', id, 'Epiphone', 'Les Paul Std', 329990, 4, 'Cuerpo caoba, tapa arce, pastillas humbucker.', '/img/guitarras/Guitarra Eléctrica Les Paul.png' FROM categoria WHERE nombre = 'Guitarras Eléctricas';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'GE003', 'Guitarra Eléctrica SG', id, 'Epiphone', 'SG Standard', 319990, 3, 'Cuerpo caoba, mástil caoba, 2 humbuckers.', '/img/guitarras/Guitarra Eléctrica SG.png' FROM categoria WHERE nombre = 'Guitarras Eléctricas';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'GE004', 'Guitarra Eléctrica Telecaster', id, 'Squier', 'Affinity Tele', 239990, 4, 'Cuerpo álamo, clavijero vintage, 2 pastillas single.', '/img/guitarras/Guitarra Eléctrica Telecaster.png' FROM categoria WHERE nombre = 'Guitarras Eléctricas';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'GE005', 'Guitarra Eléctrica Semi-hollow', id, 'Epiphone', 'ES-335', 549990, 2, 'Semi-hueca, 2 humbuckers, ideal para jazz y blues.', '/img/guitarras/Guitarra Eléctrica Semi-hollow.png' FROM categoria WHERE nombre = 'Guitarras Eléctricas';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'BA001', 'Bajo Eléctrico 4 Cuerdas', id, 'Squier', 'Affinity PJ', 299990, 5, 'Pickup PJ, cuerpo álamo, mástil arce.', '/img/guitarras/Bajo Eléctrico 4 Cuerdas Squier.jpg' FROM categoria WHERE nombre = 'Bajos Eléctricos';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'BA002', 'Bajo Eléctrico Jazz Bass', id, 'Fender', 'Player Jazz', 699990, 2, 'Alder body, 2 Alnico V Jazz single-coil.', '/img/guitarras/Bajo Eléctrico Jazz Bass.jpg' FROM categoria WHERE nombre = 'Bajos Eléctricos';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'BA003', 'Bajo Acústico 4 Cuerdas', id, 'Yamaha', 'APX700II', 429990, 2, 'Electroacústico, afinador incorporado.', '/img/guitarras/Bajo Acústico 4 Cuerdas.png' FROM categoria WHERE nombre = 'Bajos Eléctricos';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'BT001', 'Batería Acústica 5 piezas', id, 'Pearl', 'Roadshow', 599990, 2, 'Incluye stands, platillos y pedal de bombo.', '/img/baterias/Batería Acústica 5 piezas.png' FROM categoria WHERE nombre = 'Baterías';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'BT002', 'Batería Electrónica 8 pads', id, 'Roland', 'TD-02KV', 799990, 2, 'Módulo TD-02, 8 pads de goma, pedal hi-hat.', '/img/baterias/Batería Electrónica 8 pads.png' FROM categoria WHERE nombre = 'Baterías';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'BT003', 'Caja Snare 14"', id, 'Pearl', 'STE1450', 89990, 4, 'Acero, 14x5", 10 tensores.', '/img/baterias/Caja Snare 14.png' FROM categoria WHERE nombre = 'Baterías';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'BT004', 'Platillo Hi-Hat 14"', id, 'Zildjian', 'A Series', 149990, 3, 'Latón B20, sonido brillante y claro.', '/img/baterias/Platillo Hi-Hat 14.png' FROM categoria WHERE nombre = 'Baterías';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'BT005', 'Platillo Crash 16"', id, 'Zildjian', 'A Series', 129990, 3, 'Latón B20, ataque rápido.', '/img/baterias/Platillo Crash 16.png' FROM categoria WHERE nombre = 'Baterías';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'TC001', 'Teclado Digital 61 teclas', id, 'Yamaha', 'PSR-E373', 249990, 4, '61 teclas sensibles al tacto, 622 voces.', '/img/teclados/Teclado Digital 61 teclas.png' FROM categoria WHERE nombre = 'Teclados y Pianos';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'TC002', 'Piano Digital 88 teclas', id, 'Yamaha', 'P-45', 499990, 2, '88 teclas pesadas, 10 voces, pedal sustain incluido.', '/img/teclados/Piano Digital 88 teclas.png' FROM categoria WHERE nombre = 'Teclados y Pianos';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'TC003', 'Sintetizador 49 teclas', id, 'Arturia', 'MiniLab MKII', 129990, 5, 'MIDI controller, 49 mini teclas.', '/img/teclados/Sintetizador 49 teclas.png' FROM categoria WHERE nombre = 'Teclados y Pianos';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'TC004', 'Teclado MIDI 88 teclas', id, 'M-Audio', 'Hammer 88', 399990, 2, '88 teclas martillo, sin sonidos propios.', '/img/teclados/Teclado MIDI 88 teclas.png' FROM categoria WHERE nombre = 'Teclados y Pianos';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'AM001', 'Amplificador Guitarra 15W', id, 'Fender', 'Frontman 15G', 99990, 5, '15W, distorsión incorporada, entrada auxiliar.', '/img/amplificadores/Amplificador Guitarra 15W.png' FROM categoria WHERE nombre = 'Amplificadores';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'AM002', 'Amplificador Guitarra 40W', id, 'Marshall', 'MG40GFX', 299990, 3, '40W, 4 canales, efectos digitales integrados.', '/img/amplificadores/Amplificador Guitarra 40W.png' FROM categoria WHERE nombre = 'Amplificadores';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'AM003', 'Amplificador Bajo 100W', id, 'Hartke', 'HD100', 449990, 2, '100W, tweeter integrado, ecualizador de 4 bandas.', '/img/amplificadores/Amplificador Bajo 100W.png' FROM categoria WHERE nombre = 'Amplificadores';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'AM004', 'Amplificador Acústico 40W', id, 'Fishman', 'Loudbox Mini', 499990, 2, '60W, 2 canales, reverb y chorus incorporados.', '/img/amplificadores/Amplificador Acústico 40W.png' FROM categoria WHERE nombre = 'Amplificadores';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'MI001', 'Micrófono Dinámico Cardioide', id, 'Shure', 'SM58', 149990, 8, 'Estándar industria para voz en vivo.', '/img/microfonos/Micrófono Dinámico Cardioide.png' FROM categoria WHERE nombre = 'Micrófonos';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'MI002', 'Micrófono Dinámico Instrumento', id, 'Shure', 'SM57', 139990, 6, 'Ideal para captura de instrumentos y amplificadores.', '/img/microfonos/Micrófono Dinámico Instrumento.png' FROM categoria WHERE nombre = 'Micrófonos';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'MI003', 'Micrófono Condensador', id, 'Audio-Tech.', 'AT2020', 199990, 4, 'Cardioide, XLR, ideal para grabación en estudio.', '/img/microfonos/Micrófono Condensador.png' FROM categoria WHERE nombre = 'Micrófonos';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'MI004', 'Micrófono USB de Condensador', id, 'Blue', 'Yeti', 299990, 5, 'USB, 4 patrones polares, ideal para streaming y podcast.', '/img/microfonos/Micrófono USB de Condensador.png' FROM categoria WHERE nombre = 'Micrófonos';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'PE001', 'Pedal Distorsión', id, 'Boss', 'DS-1', 79990, 7, 'Clásico pedal de distorsión, 3 controles.', '/img/pedales/Pedal Distorsión.png' FROM categoria WHERE nombre = 'Pedales de Efectos';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'PE002', 'Pedal Reverb', id, 'Boss', 'RV-6', 179990, 4, '8 modos de reverb, control de shimmer.', '/img/pedales/Pedal Reverb.png' FROM categoria WHERE nombre = 'Pedales de Efectos';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'PE003', 'Pedal Multi-efectos', id, 'Boss', 'ME-80', 349990, 2, 'Diseño tipo pedalboard, 8 efectos simultáneos.', '/img/pedales/Pedal Multi-efectos.png' FROM categoria WHERE nombre = 'Pedales de Efectos';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'PE004', 'Pedal Tuner Cromático', id, 'Boss', 'TU-3', 89990, 8, 'Afinador cromático, indicador de tono.', '/img/pedales/Pedal Tuner Cromático.png' FROM categoria WHERE nombre = 'Pedales de Efectos';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'PE005', 'Pedal Delay', id, 'MXR', 'Carbon Copy', 179990, 4, 'Delay analógico cálido, tiempo 600ms.', '/img/pedales/Pedal Delay.png' FROM categoria WHERE nombre = 'Pedales de Efectos';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'PE006', 'Pedal Overdrive', id, 'Ibanez', 'TS9', 99990, 6, 'Tube Screamer clásico, sonido suave y orgánico.', '/img/pedales/Pedal Overdrive.png' FROM categoria WHERE nombre = 'Pedales de Efectos';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'AC001', 'Cuerdas Guitarra Eléctrica 09-42', id, 'Ernie Ball', 'Super Slinky', 8990, 25, 'Juego 6 cuerdas, calibre ligero.', '/img/accesorios/Cuerdas Guitarra Eléctrica 09-42.png' FROM categoria WHERE nombre = 'Accesorios';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'AC002', 'Cuerdas Guitarra Acústica 12-53', id, 'Ernie Ball', 'Earthwood', 10990, 20, 'Bronce fósforo, sonido cálido.', '/img/accesorios/Cuerdas Guitarra Acústica 12-53.png' FROM categoria WHERE nombre = 'Accesorios';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'AC003', 'Cuerdas Bajo 45-105', id, 'Ernie Ball', 'Regular Slinky', 14990, 12, 'Cuerdas de níquel enrollado, set 4 cuerdas.', '/img/accesorios/Cuerdas Bajo 45-105.png' FROM categoria WHERE nombre = 'Accesorios';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'AC004', 'Púas de Guitarra x10 (0.73mm)', id, 'Fender', '351', 3990, 50, 'Celulosa, grosor medio.', '/img/accesorios/Púas de Guitarra x10 (0.73mm).png' FROM categoria WHERE nombre = 'Accesorios';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'AC005', 'Capotraste Guitarra', id, 'Dunlop', 'Trigger', 12990, 15, 'Capotraste de resorte, compatible 6 cuerdas.', '/img/accesorios/Capotraste Guitarra.png' FROM categoria WHERE nombre = 'Accesorios';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'AC006', 'Afinador de Clip', id, 'Snark', 'SN-5', 8990, 20, 'Afinador cromático de clip, pantalla giratoria.', '/img/accesorios/Afinador de Clip.png' FROM categoria WHERE nombre = 'Accesorios';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'AC007', 'Cable Instrumento 3m', id, 'Monster', 'S100-I-3', 12990, 15, 'Cable trenzado, conectores dorados, 3 metros.', '/img/accesorios/Cable Instrumento 3m.png' FROM categoria WHERE nombre = 'Accesorios';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'AC008', 'Cable Instrumento 6m', id, 'Monster', 'S100-I-6', 17990, 10, 'Cable trenzado, conectores dorados, 6 metros.', '/img/accesorios/Cable Instrumento 6m.png' FROM categoria WHERE nombre = 'Accesorios';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'AC009', 'Soporte Guitarra de Piso', id, 'Hercules', 'GS302B', 22990, 12, 'Soporte plegable con enganche automático.', '/img/accesorios/Soporte Guitarra de Piso.png' FROM categoria WHERE nombre = 'Accesorios';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'AC010', 'Soporte Guitarra de Pared', id, 'Hercules', 'WAH-202', 18990, 10, 'Montaje a pared, enganche automático.', '/img/accesorios/Soporte Guitarra de Pared.png' FROM categoria WHERE nombre = 'Accesorios';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'ES001', 'Interfaz de Audio 2x2 USB', id, 'Focusrite', 'Scarlett Solo', 149990, 4, '1 entrada XLR+instrumento, 2 salidas, 24bit/192kHz.', '/img/estudio/Interfaz de Audio 2x2 USB.png' FROM categoria WHERE nombre = 'Estudio y Grabación';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'ES002', 'Auriculares de Estudio', id, 'Audio-Tech.', 'ATH-M20x', 79990, 6, 'Circumaurales, respuesta 15Hz-20kHz.', '/img/estudio/Auriculares de Estudio.png' FROM categoria WHERE nombre = 'Estudio y Grabación';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'ES003', 'Auriculares de Estudio Pro', id, 'Audio-Tech.', 'ATH-M50x', 219990, 4, 'Referencia de industria, sonido neutro y detallado.', '/img/estudio/Auriculares de Estudio Pro.png' FROM categoria WHERE nombre = 'Estudio y Grabación';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'ES004', 'Monitor de Estudio 5"', id, 'Yamaha', 'HS5', 349990, 2, 'Altavoz activo, respuesta plana, ideal mezcla.', '/img/estudio/Monitor de Estudio 5.png' FROM categoria WHERE nombre = 'Estudio y Grabación';
INSERT INTO producto (codigo, nombre, id_categoria, marca, modelo, precio, stock, descripcion, imagen)
SELECT 'ES005', 'Pop Filter para Micrófono', id, 'Sennheiser', 'MZP 40', 14990, 8, 'Doble malla, brazo flexible con clip.', '/img/estudio/Pop Filter para Micrófono.png' FROM categoria WHERE nombre = 'Estudio y Grabación';
