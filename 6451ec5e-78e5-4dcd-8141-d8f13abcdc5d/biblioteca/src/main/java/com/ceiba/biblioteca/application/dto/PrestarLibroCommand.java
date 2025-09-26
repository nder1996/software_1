package com.ceiba.biblioteca.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Comando que encapsula los datos necesarios para ejecutar el caso de uso de prestar un libro.
 *
 * Este DTO implementa el patrón Command, encapsulando toda la información
 * requerida para procesar una solicitud de préstamo de libro. Actúa como
 * un contenedor inmutable de datos que viaja desde la capa de aplicación
 * hacia el dominio.
 *
 * El patrón Command permite:
 * - Encapsular una solicitud como un objeto
 * - Parametrizar objetos con diferentes solicitudes
 * - Facilitar el logging, queuing y undo de operaciones
 * - Separar el objeto que invoca la operación del que la ejecuta
 *
 * Los datos incluidos son los mínimos necesarios para:
 * - Identificar el libro a prestar (ISBN)
 * - Identificar al usuario solicitante
 * - Determinar el tipo de usuario para aplicar reglas de negocio
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestarLibroCommand {

    /**
     * Código ISBN (International Standard Book Number) del libro a prestar.
     *
     * Identificador único internacional que permite localizar específicamente
     * el libro en el catálogo de la biblioteca. Debe corresponder a un libro
     * existente en el sistema.
     *
     * Formato esperado: Cadena alfanumérica que cumple estándar ISBN-10 o ISBN-13
     */
    private String isbn;

    /**
     * Documento de identificación del usuario que solicita el préstamo.
     *
     * Puede ser cédula de ciudadanía, pasaporte, cédula de extranjería u otro
     * documento válido de identificación. Se utiliza para:
     * - Asociar el préstamo con el usuario
     * - Validar restricciones específicas (ej: usuarios invitados)
     * - Realizar consultas de préstamos existentes
     *
     * Formato: Cadena de texto sin formato específico
     */
    private String identificacionUsuario;

    /**
     * Tipo de usuario que realiza la solicitud de préstamo.
     *
     * Determina las reglas de negocio aplicables:
     * - 1: AFILIADO (10 días de préstamo, sin restricciones de cantidad)
     * - 2: EMPLEADO (8 días de préstamo, privilegios especiales)
     * - 3: INVITADO (7 días de préstamo, máximo 1 libro simultáneo)
     *
     * Este valor se valida contra los tipos permitidos en el sistema
     * y determina el cálculo de la fecha de devolución.
     */
    private Integer tipoUsuario;
}