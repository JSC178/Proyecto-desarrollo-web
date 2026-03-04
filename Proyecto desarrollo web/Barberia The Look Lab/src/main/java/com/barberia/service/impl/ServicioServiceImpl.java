package com.barberia.service.impl;

import com.barberia.domain.Servicio;
import com.barberia.repository.ServicioRepository;
import com.barberia.service.ServicioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> getServicios(boolean activos) {
        var lista = servicioRepository.findAll();
        if (activos) {
            lista.removeIf(s -> !s.isActivo());
        }
        return lista;
    }
}