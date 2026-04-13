package com.barberia.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import lombok.Data;

@Data
@Entity
@Table(name = "horario")
public class Horario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private Long idHorario;

    @Column(name = "dia_semana")
    private String diaSemana;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fin")
    private LocalTime horaFin;

    // Relación con el Empleado
    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;
}
