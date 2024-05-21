package com.proyectoci.proyectoci.services.impl;



import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.proyectoci.proyectoci.models.Dominio;
import com.proyectoci.proyectoci.repository.CustomCrudRepository;
import com.proyectoci.proyectoci.services.CrudService;
import jakarta.transaction.Transactional;

import org.apache.commons.beanutils.PropertyUtils;
import org.modelmapper.AbstractConverter;
import org.modelmapper.AbstractProvider;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * creada por: Clase generica encargada de implementar las funciones generales entre
 * las entidades y trasformacion de tipos de datos.
 *
 * @param <T> DTO para transformar el objeto
 * @param <R> Repositorio de la clase entidad
 * @param <I> Instancia recibida por sub herencia
 */
@Transactional
public class CrudServiceImpl<I extends Dominio, R extends CustomCrudRepository<I>, T>
        implements CrudService<I, T> {


    private static final Provider<LocalDateTime> localDateProvider =
            new AbstractProvider<LocalDateTime>() {
                @Override
                public LocalDateTime get() {
                    return LocalDateTime.now();
                }
            };


    private static ModelMapper modelMapper;
    private final Class<I> typeClassInstance;
    private final Class<T> typeClassDto;
    protected R repository;
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Recibe repositorio y tipos de clases por parametros.
     *
     * @param repository Repositorio
     * @param typeClassInstance Tipo de clase de la entidad
     * @param typeClassDto Tipo de clase del Dto
     */
    public CrudServiceImpl(R repository, Class<I> typeClassInstance, Class<T> typeClassDto) {
        // Se crea mapeador para fechas
        instanciarMapper();
        this.repository = repository;
        this.typeClassInstance = typeClassInstance;
        this.typeClassDto = typeClassDto;
    }

    private static void instanciarMapper() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
            modelMapper.createTypeMap(String.class, LocalDateTime.class);
            modelMapper.getTypeMap(String.class, LocalDateTime.class).setProvider(localDateProvider);
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
        }
    }

    public static ModelMapper getModelMapper() {
        return modelMapper;
    }

    /**
     * Guarda la instancia y retorna el Dto para responder en rest.
     *
     * @param dto dto
     *
     * @return dto persistida
     */
    @Override
    public T guardarDto(T dto) {
        try {
            I instance = obtenerInstanciaParametrosEspeciales(dto);
            instance.setDisponible(true);
            instance.setVisible(true);
            instance.setFechaCreacion(LocalDateTime.now());
            if (instance.getFechaCreacion() == null) {
                instance.setUltimaActualizacion(LocalDateTime.now());
            }
            return obtenerDto(repository.save(instance));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getCause().getCause().getMessage());
        }
    }

    /**
     * Guarda la instancia y retorna el objeto, para transacciones internas.
     *
     * @param instance instancia
     *
     * @return T obtejo
     */
    @Override
    public I guardar(I instance) {
        I instanciaTemporal = null;
        try {
            instance.setDisponible(true);
            instance.setVisible(true);
            if (instance.getFechaCreacion() == null) {
                instance.setFechaCreacion(LocalDateTime.now());
            }
            instance.setUltimaActualizacion(LocalDateTime.now());
            log.debug("{}", instance);
            log.info("entidad save: {}", instance);
            instanciaTemporal = repository.save(instance);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return instanciaTemporal;
    }

    @Override
    public List<I> guardar(List<I> instances) {
        List<I> instancias = new ArrayList<>();
        try {
            log.debug("{}", instances);
            instancias = repository.saveAll(instances);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return instancias;
    }

    /**
     * Consulta y obtiene la instancia y retorna el DTO para responder en rest.
     *
     * @param id id de instancia
     *
     * @return I Objeto Dto
     */
    @Override
    public T buscatDtoPorId(int id) {
        try {
            log.debug("{}", id);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return obtenerDto(repository.getReferenceById(id));
    }

    /**
     * Consulta, Obtiene y retorna el objeto entidad, para transacciones internas.
     *
     * @param id id de instancia
     *
     * @return T obtejo Entidad
     */
    @Override
    public I buscarPorId(int id) {
        return repository.getReferenceById(id);
    }

    /**
     * Permite buscar por specification. de manera paginada.
     *
     * @param specification specification parametrizada
     * @param pageable parametro de paginacion
     *
     * @return resultado de la busqueda.
     */
    @Override
    public Page<I> encontarTodosPage(Specification<I> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    @Override
    public List<T> encontarTodosDto() {
        return repository.findAll().stream().map(this::obtenerDto).collect(Collectors.toList());
    }

    @Override
    public List<T> encontarTodosDto(Example<I> example) {
        return repository.findAll(example).stream().map(this::obtenerDto).collect(Collectors.toList());
    }

    /**
     * Retorna una lista paginable de objetos de tipo DTO.
     *
     * @param pageable paginador
     *
     * @return lista paginada
     */
    @Override
    public Page<T> encontarTodosDto(Pageable pageable) {
        Pageable pageable2 = PageRequest.of(0, 100000);
        return repository.findAll(pageable2).map(this::obtenerDto);
    }

    @Override
    public Page<T> encontarTodosDto(Example<I> example, Pageable pageable) {
        Pageable pageable2 = PageRequest.of(0, 100000);
        return repository.findAll(example, pageable2).map(this::obtenerDto);
    }

    @Override
    public Page<I> encontarTodos(Pageable pageable) {
        Pageable pageable2 = PageRequest.of(0, 100000);
        return repository.findAll(pageable2);
    }

    /**
     * Lista todas las entidades paginadas.
     *
     * @param example objeto parametrizado
     * @param pageable parametro de paginacion.
     *
     * @return lista paginada.
     */
    @Override
    public Page<I> encontarTodos(Example<I> example, Pageable pageable) {
        Pageable pageable2 = PageRequest.of(0, 100000);
        return repository.findAll(example, pageable2);
    }

    @Override
    public List<I> encontarTodos(Example<I> example) {
        return repository.findAll(example);
    }

    /**
     * Retorna todos en su objeto original para uso de persistencia.
     *
     * @return lista de instancias
     */
    @Override
    public List<I> encontarTodos() {
        return repository.findAll();
    }

    /**
     * Permite buscar por specification.
     *
     * @param specification specification parametrizada
     *
     * @return resultado de la busqueda.
     */
    @Override
    public List<I> encontarTodos(Specification<I> specification) {
        return repository.findAll(specification);
    }

    @Override
    public void eliminar(Iterable<I> iterable) {
        repository.deleteAll(iterable);
    }

    @Override
    public void eliminar(int[] ids) {
        try {
            List<I> list = new ArrayList<>();
            for (int id : ids) {
                I instance = repository.getReferenceById(id);
                list.add(instance);
            }
            repository.deleteAll(list);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void eliminar(int ids) {
        try {
            repository.deleteById(ids);
        } catch (Exception e) {
            log.info("-> Error Deleting {} id: ", ids);
        }
    }

    @Override
    public T actualizarDto(T dto, int id) {
        return obtenerDto(actualizar(obtenerInstancia(dto), id));
    }

    @Override
    public I actualizar(I instance, int id) {
        I instancia = repository.getReferenceById(id);
        try {
            PropertyUtils.describe(instance).entrySet().stream().filter(Objects::nonNull)
                    .filter(e -> !e.getKey().equals("class")).filter(e -> !e.getKey().equals("id"))
                    .filter(e -> !e.getKey().equals("fechaCreacion"))
                    .filter(e -> !e.getKey().equals("handler"))
                    .filter(e -> !e.getKey().equals("hibernateLazyInitializer")).forEach(e -> {
                        try {
                            if (e.getValue() != null) {
                                PropertyUtils.setProperty(instancia, e.getKey(), e.getValue());
                            }
                        } catch (Exception ex) {
                            log.error(ex.getMessage());
                        }
                    });
        } catch (Exception e) {
            log.error("-> Error updating id: {}", id);
        }
        if (instancia.getId() != null) {
            return repository.save(instancia);
        }
        return instancia;
    }

    /**
     * Obtener DTO a partir de instancia.
     *
     * @param instance instancia
     *
     * @return dto
     */
    @Override
    public T obtenerDto(I instance) {
        if (instance != null) {
            return modelMapper.map(instance, typeClassDto);
        }
        return modelMapper.map(new Object(), typeClassDto);
    }

    /**
     * Obtener instancia a partir del DTO.
     *
     * @param dto objeto de transformacion
     *
     * @return instancia
     */
    @Override
    public I obtenerInstancia(T dto) {
        if (dto != null) {
            return modelMapper.map(dto, typeClassInstance);
        }
        return modelMapper.map(new Object(), typeClassInstance);
    }

    /**
     * Convierte el dto en una entidad pero agrega el usuario que este logueado.
     *
     * @param dto Recibe un Dto como parametro.
     *
     * @return entidad
     */
    @Override
    public I obtenerInstanciaParametrosEspeciales(Object dto) {
        if (dto != null) {
            try {
                PropertyUtils.describe(dto).entrySet().stream().filter(e -> e.getKey().equals("usuarioId"))
                        .forEach(e -> {
                            try {
                                if ((e.getValue() == null || e.getValue().equals(0)) && MDC.get("userId") != null) {
                                    PropertyUtils.setProperty(dto, e.getKey(), Integer.parseInt(MDC.get("userId")));
                                }
                            } catch (Exception ex) {
                                log.error(ex.getMessage());
                            }
                        });
            } catch (Exception e) {
                log.error("-> Error updating {}", dto);
            }
            return modelMapper.map(dto, typeClassInstance);
        }
        return modelMapper.map(new Object(), typeClassInstance);
    }

    /**
     * Convierte el dto en una entidad pero agrega el usuario que este logueado.
     *
     * @param dto Recibe un Dto como parametro.
     *
     * @return entidad
     */
    @Override
    public I obtenerInstanciaParametrosEspecialesConsulta(Object dto) {
        if (dto != null) {
            return modelMapper.map(dto, typeClassInstance);
        }
        return modelMapper.map(new Object(), typeClassInstance);
    }

    public Class<I> getTypeClassInstance() {
        return typeClassInstance;
    }
}
