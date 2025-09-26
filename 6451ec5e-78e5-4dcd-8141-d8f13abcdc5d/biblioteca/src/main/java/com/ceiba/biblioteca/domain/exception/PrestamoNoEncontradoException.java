package com.ceiba.biblioteca.domain.exception;

/**
 * Excepción que se lanza cuando se intenta acceder a un préstamo que no existe en el sistema.
 *
 * Esta excepción se produce cuando:
 * - Se busca un préstamo por ID y no se encuentra en la base de datos
 * - Se intenta consultar información de un préstamo con un identificador inválido
 * - El préstamo fue eliminado o nunca fue creado
 *
 * Generalmente se maneja devolviendo un error HTTP 404 (Not Found) al cliente,
 * indicando que el recurso solicitado no existe.
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
public class PrestamoNoEncontradoException extends DomainException {

    /**
     * Constructor que inicializa la excepción con un mensaje personalizado.
     *
     * El mensaje incluye el ID del préstamo que se intentó buscar para
     * facilitar la identificación del problema y el debugging.
     *
     * @param prestamoId Identificador del préstamo que no fue encontrado
     */
    public PrestamoNoEncontradoException(Integer prestamoId) {
        super("El préstamo con ID " + prestamoId + " no existe");
    }
}