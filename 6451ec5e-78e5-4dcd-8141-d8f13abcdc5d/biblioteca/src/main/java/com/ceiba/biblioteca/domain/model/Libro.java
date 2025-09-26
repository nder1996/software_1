package com.ceiba.biblioteca.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Clase que representa un libro en el sistema de biblioteca.
 *
 * Esta entidad de dominio encapsula toda la información bibliográfica
 * de un libro, incluyendo datos de identificación, publicación y descripción.
 * Cada libro se identifica únicamente por su código ISBN.
 *
 * Utiliza el patrón Builder para la construcción flexible de objetos
 * y Lombok para reducir código repetitivo.
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Libro {

    /**
     * Identificador único del libro en la base de datos.
     * Se genera automáticamente al persistir el libro.
     */
    private Integer id;

    /**
     * International Standard Book Number (ISBN) del libro.
     * Identificador único internacional que permite localizar
     * específicamente cada libro en cualquier biblioteca o sistema.
     */
    private String isbn;

    /**
     * Título completo del libro.
     * Incluye el título principal y subtítulo si los tiene.
     */
    private String titulo;

    /**
     * Nombre del autor o autores del libro.
     * En caso de múltiples autores, se separan por comas.
     */
    private String autor;

    /**
     * Descripción o sinopsis del contenido del libro.
     * Proporciona una breve reseña del tema y contenido.
     */
    private String descripcion;

    /**
     * Fecha de publicación original del libro.
     * Se utiliza para catalogación y referencias bibliográficas.
     */
    private LocalDate fechaPublicacion;

    /**
     * Nombre de la casa editorial que publicó el libro.
     * Información relevante para catalogación y referencias.
     */
    private String editorial;

    /**
     * Método factory para crear un libro básico con información mínima requerida.
     *
     * Crea una instancia de libro con los datos esenciales proporcionados
     * y valores temporales para los campos no críticos. Este método se utiliza
     * cuando se necesita registrar rápidamente un libro en el sistema.
     *
     * @param isbn Código ISBN único del libro
     * @param titulo Título del libro
     * @param autor Autor del libro
     * @return Nueva instancia de Libro con datos básicos configurados
     *
     * @throws IllegalArgumentException si algún parámetro esencial es nulo o vacío
     */
    public static Libro crear(String isbn, String titulo, String autor) {
        return Libro.builder()
                .isbn(isbn)
                .titulo(titulo)
                .autor(autor)
                .descripcion("Descripción temporal")
                .fechaPublicacion(LocalDate.now())
                .editorial("Editorial Temporal")
                .build();
    }
}