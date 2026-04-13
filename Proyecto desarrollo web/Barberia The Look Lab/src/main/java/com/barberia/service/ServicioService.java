package com.barberia.service;

import com.barberia.domain.Servicio;
import java.util.List;

public interface ServicioService {
    
    // Trae la lista de servicios
    public List<Servicio> getServicios(boolean activos);
    
    // Busca un solo servicio por su id
    public Servicio getServicio(Servicio servicio);
    
    // Guarda un servicio nuevo o actualiza uno existente
    public void save(Servicio servicio);
    
    // Elimina un servicio
    public void delete(Servicio servicio);
}