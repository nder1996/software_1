package com.ceiba.biblioteca.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para retornar información completa de un préstamo desde la API REST.
 *
 * Esta clase define la estructura de la respuesta JSON para consultas
 * de préstamos existentes. Proporciona una vista consolidada que combina
 * información del préstamo, libro y usuario en una sola respuesta.
 *
 * Implementa el patrón DTO en la capa de infraestructura web para:
 * - Estructurar respuestas JSON complejas de manera consistente
 * - Evitar múltiples llamadas para obtener datos relacionados
 * - Controlar exactamente qué información se expone en la API
 * - Facilitar el consumo de datos en aplicaciones cliente
 *
 * Ejemplo de respuesta JSON generada:
 * {
 *   "id": 123,
 *   "isbn": "978-3-16-148410-0",
 *   "titulo": "El Señor de los Anillos",
 *   "fechaMaximaDevolucion": "15/01/2024",
 *   "identificacionUsuario": "12345678",
 *   "tipoUsuario": 1
 * }
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultaPrestamoResponseDto {

    /**
     * Identificador único del préstamo.
     *
     * Número secuencial que identifica unívocamente
     * el préstamo en el sistema.
     */
    private Integer id;

    /**
     * Código ISBN del libro prestado.
     *
     * Identificador internacional estándar que permite
     * localizar el libro en cualquier catálogo bibliográfico.
     */
    private String isbn;

    /**
     * Título completo del libro prestado.
     *
     * Nombre oficial del libro incluyendo título principal
     * y subtítulo si corresponde.
     */
    private String titulo;

    /**
     * Fecha límite para la devolución del libro.
     *
     * Último día hábil permitido para devolver el libro
     * sin incurrir en penalizaciones.
     *
     * Formato: "dd/MM/yyyy"
     */
    private String fechaMaximaDevolucion;

    /**
     * Documento de identificación del usuario que realizó el préstamo.
     *
     * Campo mapeado explícitamente con @JsonProperty para
     * garantizar la correcta serialización JSON.
     */
    @JsonProperty("identificacionUsuario")
    private String identificacionUsuario;

    /**
     * Tipo de usuario que realizó el préstamo.
     *
     * Valor numérico que indica la categoría:
     * - 1: AFILIADO
     * - 2: EMPLEADO
     * - 3: INVITADO
     */
    private Integer tipoUsuario;
}