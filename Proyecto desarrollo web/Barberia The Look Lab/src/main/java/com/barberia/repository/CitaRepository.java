/*
 * Clases repository: permiten que Spring Boot realice las operaciones en la base de datos de MySQL de forma automática 
 * (consultas, eliminaciones, actualizaciones)
 */
package com.barberia.repository;

import com.barberia.domain.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    // Para ver el historial de citas pasadas del cliente  
//    List<Cita> findByIdUsuario(Long idUsuario);
}