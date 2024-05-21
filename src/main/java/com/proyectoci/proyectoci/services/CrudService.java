package com.proyectoci.proyectoci.services;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
/**
 * Interfaz que sirve para encapsular los metodos genericos del servicio en cuanto a funciones CRUD
 * se especifica.
 *
 * @param <I> Instancia entity
 * @param <T> Instancia DTO
 */
@Transactional
public interface CrudService<I, T> {

  /**
   * Guarda y retorna un DTO.
   *
   * @param dto instancia A la que pertenece el crud
   *
   * @return dto
   */
  T guardarDto(T dto);

  /**
   * Guarda y retorna instancia guardada.
   *
   * @param instance instancia A la que pertenece el crud
   *
   * @return instancia
   */
  I guardar(I instance);

  /**
   * Guarda una lista de elementos.
   *
   * @param instances instancia A la que pertenece el crud
   *
   * @return lista de entidades
   */
  List<I> guardar(List<I> instances);

  /**
   * Busca por id y retorna DTO.
   *
   * @param id identificador de la instancia
   *
   * @return dto de la entidad
   */
  T buscatDtoPorId(int id);

  /**
   * Busca instancia por id.
   *
   * @param id identificador de la instancia
   *
   * @return Entidad
   */
  I buscarPorId(int id);

  /**
   * Permite buscar por specification. de manera paginada.
   *
   * @param specification specification parametrizada
   * @param pageable parametro de paginacion
   *
   * @return resultado de la busqueda.
   */
  Page<I> encontarTodosPage(Specification<I> specification, Pageable pageable);

  /**
   * Lista todos los elementos en DTOs.
   *
   * @return Lista de dtos
   */
  List<T> encontarTodosDto();

  /**
   * Lista todos a partir de un EXAMPLE.
   *
   * @param example objeto parametrizado
   *
   * @return lista de dtos
   */
  List<T> encontarTodosDto(Example<I> example);

  /**
   * Lista todos en DTOs.
   *
   * @param pageable parametro de paginacion
   *
   * @return dtos paginados
   */
  Page<T> encontarTodosDto(Pageable pageable);

  /**
   * Lista todos en DTOs de manera paginada a partir de un EXAMPLE.
   *
   * @param example objeto parametrizado
   * @param pageable parametro de paginacion
   *
   * @return Lista paginada
   */
  Page<T> encontarTodosDto(Example<I> example, Pageable pageable);

  /**
   * Lista todas las entidades paginadas.
   *
   * @param example objeto parametrizado
   * @param pageable parametro de paginacion.
   *
   * @return lista paginada.
   */
  Page<I> encontarTodos(Example<I> example, Pageable pageable);

  /**
   * Lista entidades a partir de un objeto parametrizado.
   *
   * @param example objeto parametrizado
   *
   * @return Lista
   */
  List<I> encontarTodos(Example<I> example);

  /**
   * Lista todos los elementos del repositorio.
   *
   * @return Lista de instancias
   */
  List<I> encontarTodos();

  /**
   * Permite buscar por specification.
   *
   * @param specification specification parametrizada
   *
   * @return resultado de la busqueda.
   */
  List<I> encontarTodos(Specification<I> specification);

  /**
   * Lista todos los elementos paginados.
   *
   * @param pageable parametro de paginacion
   *
   * @return lista paginada
   */
  Page<I> encontarTodos(Pageable pageable);

  /**
   * Eliminar elemento a partir de una lista de ids.
   *
   * @param ids lista de identificadores
   */
  void eliminar(int[] ids);

  /**
   * Eliminar de lista de elementos.
   *
   * @param iterable Lista de entidades
   */
  void eliminar(Iterable<I> iterable);

  /**
   * Eliminar a partir del id.
   *
   * @param id identificador de entidad
   */
  void eliminar(int id);

  /**
   * Actualiza la instancia y la retorna.
   *
   * @param instance instancia
   * @param id identificador
   *
   * @return instancia
   */
  I actualizar(I instance, int id);

  /**
   * Actualiza instancia y retorna DTO.
   *
   * @param dto dto de la instancia
   * @param id identificador
   *
   * @return dto
   */
  T actualizarDto(T dto, int id);

  /**
   * Obtener DTO a partir de instancia.
   *
   * @param instance instancia
   *
   * @return dto de la instancia
   */
  T obtenerDto(I instance);

  /**
   * Obtener instancia a partir del DTO.
   *
   * @param dto Recibe un Dto como parametro.
   *
   * @return instancia
   */
  I obtenerInstancia(T dto);

  /**
   * Hace acciones especiales dependiendo del parametro al momento de crear una entidad.
   *
   * @param dto Recibe un Dto como parametro.
   *
   * @return Instancia
   */
  I obtenerInstanciaParametrosEspeciales(Object dto);

  /**
   * Retorna el tipo de la clase de la instancia del servicio.
   *
   * @return tipo de la clase
   */
  Class<I> getTypeClassInstance();

  /**
   * Hace acciones especiales dependiendo del parametro al momento de crear una entidad.
   *
   * @param dto Recibe un Dto como parametro.
   *
   * @return Instancia
   */
  I obtenerInstanciaParametrosEspecialesConsulta(Object dto);
}
