package com.barberia.service;

import com.barberia.domain.Empleado;
import java.util.List;

public interface EmpleadoService {
    // Para que el cliente pueda elegir de una lista
    public List<Empleado> getEmpleados(boolean activos);
}
