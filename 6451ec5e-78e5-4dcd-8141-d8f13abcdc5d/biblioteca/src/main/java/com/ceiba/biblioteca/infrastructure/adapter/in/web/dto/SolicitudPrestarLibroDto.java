package com.ceiba.biblioteca.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para recibir solicitudes de préstamo de libros desde la capa web.
 *
 * Esta clase representa el contrato de entrada para el endpoint REST
 * que maneja solicitudes de préstamo. Define la estructura exacta del
 * JSON que el cliente debe enviar para solicitar un préstamo.
 *
 * Implementa el patrón DTO en la capa de infraestructura web para:
 * - Desacoplar la estructura de entrada de los modelos de dominio
 * - Proporcionar validación y serialización JSON específica
 * - Permitir evolucionar la API sin afectar el dominio
 * - Controlar exactamente qué campos son aceptados del cliente
 *
 * Ejemplo de JSON esperado:
 * {
 *   "isbn": "978-3-16-148410-0",
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
public class SolicitudPrestarLibroDto {

    /**
     * Código ISBN del libro que se desea prestar.
     *
     * Campo requerido que debe corresponder a un libro existente
     * en el catálogo de la biblioteca.
     *
     * Formato: Cadena alfanumérica sin restricciones de formato
     * Ejemplo: "978-3-16-148410-0"
     */
    private String isbn;

    /**
     * Documento de identificación del usuario solicitante.
     *
     * Campo mapeado explícitamente con @JsonProperty para garantizar
     * la correcta deserialización desde JSON, independientemente
     * de las convenciones de naming.
     *
     * Formato: Cadena de texto sin validaciones de formato
     * Ejemplo: "12345678", "CC12345678", "PA123456789"
     */
    @JsonProperty("identificacionUsuario")
    private String identificacionUsuario;

    /**
     * Tipo de usuario que realiza la solicitud.
     *
     * Valor numérico que determina las reglas de préstamo aplicables:
     * - 1: AFILIADO (10 días, sin restricciones)
     * - 2: EMPLEADO (8 días, privilegios especiales)
     * - 3: INVITADO (7 días, máximo 1 libro)
     *
     * La validación de valores permitidos se realiza en el dominio.
     */
    private Integer tipoUsuario;
}