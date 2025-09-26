package com.ceiba.biblioteca.infrastructure.adapter.out.persistence.mapper;

import com.ceiba.biblioteca.domain.model.Prestamo;
import com.ceiba.biblioteca.domain.model.TipoUsuario;
import com.ceiba.biblioteca.infrastructure.adapter.out.persistence.entity.LibroJpaEntity;
import com.ceiba.biblioteca.infrastructure.adapter.out.persistence.entity.PrestamoJpaEntity;
import com.ceiba.biblioteca.infrastructure.adapter.out.persistence.repository.LibroJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class PrestamoJpaMapper {

    private final LibroJpaMapper libroMapper;
    private final LibroJpaRepository libroRepository;

    public PrestamoJpaMapper(LibroJpaMapper libroMapper, LibroJpaRepository libroRepository) {
        this.libroMapper = libroMapper;
        this.libroRepository = libroRepository;
    }

    public PrestamoJpaEntity toEntity(Prestamo prestamo) {
        LibroJpaEntity libroEntity = libroRepository.findByIsbn(prestamo.getLibro().getIsbn())
            .orElseThrow(() -> new RuntimeException("Libro no encontrado con ISBN: " + prestamo.getLibro().getIsbn()));

        return PrestamoJpaEntity.builder()
            .id(prestamo.getId())
            .fechaPrestamo(prestamo.getFechaPrestamo())
            .fechaMaximaDevolucion(prestamo.getFechaMaximaDevolucion())
            .identificacionUsuario(prestamo.getIdentificacionUsuario())
            .tipoUsuario(prestamo.getTipoUsuario() != null ? prestamo.getTipoUsuario().getValor() : null)
            .libro(libroEntity)
            .build();
    }

    public Prestamo toDomain(PrestamoJpaEntity entity) {
        return Prestamo.builder()
            .id(entity.getId())
            .fechaPrestamo(entity.getFechaPrestamo())
            .fechaMaximaDevolucion(entity.getFechaMaximaDevolucion())
            .identificacionUsuario(entity.getIdentificacionUsuario())
            .tipoUsuario(entity.getTipoUsuario() != null ? TipoUsuario.fromValor(entity.getTipoUsuario()) : null)
            .libro(libroMapper.toDomain(entity.getLibro()))
            .build();
    }
}