package com.ceiba.biblioteca.domain.port.in;

import com.ceiba.biblioteca.application.dto.PrestarLibroCommand;
import com.ceiba.biblioteca.application.dto.ResultadoPrestar;

/**
 * Puerto de entrada para el caso de uso de prestar libros.
 *
 * Esta interfaz define el contrato para el caso de uso principal del sistema:
 * realizar préstamos de libros a usuarios. Encapsula toda la lógica de negocio
 * relacionada con validaciones, cálculos y persistencia de préstamos.
 *
 * Implementa el patrón Port en la arquitectura hexagonal, proporcionando
 * una abstracción que permite diferentes implementaciones sin afectar
 * el dominio.
 *
 * Las reglas de negocio que maneja incluyen:
 * - Validación de tipos de usuario permitidos
 * - Restricciones para usuarios invitados (máximo 1 libro)
 * - Cálculo de fechas de devolución según tipo de usuario
 * - Verificación de existencia de libros en el catálogo
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
public interface PrestarLibroUseCase {

    /**
     * Ejecuta el proceso de préstamo de un libro aplicando todas las reglas de negocio.
     *
     * Este método orquesta el flujo completo de préstamo:
     * 1. Valida que el tipo de usuario sea permitido en el sistema
     * 2. Verifica restricciones específicas para usuarios invitados
     * 3. Busca el libro en el catálogo por ISBN
     * 4. Calcula la fecha máxima de devolución según el tipo de usuario
     * 5. Crea y persiste el nuevo préstamo
     * 6. Retorna el resultado con información del préstamo creado
     *
     * @param command Comando que encapsula todos los datos necesarios para realizar el préstamo:
     *                - ISBN del libro a prestar
     *                - Identificación del usuario solicitante
     *                - Tipo de usuario (1=AFILIADO, 2=EMPLEADO, 3=INVITADO)
     *
     * @return Resultado del préstamo conteniendo:
     *         - ID único del préstamo generado
     *         - Fecha máxima de devolución formateada (dd/MM/yyyy)
     *
     * @throws TipoUsuarioNoPermitidoException si el tipo de usuario no es válido (1, 2, o 3)
     * @throws UsuarioConPrestamoExistenteException si el usuario invitado ya tiene un préstamo activo
     * @throws RuntimeException si el libro con el ISBN especificado no existe en el catálogo
     */
    ResultadoPrestar ejecutar(PrestarLibroCommand command);
}