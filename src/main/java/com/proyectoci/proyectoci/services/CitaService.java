package com.proyectoci.proyectoci.services;

import com.proyectoci.proyectoci.dto.CitaDto;
import com.proyectoci.proyectoci.models.Cita;
import org.springframework.stereotype.Service;

@Service
public interface CitaService extends CrudService<Cita, CitaDto> {
}