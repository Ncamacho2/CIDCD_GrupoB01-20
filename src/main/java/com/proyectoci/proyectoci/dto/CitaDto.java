package com.proyectoci.proyectoci.dto;

import java.time.LocalDateTime;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CitaDto {
	private PacienteDto paciente;
    private MedicoDto medico;
    private LocalDateTime fechaHora;
    private String motivoConsulta;
}
