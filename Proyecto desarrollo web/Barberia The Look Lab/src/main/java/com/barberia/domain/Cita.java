package com.barberia.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
@Entity
@Table(name = "cita")
public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long idCita;

    private String observaciones;
    private String estado;
    private Integer calificacion;
    private String comentarioCalificacion;

    // Relación con tabla usuario 
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    // Relación con tabla empleado 
    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    // Relación con tabla Servicio
    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicio servicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaHora;
}
