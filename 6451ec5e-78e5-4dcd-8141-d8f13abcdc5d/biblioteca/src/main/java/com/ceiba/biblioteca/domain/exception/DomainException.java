package com.ceiba.biblioteca.domain.exception;

/**
 * Clase abstracta base para todas las excepciones de dominio del sistema de biblioteca.
 *
 * Esta clase representa la jerarquía raíz de excepciones específicas del dominio,
 * proporcionando un mecanismo unificado para manejar errores relacionados con
 * reglas de negocio y validaciones del dominio.
 *
 * Extiende RuntimeException para permitir el manejo de excepciones no chequeadas,
 * lo cual es apropiado para errores de dominio que representan violaciones
 * de reglas de negocio.
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
public abstract class DomainException extends RuntimeException {

    /**
     * Constructor que inicializa la excepción con un mensaje descriptivo.
     *
     * @param message Mensaje que describe la causa específica del error de dominio
     */
    public DomainException(String message) {
        super(message);
    }
}