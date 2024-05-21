package com.proyectoci.proyectoci.services.impl;

import java.util.List;

import com.proyectoci.proyectoci.dto.CitaDto;
import com.proyectoci.proyectoci.models.Cita;
import com.proyectoci.proyectoci.repository.CitaRepository;
import com.proyectoci.proyectoci.services.CitaService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class CitaServiceImpl extends CrudServiceImpl<Cita, CitaRepository, CitaDto> implements CitaService {

	public CitaServiceImpl(CitaRepository repository) {
		super(repository, Cita.class, CitaDto.class);
	}


}