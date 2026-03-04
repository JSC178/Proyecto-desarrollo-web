package com.barberia.service;

import com.barberia.domain.Servicio;
import java.util.List;

public interface ServicioService {
    //  llena el combo de servicios en el formulario de citas
    public List<Servicio> getServicios(boolean activos);
}
