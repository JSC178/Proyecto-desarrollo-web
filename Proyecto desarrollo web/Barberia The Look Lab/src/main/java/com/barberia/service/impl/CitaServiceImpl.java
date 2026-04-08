/*
 *      EN ESTA CLASE ESTA EL CODIGO 
 */
package com.barberia.service.impl;

import com.barberia.domain.Cita;
import com.barberia.repository.CitaRepository;
import com.barberia.service.CitaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.barberia.domain.Empleado;
import java.time.LocalDateTime;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Override
    @Transactional
    public void save(Cita cita) {
        //toma la cita guardada y la envia al sql 
        citaRepository.save(cita);
    }

    @Override
    @Transactional
    public void delete(Cita cita) {
        //toma la cita y la elimina del sql
        citaRepository.delete(cita);
    }

    @Override
    @Transactional(readOnly = true)
    public Cita getCita(Cita cita) {
        return citaRepository.findById(cita.getIdCita()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> getCitas() {
        return citaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> getCitasPorUsuario(Long idUsuario) {
        // Debe coincidir exactamente con el nombre en el Repository
        return citaRepository.findByUsuarioIdUsuario(idUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmpleadoAndFechaHora(Empleado empleado, LocalDateTime fechaHora) {
        return citaRepository.existsByEmpleadoAndFechaHora(empleado, fechaHora);
    }
}
