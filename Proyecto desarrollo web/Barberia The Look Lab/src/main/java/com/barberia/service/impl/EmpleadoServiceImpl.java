package com.barberia.service.impl;

import com.barberia.domain.Empleado;
import com.barberia.repository.EmpleadoRepository;
import com.barberia.service.EmpleadoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Empleado> getEmpleados(boolean activos) {

        var lista = empleadoRepository.findAll();

        if (activos) {
 
            lista.removeIf(e -> !e.isActivo());
        }
        return lista;
    }


    @Override
    @Transactional(readOnly = true)
    public Empleado getEmpleado(Empleado empleado) {
        return empleadoRepository.findById(empleado.getIdEmpleado()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Empleado empleado) {
        empleadoRepository.save(empleado);
    }

    @Override
    @Transactional
    public void delete(Empleado empleado) {
        empleadoRepository.delete(empleado);
    }
}