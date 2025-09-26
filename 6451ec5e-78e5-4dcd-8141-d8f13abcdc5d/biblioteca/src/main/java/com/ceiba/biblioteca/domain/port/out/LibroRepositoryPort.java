package com.ceiba.biblioteca.domain.port.out;

import com.ceiba.biblioteca.domain.model.Libro;

import java.util.Optional;

/**
 * Puerto de salida para el acceso a datos del catálogo de libros.
 *
 * Esta interfaz define el contrato para las operaciones de persistencia
 * relacionadas con el catálogo de libros de la biblioteca. Abstrae el
 * acceso a datos y permite diferentes implementaciones sin afectar el dominio.
 *
 * Implementa el patrón Repository y el patrón Port de la arquitectura
 * hexagonal, manteniendo la independencia del dominio respecto a la
 * infraestructura de persistencia.
 *
 * El catálogo de libros es fundamental para:
 * - Validar que los libros existen antes de prestarlos
 * - Mantener información bibliográfica actualizada
 * - Permitir búsquedas eficientes por ISBN
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
public interface LibroRepositoryPort {

    /**
     * Persiste un libro nuevo o actualiza uno existente en el catálogo.
     *
     * Esta operación permite agregar nuevos libros al catálogo o
     * actualizar la información bibliográfica de libros existentes.
     * Si el libro no tiene ID, se creará un nuevo registro.
     *
     * @param libro Entidad de dominio del libro a persistir.
     *              Debe contener al menos ISBN, título y autor.
     *              Otros campos como descripción y editorial son opcionales.
     *
     * @return El libro persistido con su ID asignado y cualquier
     *         campo generado o modificado durante la persistencia.
     *
     * @throws RuntimeException si ocurre un error durante la persistencia
     *                         o si ya existe un libro con el mismo ISBN.
     */
    Libro save(Libro libro);

    /**
     * Busca un libro en el catálogo utilizando su código ISBN.
     *
     * El ISBN (International Standard Book Number) es el identificador
     * único universal de libros. Esta operación es esencial para
     * validar que un libro existe antes de permitir su préstamo.
     *
     * Se utiliza principalmente en el proceso de préstamo para:
     * - Verificar que el libro solicitado existe en el catálogo
     * - Obtener información completa del libro para el préstamo
     * - Validar datos antes de crear el registro de préstamo
     *
     * @param isbn Código ISBN del libro a buscar.
     *             Debe ser una cadena válida que siga el formato
     *             estándar ISBN-10 o ISBN-13.
     *
     * @return Optional conteniendo el libro si se encuentra en el catálogo,
     *         o Optional.empty() si no existe un libro con ese ISBN.
     */
    Optional<Libro> findByIsbn(String isbn);
}