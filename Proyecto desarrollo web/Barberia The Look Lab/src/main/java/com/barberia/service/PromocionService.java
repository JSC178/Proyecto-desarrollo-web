package com.barberia.service;

import com.barberia.domain.Promocion;
import java.util.List;

public interface PromocionService {

    public List<Promocion> getPromociones(boolean soloActivas);

    public Promocion getPromocion(Promocion promocion);

    public void save(Promocion promocion);

    public void delete(Promocion promocion);

    public Promocion getPromocionFidelidadSiAplica(Long idUsuario);
}
