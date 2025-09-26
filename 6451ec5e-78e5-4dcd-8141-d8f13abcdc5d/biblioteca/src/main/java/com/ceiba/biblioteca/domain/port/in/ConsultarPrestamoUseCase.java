package com.ceiba.biblioteca.domain.port.in;

import com.ceiba.biblioteca.application.dto.ConsultaPrestamoResponse;

/**
 * Puerto de entrada para el caso de uso de consultar préstamos existentes.
 *
 * Esta interfaz define el contrato para consultar información detallada
 * de préstamos registrados en el sistema. Permite obtener todos los datos
 * relevantes de un préstamo específico utilizando su identificador único.
 *
 * Implementa el patrón Port en la arquitectura hexagonal y sigue el
 * principio de segregación de interfaces al separar las operaciones
 * de consulta (Query) de las operaciones de comando (Command).
 *
 * La información que proporciona incluye:
 * - Datos del préstamo (ID, fechas, usuario)
 * - Información del libro prestado (ISBN, título)
 * - Detalles del usuario (identificación, tipo)
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
public interface ConsultarPrestamoUseCase {

    /**
     * Consulta la información completa de un préstamo específico.
     *
     * Busca un préstamo en el sistema utilizando su identificador único
     * y retorna toda la información asociada incluyendo datos del libro
     * y del usuario que realizó el préstamo.
     *
     * La respuesta incluye:
     * - ID del préstamo
     * - ISBN y título del libro prestado
     * - Fecha máxima de devolución formateada
     * - Identificación del usuario
     * - Tipo de usuario que realizó el préstamo
     *
     * @param prestamoId Identificador único del préstamo a consultar.
     *                   Debe ser un número entero positivo que corresponda
     *                   a un préstamo existente en el sistema.
     *
     * @return Respuesta con toda la información del préstamo encontrado,
     *         incluyendo datos del libro y usuario asociados.
     *
     * @throws PrestamoNoEncontradoException si no existe un préstamo con el ID especificado
     *                                      en el sistema. Esta excepción se mapea a HTTP 404.
     */
    ConsultaPrestamoResponse ejecutar(Integer prestamoId);
}