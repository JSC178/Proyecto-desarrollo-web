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

    @Override
    @Transactional(readOnly = true)
    public Servicio getServicio(Servicio servicio) {
        return servicioRepository.findById(servicio.getIdServicio()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Servicio servicio) {
        servicioRepository.save(servicio);
    }

    @Override
    @Transactional
    public void delete(Servicio servicio) {
        servicioRepository.delete(servicio);
    }
}