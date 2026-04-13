package com.barberia.service;

import com.barberia.domain.Horario;
import java.util.List;

public interface HorarioService {

    public List<Horario> getHorariosPorEmpleado(Long idEmpleado);

    public List<Horario> getHorarios();
    
    public void save(Horario horario);

    public void delete(Horario horario);

    public Horario findByEmpleadoAndDia(Long idEmpleado, String diaSemana);
}
