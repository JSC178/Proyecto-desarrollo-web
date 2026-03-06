/*
  Script de creación de base de datos para TechShop
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
    id_usuario INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(512) NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    apellidos VARCHAR(30) NOT NULL,
    correo VARCHAR(50),
    telefono VARCHAR(15),
    ruta_imagen VARCHAR(1024),
    activo BOOLEAN,
    PRIMARY KEY (id_usuario)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- 3. Tabla de Empleados (Para SC-402: Selección de barbero)
CREATE TABLE empleado (
    id_empleado INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    apellidos VARCHAR(30) NOT NULL,
    especialidad VARCHAR(50),
    ruta_imagen VARCHAR(1024),
    activo BOOLEAN,
    PRIMARY KEY (id_empleado)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- 4. Tabla de Servicios (Para SC-401, SC-404 y SC-406)
CREATE TABLE servicio (
    id_servicio INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255),
    precio DOUBLE NOT NULL,
    duracion_minutos INT,
    ruta_imagen VARCHAR(1024),
    activo BOOLEAN,
    PRIMARY KEY (id_servicio)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- 5. Tabla de Citas (Para SC-407: Agendar cita)
CREATE TABLE cita (
    id_cita INT NOT NULL AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    id_empleado INT NOT NULL,
    id_servicio INT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    observaciones VARCHAR(255),
    estado VARCHAR(20) DEFAULT 'AGENDADA', -- AGENDADA, COMPLETADA, CANCELADA
    PRIMARY KEY (id_cita),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado),
    FOREIGN KEY (id_servicio) REFERENCES servicio(id_servicio)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- 6. Inserción de datos de prueba para que Persona 2 y 4 puedan ver algo en la web
INSERT INTO empleado (nombre, apellidos, especialidad, activo) VALUES 
('Juan', 'Pérez', 'Barbero Senior', true),
('María', 'Rodríguez', 'Estilista', true);

INSERT INTO servicio (nombre, descripcion, precio, duracion_minutos, activo) VALUES 
('Corte de Cabello', 'Corte moderno con tijera o máquina', 5000, 30, true),
('Afeitado de Barba', 'Afeitado clásico con toalla caliente', 3000, 20, true);