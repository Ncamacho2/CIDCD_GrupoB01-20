package com.proyectoci.proyectoci.models;

import java.time.LocalDateTime;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Convert;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public class Dominio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;

	@JsonIgnore
	protected Boolean visible = true;

	@JsonIgnore
	protected Boolean disponible = true;

	@JsonIgnore
	@Convert(converter = LocalDateTimeConverter.class)
	protected LocalDateTime ultimaActualizacion;

	@JsonIgnore
	@Convert(converter = LocalDateTimeConverter.class)
	protected LocalDateTime fechaCreacion;

}
