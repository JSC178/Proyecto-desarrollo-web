package com.barberia.repository;

import com.barberia.domain.Promocion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    //Busca la promoción que esté marcada como "fidelidad" y que esté "activa"
    Optional<Promocion> findByEsFidelidadTrueAndActivoTrue();
}