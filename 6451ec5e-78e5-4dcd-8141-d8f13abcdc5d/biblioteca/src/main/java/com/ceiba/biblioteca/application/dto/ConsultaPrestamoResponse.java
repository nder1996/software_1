package com.ceiba.biblioteca.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa la respuesta completa de una consulta de préstamo.
 *
 * Esta clase encapsula toda la información relevante de un préstamo
 * existente, combinando datos del préstamo, del libro y del usuario
 * en una sola respuesta cohesiva.
 *
 * Implementa el patrón DTO y Response Object para:
 * - Proporcionar una vista consolidada de información de múltiples entidades
 * - Evitar múltiples llamadas al backend para obtener datos relacionados
 * - Controlar la estructura y formato de la respuesta
 * - Facilitar el consumo de datos en el frontend
 *
 * La información consolidada incluye:
 * - Identificación del préstamo y fechas importantes
 * - Detalles bibliográficos del libro prestado
 * - Información del usuario que realizó el préstamo
 *
 * Esta respuesta es especialmente útil para:
 * - Pantallas de detalle de préstamo
 * - Reportes y consultas administrativas
 * - Verificación de estado de préstamos
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultaPrestamoResponse {

    /**
     * Identificador único del préstamo en el sistema.
     *
     * Número secuencial generado automáticamente que permite
     * identificar unívocamente cada préstamo registrado.
     */
    private Integer id;

    /**
     * Código ISBN del libro prestado.
     *
     * Identificador internacional estándar del libro que permite
     * localizarlo en cualquier catálogo bibliográfico.
     */
    private String isbn;

    /**
     * Título completo del libro prestado.
     *
     * Nombre oficial del libro incluyendo título principal y
     * subtítulo si los tiene. Información clave para identificación
     * del material prestado.
     */
    private String titulo;

    /**
     * Fecha máxima de devolución del préstamo.
     *
     * Último día hábil permitido para devolver el libro sin
     * incurrir en mora. Calculada según el tipo de usuario y
     * excluyendo fines de semana.
     *
     * Formato: "dd/MM/yyyy"
     */
    private String fechaMaximaDevolucion;

    /**
     * Documento de identificación del usuario que realizó el préstamo.
     *
     * Cédula, pasaporte u otro documento oficial que identifica
     * al usuario responsable del préstamo.
     */
    private String identificacionUsuario;

    /**
     * Tipo de usuario que realizó el préstamo.
     *
     * Valor numérico que indica la categoría del usuario:
     * - 1: AFILIADO (miembro regular con privilegios completos)
     * - 2: EMPLEADO (personal de la institución)
     * - 3: INVITADO (usuario temporal con restricciones)
     *
     * Este valor determina las reglas que se aplicaron al préstamo.
     */
    private Integer tipoUsuario;
}