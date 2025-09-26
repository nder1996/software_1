package com.ceiba.biblioteca.infrastructure.adapter.in.web;

import com.ceiba.biblioteca.application.dto.ConsultaPrestamoResponse;
import com.ceiba.biblioteca.application.dto.PrestarLibroCommand;
import com.ceiba.biblioteca.application.dto.ResultadoPrestar;
import com.ceiba.biblioteca.domain.port.in.ConsultarPrestamoUseCase;
import com.ceiba.biblioteca.domain.port.in.PrestarLibroUseCase;
import com.ceiba.biblioteca.infrastructure.adapter.in.web.dto.ConsultaPrestamoResponseDto;
import com.ceiba.biblioteca.infrastructure.adapter.in.web.dto.ResultadoPrestarDto;
import com.ceiba.biblioteca.infrastructure.adapter.in.web.dto.SolicitudPrestarLibroDto;
import com.ceiba.biblioteca.infrastructure.adapter.in.web.mapper.PrestamoRestMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para operaciones de préstamos de libros.
 *
 * Esta clase expone los endpoints HTTP que permiten a los clientes
 * realizar operaciones relacionadas con préstamos de libros en la biblioteca.
 * Actúa como adaptador de entrada en la arquitectura hexagonal.
 *
 * Responsabilidades:
 * - Exposición de API REST para préstamos
 * - Validación de entrada HTTP
 * - Transformación entre DTOs web y comandos de aplicación
 * - Manejo de respuestas HTTP y códigos de estado
 * - Delegación de lógica de negocio a casos de uso
 *
 * Endpoints disponibles:
 * - POST /prestamo: Crear nuevo préstamo
 * - GET /prestamo/{id}: Consultar préstamo existente
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("prestamo")
public class PrestamoController {

    /**
     * Caso de uso para procesar solicitudes de préstamo.
     * Puerto de entrada que encapsula la lógica de negocio principal.
     */
    private final PrestarLibroUseCase prestarLibroUseCase;

    /**
     * Caso de uso para consultar información de préstamos existentes.
     * Puerto de entrada para operaciones de consulta (query).
     */
    private final ConsultarPrestamoUseCase consultarPrestamoUseCase;

    /**
     * Mapper para transformar entre DTOs web y DTOs de aplicación.
     * Facilita la conversión bidireccional de datos.
     */
    private final PrestamoRestMapper mapper;

    /**
     * Constructor que inicializa el controlador con sus dependencias.
     *
     * @param prestarLibroUseCase Caso de uso para procesar préstamos
     * @param consultarPrestamoUseCase Caso de uso para consultar préstamos
     * @param mapper Transformador entre DTOs web y aplicación
     */
    public PrestamoController(PrestarLibroUseCase prestarLibroUseCase,
                             ConsultarPrestamoUseCase consultarPrestamoUseCase,
                             PrestamoRestMapper mapper) {
        this.prestarLibroUseCase = prestarLibroUseCase;
        this.consultarPrestamoUseCase = consultarPrestamoUseCase;
        this.mapper = mapper;
    }

    /**
     * Endpoint para crear un nuevo préstamo de libro.
     *
     * Procesa solicitudes HTTP POST para prestar libros aplicando
     * todas las reglas de negocio correspondientes.
     *
     * @param solicitud DTO con los datos del préstamo (ISBN, usuario, tipo)
     * @return ResponseEntity con el resultado del préstamo (ID y fecha límite)
     *
     * Ejemplo de solicitud:
     * POST /prestamo
     * Content-Type: application/json
     * {
     *   "isbn": "978-3-16-148410-0",
     *   "identificacionUsuario": "12345678",
     *   "tipoUsuario": 1
     * }
     *
     * Respuestas posibles:
     * - 200 OK: Préstamo creado exitosamente
     * - 400 Bad Request: Error en datos o reglas de negocio
     * - 500 Internal Server Error: Error interno del sistema
     */
    @PostMapping
    public ResponseEntity<ResultadoPrestarDto> prestarLibro(@RequestBody SolicitudPrestarLibroDto solicitud) {
        ResultadoPrestar resultado = prestarLibroUseCase.ejecutar(mapper.toCommand(solicitud));
        return ResponseEntity.ok(mapper.toDto(resultado));
    }

    /**
     * Endpoint para consultar información de un préstamo existente.
     *
     * Permite obtener toda la información detallada de un préstamo
     * específico utilizando su identificador único.
     *
     * @param prestamoId Identificador único del préstamo a consultar
     * @return ResponseEntity con la información completa del préstamo
     *
     * Ejemplo de solicitud:
     * GET /prestamo/123
     *
     * Respuestas posibles:
     * - 200 OK: Información del préstamo encontrado
     * - 404 Not Found: Préstamo no existe
     * - 500 Internal Server Error: Error interno del sistema
     */
    @GetMapping("/{prestamoId}")
    public ResponseEntity<ConsultaPrestamoResponseDto> consultarPrestamo(@PathVariable Integer prestamoId) {
        ConsultaPrestamoResponse consulta = consultarPrestamoUseCase.ejecutar(prestamoId);
        return ResponseEntity.ok(mapper.toDto(consulta));
    }
}