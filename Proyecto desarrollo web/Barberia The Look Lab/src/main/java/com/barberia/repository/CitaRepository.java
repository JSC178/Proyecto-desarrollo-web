package com.barberia.repository;

import com.barberia.domain.Cita;
import com.barberia.domain.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

// se construye automaticamente la consulta sql 
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByUsuarioIdUsuario(Long idUsuario);

    boolean existsByEmpleadoAndFechaHora(Empleado empleado, LocalDateTime fechaHora);

    int countByUsuarioIdUsuarioAndEstadoAndCalificacionIsNotNull(Long idUsuario, String estado);
}
