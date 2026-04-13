package com.barberia.service;

import com.barberia.domain.Empleado;
import java.util.List;

public interface EmpleadoService {
    
    
    public List<Empleado> getEmpleados(boolean activos);

    public Empleado getEmpleado(Empleado empleado);
    
    
    public void save(Empleado empleado);
    

    public void delete(Empleado empleado);
}