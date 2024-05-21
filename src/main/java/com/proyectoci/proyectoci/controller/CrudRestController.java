package com.proyectoci.proyectoci.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;

/**
 * Creada por Manuel larrota el 11/04/19. Clase generica con las funciones crud,
 * para facilitar el mantenimiento de dichas funciones.
 *
 * @param <T> parametro DTO para retorno y recibir datos.
 */

public interface CrudRestController<T> {

	/**
	 * Retorna todas los registros de la entidad.
	 *
	 * @param pageable paginable
	 *
	 * @return T Entidad
	 */
	@GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
	Page<T> getAllPage(@RequestHeader(value = "Authorization", required = false) String token, Pageable pageable,
			T dto);

	@GetMapping(produces = MediaTypes.HAL_JSON_VALUE, value = "/alljson")
	List<T> getAll(@RequestHeader(value = "Authorization", required = false) String token, T dto);

	/**
	 * Retorna un unico elemento.
	 *
	 * @param id parametror identificador de la entidad a buscar
	 *
	 * @return Retorna la entidad en dto
	 */
	@GetMapping(value = "{id}", produces = MediaTypes.HAL_JSON_VALUE)
	T show(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable int id);

	/**
	 * Crea un elemento de la entidad.
	 *
	 * @param dto   objeto DTO de la entidad
	 * @param token token de autenticacion
	 *
	 * @return DTo
	 */
	@PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
	T create(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody T dto);

	/**
	 * Eliminar un elemento de la entidad.
	 *
	 * @param request array de enteros
	 */
	@DeleteMapping(produces = MediaTypes.HAL_JSON_VALUE)
	void delete(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody Integer request);

	/**
	 * Actualizar un elemento de la entidad.
	 *
	 * @param token token identificador
	 * @param id    identificador de entidad
	 * @param dto   dto de entidad
	 *
	 * @return dto
	 */
	@PutMapping(value = "{id}", produces = MediaTypes.HAL_JSON_VALUE)
	T update(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable int id,
			@RequestBody T dto);
}
