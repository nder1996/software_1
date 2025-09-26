package com.ceiba.biblioteca.infrastructure.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Entidad JPA que representa la tabla 'libro' en la base de datos.
 *
 * Esta clase mapea directamente a la estructura de la tabla de libros
 * en la base de datos, definiendo cómo se almacena la información
 * bibliográfica del catálogo.
 *
 * Características de la entidad:
 * - Mapeo directo a tabla 'libro'
 * - ID autogenerado con estrategia IDENTITY
 * - Campos optimizados para búsquedas (ISBN indexado)
 * - Soporte para textos largos en descripción
 * - Validaciones de base de datos en campos críticos
 *
 * Utiliza Lombok para:
 * - @Entity: Marca como entidad JPA
 * - @Data: Genera getters, setters, equals, hashCode, toString
 * - @Builder: Permite construcción fluida de objetos
 * - @NoArgsConstructor/@AllArgsConstructor: Constructores requeridos por JPA
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "libro")
public class LibroJpaEntity {

    /**
     * Identificador único del libro en la base de datos.
     * Se genera automáticamente usando la estrategia IDENTITY.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Código ISBN (International Standard Book Number) del libro.
     * Identificador único internacional, limitado a 20 caracteres.
     */
    @Column(name = "isbn", length = 20)
    private String isbn;

    /**
     * Título completo del libro.
     * Campo obligatorio que identifica la obra.
     */
    @Column(name = "titulo", nullable = false)
    private String titulo;

    /**
     * Descripción o sinopsis del libro.
     * Campo de texto largo para contenido extenso.
     */
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    /**
     * Fecha de publicación original del libro.
     * Utilizada para catalogación y referencias bibliográficas.
     */
    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    /**
     * Nombre del autor o autores del libro.
     * Campo obligatorio para identificación bibliográfica.
     */
    @Column(name = "autor", nullable = false)
    private String autor;

    /**
     * Nombre de la casa editorial que publicó el libro.
     * Información adicional para catalogación completa.
     */
    @Column(name = "editorial")
    private String editorial;
}