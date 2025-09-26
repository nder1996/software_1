package com.ceiba.biblioteca.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Clase que representa un préstamo de libro en el sistema de biblioteca.
 *
 * Esta clase es el modelo de dominio principal que encapsula toda la información
 * relacionada con un préstamo, incluyendo las fechas, el usuario solicitante,
 * el tipo de usuario y el libro prestado.
 *
 * Utiliza el patrón Builder para la construcción de objetos y Lombok para
 * reducir el código boilerplate de getters, setters y constructores.
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prestamo {

    /**
     * Identificador único del préstamo en la base de datos.
     * Se genera automáticamente al persistir el préstamo.
     */
    private Integer id;

    /**
     * Fecha en la que se realizó el préstamo.
     * Se establece automáticamente al momento de crear el préstamo.
     */
    private LocalDate fechaPrestamo;

    /**
     * Fecha máxima permitida para la devolución del libro.
     * Se calcula según el tipo de usuario y las reglas de negocio.
     */
    private LocalDate fechaMaximaDevolucion;

    /**
     * Documento de identificación del usuario que solicita el préstamo.
     * Puede ser cédula, pasaporte u otro documento válido.
     */
    private String identificacionUsuario;

    /**
     * Tipo de usuario que realiza el préstamo (AFILIADO, EMPLEADO, INVITADO).
     * Determina los días de préstamo permitidos y las validaciones aplicables.
     */
    private TipoUsuario tipoUsuario;

    /**
     * Referencia al libro que se está prestando.
     * Contiene toda la información bibliográfica del libro.
     */
    private Libro libro;

    /**
     * Método factory para crear un nuevo préstamo con las reglas de negocio aplicadas.
     *
     * Establece automáticamente la fecha de préstamo como la fecha actual y
     * construye el objeto préstamo con los parámetros proporcionados.
     *
     * @param identificacionUsuario Documento de identificación del usuario solicitante
     * @param tipoUsuario Tipo de usuario que realiza el préstamo
     * @param libro Libro que se va a prestar
     * @param fechaMaximaDevolucion Fecha límite calculada para la devolución
     * @return Nueva instancia de Prestamo configurada con los datos proporcionados
     *
     * @throws IllegalArgumentException si algún parámetro es nulo o inválido
     */
    public static Prestamo crear(String identificacionUsuario, TipoUsuario tipoUsuario,
                                Libro libro, LocalDate fechaMaximaDevolucion) {
        return Prestamo.builder()
                .fechaPrestamo(LocalDate.now())
                .fechaMaximaDevolucion(fechaMaximaDevolucion)
                .identificacionUsuario(identificacionUsuario)
                .tipoUsuario(tipoUsuario)
                .libro(libro)
                .build();
    }
}