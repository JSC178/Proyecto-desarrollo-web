
package com.barberia.service;

import com.barberia.domain.Cita;
import com.barberia.domain.Empleado;
import java.util.List;
import java.time.LocalDateTime;

public interface CitaService {

    // Para ver el listado y precios 
    public List<Cita> getCitas();

    public List<Cita> getCitasPorUsuario(Long idUsuario);

    // Para agendar la cita 
    public void save(Cita cita);

    // Para cancelar la cita 
    public void delete(Cita cita);

    //getter
    public Cita getCita(Cita cita);
    
    boolean existsByEmpleadoAndFechaHora(Empleado empleado, LocalDateTime fechaHora);

    public int contarCitasCalificadas(Long idUsuario);
}