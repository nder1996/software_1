package com.ceiba.biblioteca.application.usecase;

import com.ceiba.biblioteca.application.dto.PrestarLibroCommand;
import com.ceiba.biblioteca.application.dto.ResultadoPrestar;
import com.ceiba.biblioteca.domain.model.Libro;
import com.ceiba.biblioteca.domain.model.Prestamo;
import com.ceiba.biblioteca.domain.model.TipoUsuario;
import com.ceiba.biblioteca.domain.port.in.PrestarLibroUseCase;
import com.ceiba.biblioteca.domain.port.out.LibroRepositoryPort;
import com.ceiba.biblioteca.domain.port.out.PrestamoRepositoryPort;
import com.ceiba.biblioteca.domain.service.CalculadorFechaDevolucion;
import com.ceiba.biblioteca.domain.service.ValidadorPrestamo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Implementación del caso de uso para prestar libros en el sistema de biblioteca.
 *
 * Esta clase orquesta el proceso completo de préstamo de libros, incluyendo:
 * - Validación del tipo de usuario y restricciones
 * - Obtención o creación del libro en el sistema
 * - Cálculo de la fecha máxima de devolución
 * - Creación y persistencia del préstamo
 * - Generación del resultado con información relevante
 *
 * Implementa el patrón Command para encapsular la lógica de negocio
 * y coordinar las interacciones entre los diferentes servicios de dominio.
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
public class PrestarLibroUseCaseImpl implements PrestarLibroUseCase {
    /**
     * Formateador de fechas utilizado para convertir LocalDate a String.
     * Utiliza el patrón dd/MM/yyyy para mostrar fechas en formato legible.
     */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Puerto de salida para acceder al repositorio de préstamos.
     * Permite persistir nuevos préstamos y consultar préstamos existentes.
     */
    private final PrestamoRepositoryPort prestamoRepository;

    /**
     * Puerto de salida para acceder al repositorio de libros.
     * Permite buscar libros existentes por ISBN.
     */
    private final LibroRepositoryPort libroRepository;

    /**
     * Servicio de dominio para calcular fechas de devolución.
     * Aplica las reglas de negocio para determinar cuándo debe devolverse un libro.
     */
    private final CalculadorFechaDevolucion calculadorFecha;

    /**
     * Servicio de dominio para validar reglas de préstamo.
     * Verifica restricciones de tipo de usuario y préstamos existentes.
     */
    private final ValidadorPrestamo validadorPrestamo;

    /**
     * Constructor que inicializa el caso de uso con todas sus dependencias.
     *
     * @param prestamoRepository Repositorio para persistir y consultar préstamos
     * @param libroRepository Repositorio para consultar libros por ISBN
     * @param calculadorFecha Servicio para calcular fechas de devolución
     * @param validadorPrestamo Servicio para validar reglas de préstamo
     */
    public PrestarLibroUseCaseImpl(PrestamoRepositoryPort prestamoRepository,
                                  LibroRepositoryPort libroRepository,
                                  CalculadorFechaDevolucion calculadorFecha,
                                  ValidadorPrestamo validadorPrestamo) {
        this.prestamoRepository = prestamoRepository;
        this.libroRepository = libroRepository;
        this.calculadorFecha = calculadorFecha;
        this.validadorPrestamo = validadorPrestamo;
    }

    /**
     * Ejecuta el caso de uso de prestar un libro siguiendo todas las reglas de negocio.
     *
     * Este método orquesta el proceso completo de préstamo:
     * 1. Valida que el tipo de usuario sea válido
     * 2. Verifica restricciones específicas para usuarios invitados
     * 3. Obtiene el libro del repositorio
     * 4. Calcula la fecha máxima de devolución
     * 5. Crea y persiste el préstamo
     * 6. Retorna el resultado con la información del préstamo creado
     *
     * @param command Comando que encapsula los datos necesarios para el préstamo
     * @return Resultado del préstamo con ID y fecha máxima de devolución
     *
     * @throws TipoUsuarioNoPermitidoException si el tipo de usuario no es válido
     * @throws UsuarioConPrestamoExistenteException si el usuario invitado ya tiene un préstamo
     * @throws RuntimeException si el libro con el ISBN no se encuentra
     */
    @Override
    public ResultadoPrestar ejecutar(PrestarLibroCommand command) {
        validadorPrestamo.validarTipoUsuario(command.getTipoUsuario());

        TipoUsuario tipoUsuario = TipoUsuario.fromValor(command.getTipoUsuario());
        validadorPrestamo.validarUsuarioInvitado(command.getIdentificacionUsuario(), tipoUsuario);

        Libro libro = obtenerOCrearLibro(command.getIsbn());
        LocalDate fechaMaximaDevolucion = calculadorFecha.calcular(tipoUsuario);

        Prestamo prestamo = Prestamo.crear(
            command.getIdentificacionUsuario(),
            tipoUsuario,
            libro,
            fechaMaximaDevolucion
        );

        Prestamo prestamoGuardado = prestamoRepository.save(prestamo);

        return ResultadoPrestar.builder()
            .id(prestamoGuardado.getId())
            .fechaMaximaDevolucion(fechaMaximaDevolucion.format(DATE_FORMATTER))
            .build();
    }

    /**
     * Método auxiliar para obtener un libro existente por su ISBN.
     *
     * Busca el libro en el repositorio utilizando el código ISBN.
     * Si no se encuentra, lanza una excepción indicando que el libro
     * no está disponible en el sistema.
     *
     * @param isbn Código ISBN del libro a buscar
     * @return El libro encontrado en el repositorio
     *
     * @throws RuntimeException si no se encuentra un libro con el ISBN especificado
     */
    private Libro obtenerOCrearLibro(String isbn) {
        return libroRepository.findByIsbn(isbn)
            .orElseThrow(() -> new RuntimeException("Libro con ISBN " + isbn + " no encontrado"));
    }
}