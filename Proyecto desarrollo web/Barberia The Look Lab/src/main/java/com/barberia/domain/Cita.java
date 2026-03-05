package com.barberia.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Data;

@Data
@Entity
@Table(name="cita")
public class Cita implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cita")
    private Long idCita;
    
    private LocalDateTime fechaHora;
    private String observaciones;
    private String estado;

    // Relación con tabla usuario 
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;

    // Relación con tabla empleado 
    @ManyToOne
    @JoinColumn(name="id_empleado")
    private Empleado empleado;

    // Relación con tabla Servicio
    @ManyToOne
    @JoinColumn(name="id_servicio")
    private Servicio servicio;
}






