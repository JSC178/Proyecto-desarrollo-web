package com.barberia.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name="servicio")
public class Servicio implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_servicio")
    private Long idServicio;
    
    private String nombre;
    private String descripcion;
    private Double precio;

    @Column(name="duracion_minutos")
    private int duracionMinutos;
    
    private boolean activo;
}