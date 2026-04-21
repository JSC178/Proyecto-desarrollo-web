
package com.barberia.service;

import com.barberia.domain.Cita;
import com.barberia.domain.Empleado;
import java.util.List;
import java.time.LocalDateTime;

public interface CitaService {
    
    //Este es una interfaz que define las operaciones principales del módulo de citas. Define las acciones

    // Para ver el listado y precios 
    public List<Cita> getCitas();

    public List<Cita> getCitasPorUsuario(Long idUsuario);   //Devuelve las citas que pertenecen a un usuario específico.

    // Para agendar la cita 
    public void save(Cita cita);

    // Para cancelar la cita 
    public void delete(Cita cita);

    //getter
    public Cita getCita(Cita cita);
    
    boolean existsByEmpleadoAndFechaHora(Empleado empleado, LocalDateTime fechaHora);  //verifica su ya existe una cita con ese empleado

    public int contarCitasCalificadas(Long idUsuario);  //evita que dos citas se agenden al mismo tiempo con el mismo barbero
}