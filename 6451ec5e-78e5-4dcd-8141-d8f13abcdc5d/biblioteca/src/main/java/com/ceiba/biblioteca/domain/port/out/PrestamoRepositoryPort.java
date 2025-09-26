package com.ceiba.biblioteca.domain.port.out;

import com.ceiba.biblioteca.domain.model.Prestamo;

import java.util.Optional;

/**
 * Puerto de salida para el acceso a datos de préstamos.
 *
 * Esta interfaz define el contrato para las operaciones de persistencia
 * relacionadas con préstamos de libros. Abstrae el acceso a datos y
 * permite diferentes implementaciones (JPA, MongoDB, etc.) sin afectar
 * el dominio.
 *
 * Implementa el patrón Repository y el patrón Port de la arquitectura
 * hexagonal, manteniendo el dominio independiente de la infraestructura
 * de persistencia.
 *
 * Las operaciones disponibles incluyen:
 * - Persistencia de nuevos préstamos y actualizaciones
 * - Búsqueda por identificador único
 * - Búsqueda por usuario para validaciones
 * - Conteo de préstamos activos por usuario
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
public interface PrestamoRepositoryPort {

    /**
     * Persiste un préstamo nuevo o actualiza uno existente.
     *
     * Si el préstamo no tiene ID (es null), se creará un nuevo registro
     * y se asignará un ID único. Si ya tiene ID, se actualizará el
     * registro existente.
     *
     * @param prestamo Entidad de dominio del préstamo a persistir.
     *                 Debe contener toda la información necesaria
     *                 incluyendo libro, usuario y fechas.
     *
     * @return El préstamo persistido con su ID asignado y cualquier
     *         campo generado por la base de datos.
     *
     * @throws RuntimeException si ocurre un error durante la persistencia
     *                         o si el libro asociado no existe.
     */
    Prestamo save(Prestamo prestamo);

    /**
     * Busca un préstamo por su identificador único.
     *
     * Permite consultar un préstamo específico utilizando su ID
     * para operaciones de consulta y validación.
     *
     * @param id Identificador único del préstamo a buscar.
     *           Debe ser un número entero positivo.
     *
     * @return Optional conteniendo el préstamo si existe,
     *         o Optional.empty() si no se encuentra.
     */
    Optional<Prestamo> findById(Integer id);

    /**
     * Busca un préstamo por la identificación del usuario.
     *
     * Utilizado principalmente para validaciones de negocio,
     * especialmente para verificar si un usuario invitado
     * ya tiene un préstamo activo.
     *
     * @param identificacionUsuario Documento de identificación del usuario
     *                             (cédula, pasaporte, etc.).
     *
     * @return Optional conteniendo el préstamo del usuario si existe,
     *         o Optional.empty() si el usuario no tiene préstamos.
     */
    Optional<Prestamo> findByIdentificacionUsuario(String identificacionUsuario);

    /**
     * Cuenta el número de préstamos activos de un usuario específico.
     *
     * Esta operación es crítica para validar la regla de negocio
     * que impide a usuarios invitados tener más de un préstamo
     * simultáneamente.
     *
     * @param identificacionUsuario Documento de identificación del usuario
     *                             para el cual se contarán los préstamos.
     *
     * @return Número total de préstamos activos del usuario.
     *         Retorna 0 si el usuario no tiene préstamos.
     */
    long countByIdentificacionUsuario(String identificacionUsuario);
}