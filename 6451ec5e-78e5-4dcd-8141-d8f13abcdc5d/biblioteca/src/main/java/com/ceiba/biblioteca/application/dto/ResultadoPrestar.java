package com.ceiba.biblioteca.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa el resultado exitoso de un préstamo de libro.
 *
 * Esta clase encapsula la información mínima pero esencial que se
 * retorna al cliente después de procesar exitosamente una solicitud
 * de préstamo. Contiene los datos que el usuario necesita conocer
 * inmediatamente después de realizar el préstamo.
 *
 * Implementa el patrón DTO (Data Transfer Object) para:
 * - Transferir datos entre capas de la aplicación
 * - Proporcionar una interfaz estable para respuestas
 * - Evitar exponer entidades de dominio directamente
 * - Controlar exactamente qué información se envía al cliente
 *
 * La información incluida permite al usuario:
 * - Identificar unívocamente su préstamo (ID)
 * - Conocer cuándo debe devolver el libro (fecha límite)
 * - Planificar la devolución dentro del plazo permitido
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultadoPrestar {

    /**
     * Identificador único del préstamo recién creado.
     *
     * Este ID es generado automáticamente por el sistema al persistir
     * el préstamo y permite:
     * - Identificar unívocamente el préstamo en el sistema
     * - Realizar consultas posteriores sobre el préstamo
     * - Referenciar el préstamo en futuras operaciones
     * - Proporcionar trazabilidad para auditoría
     *
     * Tipo: Entero positivo generado secuencialmente
     */
    private Integer id;

    /**
     * Fecha máxima de devolución del libro prestado.
     *
     * Representa el último día hábil en que el usuario puede devolver
     * el libro sin incurrir en mora. Esta fecha se calcula automáticamente
     * basada en:
     * - El tipo de usuario (AFILIADO: 10 días, EMPLEADO: 8 días, INVITADO: 7 días)
     * - Exclusión de fines de semana del cálculo
     * - Fecha actual como punto de partida
     *
     * Formato: "dd/MM/yyyy" (ej: "25/12/2023")
     * Ejemplo: Si un afiliado presta hoy lunes, tendrá hasta lunes de la siguiente semana
     */
    private String fechaMaximaDevolucion;
}