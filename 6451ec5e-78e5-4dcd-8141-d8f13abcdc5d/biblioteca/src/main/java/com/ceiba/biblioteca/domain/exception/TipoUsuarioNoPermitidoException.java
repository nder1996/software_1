package com.ceiba.biblioteca.domain.exception;

/**
 * Excepción que se lanza cuando se intenta realizar una operación con un tipo de usuario inválido.
 *
 * Esta excepción se produce cuando:
 * - Se proporciona un valor numérico que no corresponde a ningún tipo de usuario válido
 * - El tipo de usuario proporcionado no está permitido en el sistema de biblioteca
 * - Se envía un valor nulo para el tipo de usuario
 *
 * Los tipos de usuario válidos son:
 * - 1: AFILIADO
 * - 2: EMPLEADO
 * - 3: INVITADO
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
public class TipoUsuarioNoPermitidoException extends DomainException {

    /**
     * Constructor que inicializa la excepción con un mensaje predefinido.
     *
     * El mensaje indica claramente que el tipo de usuario no está permitido
     * en el sistema de biblioteca.
     */
    public TipoUsuarioNoPermitidoException() {
        super("Tipo de usuario no permitido en la biblioteca");
    }
}