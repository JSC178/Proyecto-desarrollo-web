/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
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
}
