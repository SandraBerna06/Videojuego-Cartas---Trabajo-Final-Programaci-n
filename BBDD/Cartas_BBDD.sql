DROP DATABASE IF EXISTS cartas;

CREATE DATABASE IF NOT EXISTS cartas;

USE cartas;

CREATE TABLE Rareza (
    id_rareza INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    probabilidad_obtencion DECIMAL(5,2) NOT NULL
);

CREATE TABLE Region (
    id_region INT AUTO_INCREMENT PRIMARY KEY,
    nombre_region VARCHAR(100) NOT NULL,
    historia TEXT NOT NULL
);

-- Contiene todos los atributos que comparten tanto los personajes como los hechizos.
CREATE TABLE Carta (
    id_carta INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT,
    tipo_carta VARCHAR(50) NOT NULL,
    id_region INT,
    id_rareza INT,
    FOREIGN KEY (id_region) REFERENCES Region(id_region) ON DELETE CASCADE,
    FOREIGN KEY (id_rareza) REFERENCES Rareza(id_rareza) ON DELETE CASCADE
);

-- Guarda exclusivamente las estadísticas de combate de los héroes/agentes.
CREATE TABLE Carta_personaje (
    id_carta INT PRIMARY KEY,
    categoria VARCHAR(50) NOT NULL,
    puntos_salud INT NOT NULL,
    velocidad INT NOT NULL,
    FOREIGN KEY (id_carta) REFERENCES Carta(id_carta) ON DELETE CASCADE
);

-- Guarda exclusivamente las mecánicas de las cartas de efecto global.
CREATE TABLE Carta_hechizo (
    id_carta INT PRIMARY KEY,
    tipo_efecto VARCHAR(50) NOT NULL,
    duracion_turnos INT NOT NULL,
    FOREIGN KEY (id_carta) REFERENCES Carta(id_carta) ON DELETE CASCADE
);

CREATE TABLE Habilidad (
    id_habilidad INT AUTO_INCREMENT PRIMARY KEY,
    nombre_habilidad VARCHAR(100) NOT NULL,
    efecto VARCHAR(50) NOT NULL,
    valor_efecto INT NOT NULL,
    coste_uso INT NOT NULL,
    id_carta INT,
    FOREIGN KEY (id_carta) REFERENCES Carta_Personaje(id_carta) ON DELETE CASCADE
);

CREATE TABLE Jugador (
    id_jugador INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    nivel INT DEFAULT 1,
    monedas_ficticias INT DEFAULT 0,
    fecha_creacion DATE
);

CREATE TABLE Mazo_jugador (
    id_mazo INT AUTO_INCREMENT PRIMARY KEY,
    id_jugador INT,
    id_carta INT,
    FOREIGN KEY (id_jugador) REFERENCES Jugador(id_jugador) ON DELETE CASCADE,
    FOREIGN KEY (id_carta) REFERENCES Carta(id_carta) ON DELETE CASCADE
);

INSERT INTO Rareza (nombre, probabilidad_obtencion) VALUES 
('Común', 90.00),
('Rara', 60.00),
('Épica', 30.00),
('Legendaria', 5.00);

INSERT INTO Region (nombre_region, historia) VALUES 
('Neo-Veridia', 
'Una metrópolis ciber-mágica donde la tecnología punta y los cristales de maná coexisten. Gobernada por corporaciones despiadadas,
es el hogar de inventores brillantes, mercenarios aumentados y hackers que controlan la red de energía de la ciudad.'),
('Bosque de Ámbar', 
'Un ecosistema ancestral donde los árboles están fosilizados en ámbar brillante y el suelo palpita con energía primigenia.
Sus habitantes, druidas cambiaformas y bestias místicas, defienden ferozmente el equilibrio natural contra cualquier invasor.'),
('Abismo de Nulidad', 
'Un páramo desolado en el extremo del mundo, corrompido por una antigua brecha dimensional.
En este lugar donde la luz apenas existe, habitan entidades sombrías, magos exiliados y aberraciones nacidas del puro caos.'),
('Cumbres de Solari', 
'Un archipiélago de islas flotantes suspendidas muy por encima de las nubes, bañadas por una luz dorada eterna.
Es la cuna de los paladines, seres celestiales y justicieros que buscan imponer el orden absoluto en el mundo inferior.');

INSERT INTO Carta (nombre, descripcion, tipo_carta, id_region, id_rareza) VALUES 
-- PERSONAJES
('JAX-9', 'Un mercenario mejorado con implantes cibernéticos, experto en asaltos rápidos y tácticas de guerrilla urbana.', 'Personaje', 1, 2),
('Nova', 'Ex-hacker de la red central. Utiliza pulsos electromagnéticos para deshabilitar las defensas enemigas.', 'Personaje', 1, 3),
('T-Bone', 'Pura fuerza bruta y blindaje de titanio. Fue un guardaespaldas de alto nivel antes de las guerras corporativas.', 'Personaje', 1, 1),
('Elara', 'Sacerdotisa del ámbar. Puede canalizar la savia vital del bosque para cerrar las heridas más profundas.', 'Personaje', 2, 1),
('Grox el Protector', 'Un titán de roca y musgo que ha custodiado el corazón del bosque durante más de un milenio.', 'Personaje', 2, 2),
('Sylas', 'Un cazador espectral que se mimetiza con el follaje para atacar desde las sombras con precisión letal.', 'Personaje', 2, 4),
('Malzira', 'Maestra de las artes oscuras que puede abrir pequeñas brechas al vacío para atrapar a sus oponentes.', 'Personaje', 3, 3),
('Vex', 'Una entidad caótica que se alimenta del miedo ajeno, lanzándose al combate para sembrar el pánico.', 'Personaje', 3, 1),
('Nyx, la Sombra', 'Nacida en la oscuridad total. Es capaz de teletransportarse distancias cortas a través de las sombras.', 'Personaje', 3, 4),
('Aurelius', 'Un ser celestial cuya sola presencia inspira valor y otorga escudos de luz a sus aliados.', 'Personaje', 4, 3),
('Lumin', 'Paladín del sol eterno. Su escudo radiante ciega a los enemigos, permitiendo el avance de su equipo.', 'Personaje', 4, 2),
('Serafina', 'Jueza de las nubes. Invoca ráfagas de viento solar para reorganizar el campo de batalla a su antojo.', 'Personaje', 4, 1),

-- HECHIZOS
('Distorsión Temporal', 'Juegas dos turnos seguidos inmediatamente.', 'Hechizo', NULL, 4),
('Baluarte de Aether', 'Duplica tu defensa actual durante los próximos ataques.', 'Hechizo', NULL, 2),
('Silencio Absoluto', 'El enemigo no puede usar habilidades mágicas en su próximo turno.', 'Hechizo', NULL, 3),
('Adrenalina Pura', 'Tu próximo ataque inflige el doble de daño.', 'Hechizo', NULL, 2);


INSERT INTO Carta_Personaje (id_carta, categoria, puntos_salud, velocidad) VALUES 
(1, 'Duelista', 110, 85),
(2, 'Controlador', 100, 70),
(3, 'Tanque', 180, 30),
(4, 'Support', 90, 65),
(5, 'Tanque', 200, 25),
(6, 'Duelista', 95, 95),
(7, 'Controlador', 120, 60),
(8, 'Iniciador', 130, 50),
(9, 'Duelista', 85, 100),
(10, 'Support', 140, 45),
(11, 'Iniciador', 125, 75),
(12, 'Controlador', 105, 60);

INSERT INTO Carta_Hechizo (id_carta, tipo_efecto, duracion_turnos) VALUES 
(13, 'Turno Extra', 1),
(14, 'Multiplicador Defensa', 3),
(15, 'Bloqueo de Habilidad', 1),
(16, 'Multiplicador Daño', 1);

INSERT INTO Habilidad (nombre_habilidad, efecto, valor_efecto, coste_uso, id_carta) VALUES 
-- Habilidades de JAX-9
('Corte de Plasma', 'Daño', 45, 2, 1),
('Sobrecarga de Núcleo', 'Buff', 20, 3, 1), -- Aumenta su propio daño/velocidad
-- Habilidades de Nova
('Pulso EMP', 'Daño', 30, 2, 2),
('Cortafuegos', 'Defensa', 25, 3, 2), -- Mitiga daño enemigo
-- Habilidades de T-Bone
('Golpe Sísmico', 'Daño', 35, 2, 3),
('Blindaje de Titanio', 'Defensa', 60, 3, 3),
-- Habilidades de Elara
('Brisa de Ámbar', 'Cura', 40, 2, 4),
('Savia Protectora', 'Defensa', 30, 2, 4),
-- Habilidades de Grox, el Protector
('Avalancha', 'Daño', 40, 3, 5),
('Muro de Raíces', 'Defensa', 70, 4, 5),
-- Habilidades de Sylas
('Flecha Espectral', 'Daño', 50, 2, 6),
('Emboscada Letal', 'Daño', 85, 4, 6), -- Habilidad de alto coste y mucho daño
-- Habilidades de Malzira
('Orbe del Vacío', 'Daño', 40, 2, 7),
('Atadura Sombría', 'Debuff', 25, 3, 7), -- Reduce las estadísticas del rival
-- Habilidades de Vex
('Grito Terrorífico', 'Daño', 35, 2, 8),
('Aura de Pánico', 'Debuff', 20, 2, 8),
-- Habilidades de Nyx, la Sombra
('Puñalada Umbría', 'Daño', 55, 2, 9),
('Corte Dimensional', 'Daño', 90, 4, 9),
-- Habilidades de Aurelius
('Luz Redentora', 'Cura', 50, 3, 10),
('Égida Celestial', 'Defensa', 45, 2, 10),
-- Habilidades de Lumin
('Rayo Solar', 'Daño', 45, 2, 11),
('Ceguera Radiante', 'Debuff', 30, 3, 11),
-- Habilidades de Serafina
('Ciclón Cortante', 'Daño', 40, 2, 12),
('Vientos Alisios', 'Buff', 25, 2, 12); -- Aumenta la velocidad del equipo

INSERT INTO Jugador (nombre_usuario, nivel, monedas_ficticias, fecha_creacion) VALUES 
('ShadowHunter', 15, 2500, NOW()),
('NeonViper', 8, 1200, NOW()),
('MasterSage', 22, 5400, NOW()),
('CyberGhost', 5, 450, NOW()),
('SolarKnight', 30, 10000, NOW()),
('VoidWalker', 12, 1850, NOW()),
('IronTitan', 18, 3100, NOW()),
('MysticLeaf', 3, 200, NOW()),
('NovaBlaze', 25, 7200, NOW()),
('ZenithX', 10, 1500, NOW());

-- ==============================================================================
-- INSERCIÓN EN MAZO_JUGADOR (Mezclando Personajes y Hechizos)
-- IDs 1 al 12 son Personajes. IDs 13 al 16 son Hechizos.
-- ==============================================================================

INSERT INTO Mazo_Jugador (id_jugador, id_carta) VALUES 
-- Mazo de ShadowHunter (id_jugador = 1) -> Agresivo (Duelistas + Daño x2)
(1, 1),   -- JAX-9 (Personaje)
(1, 6),   -- Sylas (Personaje)
(1, 16),  -- Adrenalina Pura (Hechizo: Daño x2)

-- Mazo de NeonViper (id_jugador = 2) -> Control total + Bloqueo
(2, 2),   -- Nova (Personaje)
(2, 7),   -- Malzira (Personaje)
(2, 15),  -- Silencio Absoluto (Hechizo: Bloquea magias)

-- Mazo de MasterSage (id_jugador = 3) -> Supervivencia (Cura + Defensa)
(3, 3),   -- T-Bone (Personaje)
(3, 4),   -- Elara (Personaje)
(3, 14),  -- Baluarte de Aether (Hechizo: Doble Defensa)

-- Mazo de CyberGhost (id_jugador = 4) -> Velocidad e Iniciación
(4, 8),   -- Vex (Personaje)
(4, 11),  -- Lumin (Personaje)
(4, 13),  -- Distorsión Temporal (Hechizo: Turno Extra)

-- Mazo de SolarKnight (id_jugador = 5) -> Solari Defensivo
(5, 10),  -- Aurelius (Personaje)
(5, 12),  -- Serafina (Personaje)
(5, 14),  -- Baluarte de Aether (Hechizo: Doble Defensa)

-- Mazo de VoidWalker (id_jugador = 6) -> Nulidad Letal
(6, 9),   -- Nyx, la Sombra (Personaje)
(6, 7),   -- Malzira (Personaje)
(6, 16),  -- Adrenalina Pura (Hechizo: Daño x2)

-- Mazo de IronTitan (id_jugador = 7) -> Muro Infranqueable
(7, 5),   -- Grox el Protector (Personaje)
(7, 3),   -- T-Bone (Personaje)
(7, 15),  -- Silencio Absoluto (Hechizo: Bloquea magias)

-- Mazo de MysticLeaf (id_jugador = 8) -> Bosque Impredecible
(8, 4),   -- Elara (Personaje)
(8, 6),   -- Sylas (Personaje)
(8, 13),  -- Distorsión Temporal (Hechizo: Turno Extra)

-- Mazo de NovaBlaze (id_jugador = 9) -> Asalto Veridia
(9, 1),   -- JAX-9 (Personaje)
(9, 2),   -- Nova (Personaje)
(9, 16),  -- Adrenalina Pura (Hechizo: Daño x2)

-- Mazo de ZenithX (id_jugador = 10) -> Balance Táctico
(10, 11), -- Lumin (Personaje)
(10, 5),  -- Grox el Protector (Personaje)
(10, 14); -- Baluarte de Aether (Hechizo: Doble Defensa)

DELIMITER //

CREATE PROCEDURE ObtenerMazo(IN p_nombre_usuario VARCHAR(50))
BEGIN
    SELECT c.id_carta, c.nombre, c.tipo_carta, cp.puntos_salud, cp.velocidad, ch.tipo_efecto
    FROM Jugador j
    JOIN Mazo_jugador mj ON j.id_jugador = mj.id_jugador
    JOIN Carta c ON mj.id_carta = c.id_carta
    LEFT JOIN Carta_personaje cp ON c.id_carta = cp.id_carta
    LEFT JOIN Carta_hechizo ch ON c.id_carta = ch.id_carta
    WHERE j.nombre_usuario = p_nombre_usuario;
END //

DELIMITER ;