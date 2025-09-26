package com.ceiba.biblioteca.infrastructure.adapter.out.persistence.adapter;

import com.ceiba.biblioteca.domain.model.Libro;
import com.ceiba.biblioteca.domain.port.out.LibroRepositoryPort;
import com.ceiba.biblioteca.infrastructure.adapter.out.persistence.entity.LibroJpaEntity;
import com.ceiba.biblioteca.infrastructure.adapter.out.persistence.mapper.LibroJpaMapper;
import com.ceiba.biblioteca.infrastructure.adapter.out.persistence.repository.LibroJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Adaptador de persistencia para el catálogo de libros.
 *
 * Esta clase implementa el puerto de salida LibroRepositoryPort,
 * proporcionando acceso a datos de libros utilizando JPA como
 * tecnología de persistencia.
 *
 * Implementa el patrón Adapter de la arquitectura hexagonal para:
 * - Convertir entre objetos de dominio y entidades JPA
 * - Aislar el dominio de detalles específicos de JPA/Hibernate
 * - Permitir cambio de tecnología de persistencia sin afectar dominio
 * - Proporcionar una implementación concreta del puerto abstracto
 *
 * Responsabilidades:
 * - Gestionar la persistencia del catálogo de libros
 * - Realizar búsquedas de libros por ISBN
 * - Coordinar transformaciones entre modelos de dominio y entidades JPA
 * - Delegar operaciones CRUD al repositorio JPA
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
@Repository
public class LibroRepositoryAdapter implements LibroRepositoryPort {

    /**
     * Repositorio JPA para operaciones CRUD sobre entidades LibroJpaEntity.
     * Proporciona métodos estándar y consultas personalizadas.
     */
    private final LibroJpaRepository jpaRepository;

    /**
     * Mapper para transformaciones entre objetos de dominio y entidades JPA.
     * Facilita la conversión bidireccional manteniendo separación de capas.
     */
    private final LibroJpaMapper mapper;

    /**
     * Constructor que inicializa el adaptador con sus dependencias.
     *
     * @param jpaRepository Repositorio JPA para acceso a datos de libros
     * @param mapper Transformador entre modelos de dominio y entidades JPA
     */
    public LibroRepositoryAdapter(LibroJpaRepository jpaRepository, LibroJpaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    /**
     * Persiste un libro en la base de datos.
     *
     * Convierte el modelo de dominio a entidad JPA, lo persiste
     * utilizando el repositorio y retorna el resultado convertido
     * de vuelta al modelo de dominio.
     *
     * @param libro Modelo de dominio del libro a persistir
     * @return Libro persistido con ID asignado y datos actualizados
     */
    @Override
    public Libro save(Libro libro) {
        LibroJpaEntity entity = mapper.toEntity(libro);
        LibroJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    /**
     * Busca un libro en el catálogo por su código ISBN.
     *
     * Utiliza el repositorio JPA para realizar la búsqueda y
     * convierte el resultado a modelo de dominio si existe.
     *
     * @param isbn Código ISBN del libro a buscar
     * @return Optional conteniendo el libro si existe, empty() si no se encuentra
     */
    @Override
    public Optional<Libro> findByIsbn(String isbn) {
        return jpaRepository.findByIsbn(isbn)
            .map(mapper::toDomain);
    }
}