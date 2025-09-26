package com.ceiba.biblioteca.infrastructure.adapter.out.persistence.mapper;

import com.ceiba.biblioteca.domain.model.Libro;
import com.ceiba.biblioteca.infrastructure.adapter.out.persistence.entity.LibroJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class LibroJpaMapper {

    public LibroJpaEntity toEntity(Libro libro) {
        return LibroJpaEntity.builder()
            .id(libro.getId())
            .isbn(libro.getIsbn())
            .titulo(libro.getTitulo())
            .autor(libro.getAutor())
            .descripcion(libro.getDescripcion())
            .fechaPublicacion(libro.getFechaPublicacion())
            .editorial(libro.getEditorial())
            .build();
    }

    public Libro toDomain(LibroJpaEntity entity) {
        return Libro.builder()
            .id(entity.getId())
            .isbn(entity.getIsbn())
            .titulo(entity.getTitulo())
            .autor(entity.getAutor())
            .descripcion(entity.getDescripcion())
            .fechaPublicacion(entity.getFechaPublicacion())
            .editorial(entity.getEditorial())
            .build();
    }
}