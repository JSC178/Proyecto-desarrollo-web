package com.barberia.controller;

import com.barberia.domain.Empleado;
import com.barberia.domain.Horario;
import com.barberia.service.CitaService;
import com.barberia.service.HorarioService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agenda")
public class AgendaRestController {

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private CitaService citaService;

    @GetMapping("/horas-disponibles")
    //a que horas puede atender el barbero en un dia especifico
    public List<String> obtenerHorasDisponibles(@RequestParam Long idEmpleado, @RequestParam String fechaString) {
        List<String> horasDisponibles = new ArrayList<>();

        try {
            LocalDate fecha = LocalDate.parse(fechaString);
            
            //toma la fecha y dice que dia de la semana es 
            String diaSemana = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            diaSemana = diaSemana.substring(0, 1).toUpperCase() + diaSemana.substring(1);

            Horario horario = horarioService.findByEmpleadoAndDia(idEmpleado, diaSemana);
//valida que el barbero trabaje el dia solicitado
            if (horario == null) {
                // si no trabaja devuelve la lista vacia
                return horasDisponibles;
            }

            LocalTime horaActual = horario.getHoraInicio();
            LocalTime horaCierre = horario.getHoraFin();

            Empleado empleado = new Empleado();
            empleado.setIdEmpleado(idEmpleado);

            while (horaActual.isBefore(horaCierre)) {
                
                LocalDateTime fechaHoraVerificar = LocalDateTime.of(fecha, horaActual);
// llama a un metodo para saber si el barbero ya tiene una cita en ese momento
                boolean ocupado = citaService.existsByEmpleadoAndFechaHora(empleado, fechaHoraVerificar);

                if (!ocupado) {
                    //si no esta ocupado, agrega esa hora a la lista de horas disponibles
                    horasDisponibles.add(horaActual.toString());
                }
                
                horaActual = horaActual.plusMinutes(60); 
            }

        } catch (Exception e) {
            System.out.println("Error al calcular horas: " + e.getMessage());
        }

        return horasDisponibles;
    }
}