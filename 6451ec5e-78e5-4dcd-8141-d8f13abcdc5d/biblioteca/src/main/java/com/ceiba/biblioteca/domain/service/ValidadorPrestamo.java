package com.ceiba.biblioteca.domain.service;

import com.ceiba.biblioteca.domain.exception.TipoUsuarioNoPermitidoException;
import com.ceiba.biblioteca.domain.exception.UsuarioConPrestamoExistenteException;
import com.ceiba.biblioteca.domain.model.TipoUsuario;
import com.ceiba.biblioteca.domain.port.out.PrestamoRepositoryPort;

/**
 * Servicio de dominio responsable de validar las reglas de negocio para préstamos de libros.
 *
 * Este servicio encapsula todas las validaciones relacionadas con los préstamos,
 * incluyendo la verificación de tipos de usuario válidos y las restricciones
 * específicas para usuarios invitados.
 *
 * Implementa el patrón Domain Service para mantener las reglas de negocio
 * separadas de los modelos de dominio y centralizadas en un solo lugar.
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
public class ValidadorPrestamo {

    /**
     * Puerto de salida para acceder al repositorio de préstamos.
     * Se utiliza para consultar préstamos existentes y aplicar validaciones.
     */
    private final PrestamoRepositoryPort prestamoRepository;

    /**
     * Constructor que inicializa el validador con sus dependencias.
     *
     * @param prestamoRepository Repositorio para consultas de préstamos existentes
     */
    public ValidadorPrestamo(PrestamoRepositoryPort prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    /**
     * Valida que el tipo de usuario proporcionado sea válido según las reglas del sistema.
     *
     * Verifica que el valor numérico corresponda a uno de los tipos de usuario
     * permitidos en el sistema (AFILIADO, EMPLEADO, INVITADO).
     *
     * @param tipoUsuarioValor Valor numérico del tipo de usuario a validar
     *
     * @throws TipoUsuarioNoPermitidoException si el tipo de usuario no es válido
     *         o no está permitido en el sistema
     */
    public void validarTipoUsuario(Integer tipoUsuarioValor) {
        try {
            TipoUsuario.fromValor(tipoUsuarioValor);
        } catch (IllegalArgumentException e) {
            throw new TipoUsuarioNoPermitidoException();
        }
    }

    /**
     * Valida las restricciones específicas para usuarios invitados.
     *
     * Los usuarios invitados tienen la restricción de solo poder tener
     * un libro prestado a la vez. Este método verifica que un usuario
     * invitado no tenga préstamos activos antes de permitir un nuevo préstamo.
     *
     * Para otros tipos de usuario (AFILIADO, EMPLEADO), no aplica esta restricción.
     *
     * @param identificacionUsuario Documento de identificación del usuario
     * @param tipoUsuario Tipo de usuario que solicita el préstamo
     *
     * @throws UsuarioConPrestamoExistenteException si el usuario invitado ya tiene
     *         un préstamo activo y no puede tener uno adicional
     */
    public void validarUsuarioInvitado(String identificacionUsuario, TipoUsuario tipoUsuario) {
        if (tipoUsuario.esInvitado()) {
            long prestamosActivos = prestamoRepository.countByIdentificacionUsuario(identificacionUsuario);
            if (prestamosActivos > 0) {
                throw new UsuarioConPrestamoExistenteException(identificacionUsuario);
            }
        }
    }
}