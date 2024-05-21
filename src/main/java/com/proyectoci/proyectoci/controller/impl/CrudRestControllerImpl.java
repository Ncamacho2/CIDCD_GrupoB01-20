package com.proyectoci.proyectoci.controller.impl;

import java.util.List;

import com.proyectoci.proyectoci.controller.CrudRestController;
import com.proyectoci.proyectoci.services.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;



import jakarta.validation.Valid;


public abstract class CrudRestControllerImpl<I, T> implements CrudRestController<T> {

  protected final Logger log = LoggerFactory.getLogger(this.getClass().getName());

  protected final CrudService<I, T> service;

  protected CrudRestControllerImpl(CrudService<I, T> service) {
    this.service = service;
  }

  @Override
   public Page<T> getAllPage(
      @RequestHeader(value = "Authorization",
              required = false) String token,
       Pageable pageable,
      T dto) {
    log.info("{}", dto);
    return service.encontarTodosDto(Example.of(service.obtenerInstancia(dto)), pageable);
  }

  @Override
  public List<T> getAll( @RequestHeader(value = "Authorization",
          required = false) String token,
      T dto) {
    log.info("{}", dto);
    return service.encontarTodosDto(Example.of(service.obtenerInstancia(dto)));
  }

  @Override
  public T show(@RequestHeader(value = "Authorization",
          required = false) String token,
      @PathVariable int id) {
    log.info("{}", id);
    return service.buscatDtoPorId(id);
  }

  @Override
  public T create(@RequestHeader(value = "Authorization",
          required = false) String token,
      @Valid @RequestBody T dto) {
    log.info("{}", dto);
    return service.guardarDto(dto);
  }

  @Override
  public void delete( @RequestHeader(value = "Authorization",
          required = false) String token,
      @RequestBody Integer request) {
    log.info("{}", request);
    service.eliminar(request);
  }

  @Override
  public T update(@RequestHeader(value = "Authorization",
          required = false) String token,
      @PathVariable int id, @RequestBody T dto) {
    log.info("{}", dto);
    return service.actualizarDto(dto, id);
  }
}
