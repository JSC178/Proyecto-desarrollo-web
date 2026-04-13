package com.barberia.service.impl;

import com.barberia.domain.Promocion;
import com.barberia.repository.CitaRepository;
import com.barberia.repository.PromocionRepository;
import com.barberia.service.PromocionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PromocionServiceImpl implements PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;
    
    @Autowired
    private CitaRepository citaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Promocion> getPromociones(boolean soloActivas) {
        var lista = promocionRepository.findAll();
        if (soloActivas) {
            lista.removeIf(p -> !p.isActivo());
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public Promocion getPromocionFidelidadSiAplica(Long idUsuario) {
        int citasValidas = citaRepository.countByUsuarioIdUsuarioAndEstadoAndCalificacionIsNotNull(idUsuario, "Finalizada");
        
        if (citasValidas >= 3) {
            return promocionRepository.findByEsFidelidadTrueAndActivoTrue().orElse(null);
        }
        
        return null;
    }

    @Override @Transactional public void save(Promocion p) { promocionRepository.save(p); }
    @Override @Transactional public void delete(Promocion p) { promocionRepository.delete(p); }
    @Override @Transactional(readOnly = true) public Promocion getPromocion(Promocion p) { 
        return promocionRepository.findById(p.getIdPromocion()).orElse(null); 
    }
}