package com.proyectoci.proyectoci.controller;

import com.proyectoci.proyectoci.controller.impl.CrudRestControllerImpl;
import com.proyectoci.proyectoci.dto.CitaDto;
import com.proyectoci.proyectoci.models.Cita;
import com.proyectoci.proyectoci.services.CitaService;
import com.proyectoci.proyectoci.services.CrudService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class citasController extends CrudRestControllerImpl<Cita, CitaDto> {
    protected citasController(CitaService citaService) {
        super(citaService);
    }
}
