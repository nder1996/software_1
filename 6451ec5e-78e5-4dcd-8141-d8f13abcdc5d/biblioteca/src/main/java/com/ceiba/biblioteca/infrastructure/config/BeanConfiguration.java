package com.ceiba.biblioteca.infrastructure.config;

import com.ceiba.biblioteca.application.usecase.ConsultarPrestamoUseCaseImpl;
import com.ceiba.biblioteca.application.usecase.PrestarLibroUseCaseImpl;
import com.ceiba.biblioteca.domain.port.in.ConsultarPrestamoUseCase;
import com.ceiba.biblioteca.domain.port.in.PrestarLibroUseCase;
import com.ceiba.biblioteca.domain.port.out.LibroRepositoryPort;
import com.ceiba.biblioteca.domain.port.out.PrestamoRepositoryPort;
import com.ceiba.biblioteca.domain.service.CalculadorFechaDevolucion;
import com.ceiba.biblioteca.domain.service.ValidadorPrestamo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de beans para la inversión de dependencias en el dominio.
 *
 * Esta clase de configuración se encarga de crear y configurar todos los
 * beans necesarios para implementar la arquitectura hexagonal, conectando
 * las interfaces del dominio con sus implementaciones concretas.
 *
 * Implementa el patrón Dependency Injection para:
 * - Desacoplar el dominio de la infraestructura
 * - Facilitar testing con mocks
 * - Centralizar la configuración de dependencias
 * - Permitir múltiples implementaciones de interfaces
 *
 * Responsabilidades:
 * - Configuración de servicios de dominio
 * - Inyección de dependencias en casos de uso
 * - Creación de beans sin anotaciones (@Component, @Service)
 * - Orquestación de la arquitectura hexagonal
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public CalculadorFechaDevolucion calculadorFechaDevolucion() {
        return new CalculadorFechaDevolucion();
    }

    @Bean
    public ValidadorPrestamo validadorPrestamo(PrestamoRepositoryPort prestamoRepository) {
        return new ValidadorPrestamo(prestamoRepository);
    }

    @Bean
    public PrestarLibroUseCase prestarLibroUseCase(PrestamoRepositoryPort prestamoRepository,
                                                   LibroRepositoryPort libroRepository,
                                                   CalculadorFechaDevolucion calculadorFecha,
                                                   ValidadorPrestamo validadorPrestamo) {
        return new PrestarLibroUseCaseImpl(prestamoRepository, libroRepository, calculadorFecha, validadorPrestamo);
    }

    @Bean
    public ConsultarPrestamoUseCase consultarPrestamoUseCase(PrestamoRepositoryPort prestamoRepository) {
        return new ConsultarPrestamoUseCaseImpl(prestamoRepository);
    }
}