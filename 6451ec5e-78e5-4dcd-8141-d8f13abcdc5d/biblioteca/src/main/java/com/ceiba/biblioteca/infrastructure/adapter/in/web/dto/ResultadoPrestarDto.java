package com.ceiba.biblioteca.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para retornar el resultado de un préstamo exitoso desde la API REST.
 *
 * Esta clase define la estructura de la respuesta JSON que se envía
 * al cliente cuando un préstamo se procesa exitosamente. Proporciona
 * la información esencial que el usuario necesita conocer inmediatamente.
 *
 * Implementa el patrón DTO en la capa de infraestructura web para:
 * - Controlar la estructura exacta de la respuesta JSON
 * - Desacoplar las respuestas de los DTOs de aplicación
 * - Permitir versionado independiente de la API
 * - Facilitar la serialización JSON con Jackson
 *
 * Ejemplo de respuesta JSON generada:
 * {
 *   "id": 123,
 *   "fechaMaximaDevolucion": "15/01/2024"
 * }
 *
 * Esta respuesta permite al cliente:
 * - Confirmar que el préstamo fue creado exitosamente
 * - Obtener el ID para futuras consultas
 * - Conocer la fecha límite de devolución
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultadoPrestarDto {

    /**
     * Identificador único del préstamo creado.
     *
     * Número secuencial generado por el sistema que permite
     * al cliente referenciar este préstamo en operaciones futuras
     * como consultas o devoluciones.
     *
     * Tipo: Entero positivo
     * Ejemplo: 123, 4567, 89012
     */
    private Integer id;

    /**
     * Fecha máxima para devolver el libro prestado.
     *
     * Fecha calculada automáticamente según el tipo de usuario
     * y las reglas de negocio, excluyendo fines de semana.
     * Indica cuándo el usuario debe devolver el libro para
     * evitar penalizaciones.
     *
     * Formato: "dd/MM/yyyy"
     * Ejemplo: "15/01/2024", "03/12/2023"
     */
    private String fechaMaximaDevolucion;
}