/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.barberia.service.impl;

import com.barberia.domain.Cita;
import com.barberia.repository.CitaRepository;
import com.barberia.service.CitaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Override
    @Transactional
    public void save(Cita cita) {
        citaRepository.save(cita);
    }

    @Override
    @Transactional
    public void delete(Cita cita) {
        citaRepository.delete(cita);
    }

    @Override
    @Transactional(readOnly = true)
    public Cita getCita(Cita cita) {
        return citaRepository.findById(cita.getIdCita()).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Cita> getCitas(){
        return citaRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public List<Cita> getCitasPorUsuario(Long idUsuario) {
        return citaRepository.findByIdUsuario(idUsuario);
    }
}
