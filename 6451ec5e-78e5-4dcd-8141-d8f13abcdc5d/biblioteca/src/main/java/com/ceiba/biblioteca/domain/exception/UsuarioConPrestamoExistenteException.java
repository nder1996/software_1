package com.ceiba.biblioteca.domain.exception;

/**
 * Excepción que se lanza cuando un usuario invitado intenta solicitar un préstamo
 * teniendo ya un libro prestado actualmente.
 *
 * Esta excepción implementa la regla de negocio que establece que los usuarios
 * de tipo INVITADO solo pueden tener un libro prestado a la vez. Si un usuario
 * invitado ya tiene un préstamo activo, no puede solicitar otro hasta devolver
 * el libro actual.
 *
 * Esta restricción no aplica para usuarios AFILIADOS o EMPLEADOS, quienes
 * pueden tener múltiples préstamos simultáneamente.
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
public class UsuarioConPrestamoExistenteException extends DomainException {

    /**
     * Constructor que inicializa la excepción con un mensaje personalizado.
     *
     * El mensaje incluye la identificación del usuario para facilitar
     * la identificación del problema y proporcionar información útil
     * para la resolución del conflicto.
     *
     * @param identificacionUsuario Documento de identificación del usuario
     *                              que ya tiene un préstamo activo
     */
    public UsuarioConPrestamoExistenteException(String identificacionUsuario) {
        super("El usuario con identificación " + identificacionUsuario +
              " ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo");
    }
}