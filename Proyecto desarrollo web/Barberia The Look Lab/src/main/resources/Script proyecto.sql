/*
  Script de creación de base de datos para Barbershop
  Este script crea el esquema, tablas, usuarios, y
  carga datos de ejemplo.
*/
-- Sección de administración (ejecutar una vez en un entorno de desarrollo)
drop database if exists proyecto;
drop user if exists usuario_proyecto;


-- Creación de la base de datos 
CREATE database proyecto
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

-- Creación de usuarios con contraseñas seguras 
create user 'usuario_proyecto'@'%' identified by 'la_clave';


-- Asignación de permisos
-- Se otorgan permisos específicos en lugar de todos los permisos a todas las tablas futuras
grant select, insert, update, delete on proyecto.* to 'usuario_proyecto'@'%';
-- grant select on practica.* to 'usuario_practica'@'%';
flush privileges;

use proyecto;

-- Tabla de Usuarios (Para SC-400 y login)
CREATE TABLE usuario (
    id_usuario BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(512) NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    apellidos VARCHAR(30) NOT NULL,
    correo VARCHAR(50),
    telefono VARCHAR(15),
    ruta_imagen VARCHAR(1024),
    activo BOOLEAN,
    rol VARCHAR(20) DEFAULT 'ROLE_USER', -- ¡NUEVA COLUMNA!
    PRIMARY KEY (id_usuario)
) ENGINE = InnoDB;

-- 2. Tabla de Empleados
CREATE TABLE empleado (
    id_empleado BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    apellidos VARCHAR(30) NOT NULL,
    especialidad VARCHAR(50),
    ruta_imagen VARCHAR(1024),
    activo BOOLEAN,
    PRIMARY KEY (id_empleado)
) ENGINE = InnoDB;

-- 3. Tabla de Servicios (Ordenado según el prototipo)
CREATE TABLE servicio (
    id_servicio BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255),
    precio DOUBLE NOT NULL,
    duracion_minutos INT,
    ruta_imagen VARCHAR(1024),
    activo BOOLEAN,
    PRIMARY KEY (id_servicio)
) ENGINE = InnoDB;

-- 4. Tabla de Citas
CREATE TABLE cita (
    id_cita BIGINT NOT NULL AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    id_empleado BIGINT NOT NULL,
    id_servicio BIGINT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    observaciones VARCHAR(255),
    estado VARCHAR(20) DEFAULT 'AGENDADA',
    PRIMARY KEY (id_cita),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado),
    FOREIGN KEY (id_servicio) REFERENCES servicio(id_servicio)
) ENGINE = InnoDB;

CREATE TABLE horario (
    id_horario BIGINT AUTO_INCREMENT PRIMARY KEY,   
    dia_semana VARCHAR(20) NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    id_empleado BIGINT NOT NULL,                     
    FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado) ON DELETE CASCADE
);

CREATE TABLE promocion (
    id_promocion BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,          
    descripcion VARCHAR(255) NOT NULL,    
    porcentaje_descuento INT NOT NULL,       
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    activo BOOLEAN DEFAULT true             
);


ALTER TABLE promocion ADD COLUMN es_fidelidad BOOLEAN DEFAULT false;


-- Esto desactiva todo usando la llave primaria para engañar al modo seguro
UPDATE promocion SET activo = false, es_fidelidad = false WHERE id_promocion > 0;

-- Ahora inserta solo la promoción que necesitamos
INSERT INTO promocion (titulo, descripcion, porcentaje_descuento, fecha_inicio, fecha_fin, activo, es_fidelidad) 
VALUES ('Premio Cliente Frecuente', '¡Felicidades! Disfruta un 50% de descuento por ser cliente fiel.', 50, '2026-01-01', '2026-12-31', true, true);

-- 6. Inserción de datos de prueba para que Persona 2 y 4 puedan ver algo en la web
INSERT INTO empleado (nombre, apellidos, especialidad, activo) VALUES 
('Juan', 'Pérez', 'Barbero Senior', true),
('María', 'Rodríguez', 'Estilista', true);

INSERT INTO servicio (nombre, precio, duracion_minutos, activo) VALUES 
('Corte de Cabello', 5000, 30, true),
('Corte Cabello y Retoque Barba', 8000, 50, true),
('Corte de Niño', 4000, 30, true),
('Corte con una sola guía', 3000, 20, true),
('Corte de barba (Toalla Caliente)', 4000, 30, true),
('Limpieza Facial', 6000, 40, true),
('Perfilado de Cejas', 2000, 15, true),
('Corte y Limpieza Facial', 10000, 60, true);

-- Suponiendo que Juan es ID 1 y María es ID 2 según el orden de inserción
-- Lunes a Viernes para Juan Pérez (ID 1)
INSERT INTO horario (dia_semana, hora_inicio, hora_fin, id_empleado) VALUES 
('Lunes', '08:00:00', '17:00:00', 1),
('Martes', '08:00:00', '17:00:00', 1),
('Miércoles', '08:00:00', '17:00:00', 1),
('Jueves', '08:00:00', '17:00:00', 1),
('Viernes', '08:00:00', '17:00:00', 1),
('Sabado', '08:00:00', '13:00:00', 1);

-- Lunes a Viernes para María Rodríguez (ID 2)
INSERT INTO horario (dia_semana, hora_inicio, hora_fin, id_empleado) VALUES 
('Lunes', '09:00:00', '18:00:00', 2),
('Martes', '09:00:00', '18:00:00', 2),
('Miércoles', '09:00:00', '18:00:00', 2),
('Jueves', '09:00:00', '18:00:00', 2),
('Viernes', '09:00:00', '18:00:00', 2),
('Sabado', '09:00:00', '14:00:00', 2);

