package com.barberia.repository;

import com.barberia.domain.Horario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    
    List<Horario> findByEmpleadoIdEmpleado(Long idEmpleado);
    
    Horario findByEmpleadoIdEmpleadoAndDiaSemana(Long idEmpleado, String diaSemana);
}