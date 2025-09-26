package com.ceiba.biblioteca.application.usecase;

import com.ceiba.biblioteca.application.dto.ConsultaPrestamoResponse;
import com.ceiba.biblioteca.domain.exception.PrestamoNoEncontradoException;
import com.ceiba.biblioteca.domain.model.Libro;
import com.ceiba.biblioteca.domain.model.Prestamo;
import com.ceiba.biblioteca.domain.port.in.ConsultarPrestamoUseCase;
import com.ceiba.biblioteca.domain.port.out.PrestamoRepositoryPort;

import java.time.format.DateTimeFormatter;

/**
 * Implementación del caso de uso para consultar información de préstamos existentes.
 *
 * Esta clase se encarga de:
 * - Buscar un préstamo por su identificador único
 * - Validar que el préstamo exista en el sistema
 * - Construir y retornar la respuesta con toda la información relevante
 * - Formatear las fechas para presentación al usuario
 *
 * Implementa el patrón Query para separar las operaciones de consulta
 * de las operaciones de comando (modificación de estado).
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
public class ConsultarPrestamoUseCaseImpl implements ConsultarPrestamoUseCase {
    /**
     * Formateador de fechas utilizado para convertir LocalDate a String.
     * Utiliza el patrón dd/MM/yyyy para mostrar fechas en formato legible.
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Puerto de salida para acceder al repositorio de préstamos.
     * Permite consultar préstamos existentes por ID.
     */
    private final PrestamoRepositoryPort prestamoRepository;

    /**
     * Constructor que inicializa el caso de uso con sus dependencias.
     *
     * @param prestamoRepository Repositorio para consultar préstamos por ID
     */
    public ConsultarPrestamoUseCaseImpl(PrestamoRepositoryPort prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    /**
     * Ejecuta la consulta de un préstamo específico por su ID.
     *
     * Busca el préstamo en el repositorio y construye una respuesta
     * con toda la información relevante incluyendo datos del libro
     * y del usuario que realizó el préstamo.
     *
     * @param prestamoId Identificador único del préstamo a consultar
     * @return Respuesta con toda la información del préstamo encontrado
     *
     * @throws PrestamoNoEncontradoException si no existe un préstamo con el ID especificado
     */
    @Override
    public ConsultaPrestamoResponse ejecutar(Integer prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
            .orElseThrow(() -> new PrestamoNoEncontradoException(prestamoId));

        Libro libro = prestamo.getLibro();

        return ConsultaPrestamoResponse.builder()
            .id(prestamo.getId())
            .isbn(libro.getIsbn())
            .titulo(libro.getTitulo())
            .fechaMaximaDevolucion(prestamo.getFechaMaximaDevolucion().format(DATE_FORMATTER))
            .identificacionUsuario(prestamo.getIdentificacionUsuario())
            .tipoUsuario(prestamo.getTipoUsuario().getValor())
            .build();
    }
}