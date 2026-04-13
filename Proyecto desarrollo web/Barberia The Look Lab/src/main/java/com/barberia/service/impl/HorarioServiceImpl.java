package com.barberia.service.impl;

import com.barberia.domain.Horario;
import com.barberia.repository.HorarioRepository;
import com.barberia.service.HorarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HorarioServiceImpl implements HorarioService {

    @Override
    @Transactional(readOnly = true)
    public List<Horario> getHorarios() {
        return horarioRepository.findAll();
    }

    @Autowired
    private HorarioRepository horarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Horario> getHorariosPorEmpleado(Long idEmpleado) {
        return horarioRepository.findByEmpleadoIdEmpleado(idEmpleado);
    }

    @Override
    @Transactional
    public void save(Horario horario) {
        horarioRepository.save(horario);
    }

    @Override
    @Transactional
    public void delete(Horario horario) {
        horarioRepository.delete(horario);
    }

    @Override
    @Transactional(readOnly = true)
    public Horario findByEmpleadoAndDia(Long idEmpleado, String diaSemana) {
        return horarioRepository.findByEmpleadoIdEmpleadoAndDiaSemana(idEmpleado, diaSemana);
    }
}
