package com.ceiba.biblioteca.infrastructure.adapter.out.persistence.adapter;

import com.ceiba.biblioteca.domain.model.Prestamo;
import com.ceiba.biblioteca.domain.port.out.PrestamoRepositoryPort;
import com.ceiba.biblioteca.infrastructure.adapter.out.persistence.entity.LibroJpaEntity;
import com.ceiba.biblioteca.infrastructure.adapter.out.persistence.entity.PrestamoJpaEntity;
import com.ceiba.biblioteca.infrastructure.adapter.out.persistence.mapper.PrestamoJpaMapper;
import com.ceiba.biblioteca.infrastructure.adapter.out.persistence.repository.LibroJpaRepository;
import com.ceiba.biblioteca.infrastructure.adapter.out.persistence.repository.PrestamoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PrestamoRepositoryAdapter implements PrestamoRepositoryPort {

    private final PrestamoJpaRepository jpaRepository;
    private final LibroJpaRepository libroJpaRepository;
    private final PrestamoJpaMapper mapper;

    public PrestamoRepositoryAdapter(PrestamoJpaRepository jpaRepository,
                                   LibroJpaRepository libroJpaRepository,
                                   PrestamoJpaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.libroJpaRepository = libroJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Prestamo save(Prestamo prestamo) {
        // Obtener el libro por ISBN primero
        LibroJpaEntity libroEntity = libroJpaRepository.findByIsbn(prestamo.getLibro().getIsbn())
            .orElseThrow(() -> new RuntimeException("Libro no encontrado con ISBN: " + prestamo.getLibro().getIsbn()));

        // Crear la entidad préstamo manualmente para evitar conflictos
        PrestamoJpaEntity entity = PrestamoJpaEntity.builder()
            .id(prestamo.getId()) // null para nuevos préstamos
            .fechaPrestamo(prestamo.getFechaPrestamo())
            .fechaMaximaDevolucion(prestamo.getFechaMaximaDevolucion())
            .identificacionUsuario(prestamo.getIdentificacionUsuario())
            .tipoUsuario(prestamo.getTipoUsuario().getValor())
            .libro(libroEntity) // usar la entidad existente directamente
            .build();

        PrestamoJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Prestamo> findById(Integer id) {
        return jpaRepository.findById(id)
            .map(mapper::toDomain);
    }

    @Override
    public Optional<Prestamo> findByIdentificacionUsuario(String identificacionUsuario) {
        return jpaRepository.findByIdentificacionUsuario(identificacionUsuario)
            .map(mapper::toDomain);
    }

    @Override
    public long countByIdentificacionUsuario(String identificacionUsuario) {
        return jpaRepository.countByIdentificacionUsuario(identificacionUsuario);
    }
}