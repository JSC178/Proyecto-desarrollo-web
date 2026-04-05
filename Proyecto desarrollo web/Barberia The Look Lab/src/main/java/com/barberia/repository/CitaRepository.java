/*
 * Clases repository: permiten que Spring Boot realice las operaciones en la base de datos de MySQL de forma automática 
 * (consultas, eliminaciones, actualizaciones)
 */
package com.barberia.repository;

import com.barberia.domain.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    // Busca dentro del objeto 'usuario' el atributo 'idUsuario'
    List<Cita> findByUsuarioIdUsuario(Long idUsuario);
}
