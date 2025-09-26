package com.ceiba.biblioteca.infrastructure.adapter.in.web.mapper;

import com.ceiba.biblioteca.application.dto.ConsultaPrestamoResponse;
import com.ceiba.biblioteca.application.dto.PrestarLibroCommand;
import com.ceiba.biblioteca.application.dto.ResultadoPrestar;
import com.ceiba.biblioteca.infrastructure.adapter.in.web.dto.ConsultaPrestamoResponseDto;
import com.ceiba.biblioteca.infrastructure.adapter.in.web.dto.ResultadoPrestarDto;
import com.ceiba.biblioteca.infrastructure.adapter.in.web.dto.SolicitudPrestarLibroDto;
import org.springframework.stereotype.Component;

/**
 * Mapper para transformar entre DTOs de la capa web y DTOs de la capa de aplicación.
 *
 * Esta clase implementa el patrón Mapper para realizar conversiones bidireccionales
 * entre los objetos de transferencia de datos (DTOs) de la capa de infraestructura web
 * y los DTOs/Commands de la capa de aplicación.
 *
 * Responsabilidades:
 * - Convertir DTOs de entrada web (requests) en Commands de aplicación
 * - Convertir DTOs de respuesta de aplicación en DTOs de salida web (responses)
 * - Aislar la capa de aplicación de los detalles específicos de la API REST
 * - Permitir evolución independiente de contratos web y de aplicación
 *
 * Implementa el patrón Anti-Corruption Layer de DDD para:
 * - Proteger el dominio de cambios en la interfaz externa
 * - Mantener estabilidad en la capa de aplicación
 * - Facilitar versionado de APIs
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
@Component
public class PrestamoRestMapper {

    /**
     * Convierte una solicitud web de préstamo en un comando de aplicación.
     *
     * Transforma el DTO de entrada de la API REST en el Command que utiliza
     * la capa de aplicación para procesar la lógica de negocio del préstamo.
     *
     * @param dto DTO de solicitud recibido desde el cliente web
     * @return Command preparado para ejecutar el caso de uso de préstamo
     */
    public PrestarLibroCommand toCommand(SolicitudPrestarLibroDto dto) {
        return PrestarLibroCommand.builder()
            .isbn(dto.getIsbn())
            .identificacionUsuario(dto.getIdentificacionUsuario())
            .tipoUsuario(dto.getTipoUsuario())
            .build();
    }

    /**
     * Convierte el resultado de un préstamo de aplicación a DTO web.
     *
     * Transforma la respuesta del caso de uso de préstamo en el DTO
     * que se envía como respuesta JSON al cliente web.
     *
     * @param resultado Resultado del caso de uso de préstamo
     * @return DTO con formato apropiado para respuesta HTTP
     */
    public ResultadoPrestarDto toDto(ResultadoPrestar resultado) {
        return ResultadoPrestarDto.builder()
            .id(resultado.getId())
            .fechaMaximaDevolucion(resultado.getFechaMaximaDevolucion())
            .build();
    }

    /**
     * Convierte una respuesta de consulta de préstamo a DTO web.
     *
     * Transforma la respuesta del caso de uso de consulta en el DTO
     * que se envía como respuesta JSON detallada al cliente web.
     *
     * @param consulta Respuesta del caso de uso de consulta de préstamo
     * @return DTO con toda la información del préstamo para respuesta HTTP
     */
    public ConsultaPrestamoResponseDto toDto(ConsultaPrestamoResponse consulta) {
        return ConsultaPrestamoResponseDto.builder()
            .id(consulta.getId())
            .isbn(consulta.getIsbn())
            .titulo(consulta.getTitulo())
            .fechaMaximaDevolucion(consulta.getFechaMaximaDevolucion())
            .identificacionUsuario(consulta.getIdentificacionUsuario())
            .tipoUsuario(consulta.getTipoUsuario())
            .build();
    }
}