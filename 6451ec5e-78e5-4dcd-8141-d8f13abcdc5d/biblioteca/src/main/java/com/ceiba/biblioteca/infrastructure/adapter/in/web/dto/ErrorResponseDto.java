package com.ceiba.biblioteca.infrastructure.adapter.in.web.dto;

/**
 * DTO para retornar respuestas de error estandarizadas desde la API REST.
 *
 * Esta clase define la estructura unificada para todas las respuestas
 * de error que se envían al cliente cuando ocurre alguna excepción
 * o falla en el procesamiento de una solicitud.
 *
 * Implementa un formato consistente de errores que:
 * - Proporciona información clara sobre el problema ocurrido
 * - Mantiene una estructura predecible para los clientes
 * - Facilita el manejo de errores en el frontend
 * - Oculta detalles internos sensibles del sistema
 *
 * Ejemplo de respuesta JSON de error:
 * {
 *   "mensaje": "Tipo de usuario no permitido en la biblioteca"
 * }
 *
 * Esta clase es utilizada por el GlobalExceptionHandler para:
 * - Errores de dominio (400 Bad Request)
 * - Recursos no encontrados (404 Not Found)
 * - Errores internos del servidor (500 Internal Server Error)
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
public class ErrorResponseDto {

    /**
     * Mensaje descriptivo del error ocurrido.
     *
     * Texto explicativo que describe de manera clara y comprensible
     * la causa del error. Este mensaje está diseñado para ser
     * mostrado al usuario final o utilizado para debugging.
     *
     * Características del mensaje:
     * - Redactado en español para facilitar comprensión
     * - Describe el problema sin revelar detalles técnicos internos
     * - Proporciona información suficiente para entender la causa
     * - Mantiene un tono profesional y útil
     *
     * Ejemplos:
     * - "Tipo de usuario no permitido en la biblioteca"
     * - "El usuario con identificación 12345678 ya tiene un libro prestado"
     * - "El préstamo con ID 123 no existe"
     */
    private String mensaje;

    /**
     * Constructor por defecto requerido para serialización JSON.
     *
     * Permite que Jackson y otros frameworks de serialización
     * puedan crear instancias de esta clase sin parámetros.
     */
    public ErrorResponseDto() {}

    /**
     * Constructor que inicializa la respuesta de error con un mensaje.
     *
     * @param mensaje Descripción del error que se va a reportar al cliente
     */
    public ErrorResponseDto(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Obtiene el mensaje de error.
     *
     * @return Texto descriptivo del error ocurrido
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece el mensaje de error.
     *
     * @param mensaje Nuevo texto descriptivo del error
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}