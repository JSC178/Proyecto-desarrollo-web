//Clases repository: permiten que Spring Boot realice las operaciones en la base de datos de MySQL de forma automática 
// (consultas, eliminaciones, actualizaciones)
package com.barberia.repository;

import com.barberia.domain.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
}
