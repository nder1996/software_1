package com.ceiba.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Clase principal de la aplicación Spring Boot del Sistema de Biblioteca.
 *
 * Esta clase actúa como punto de entrada de la aplicación y configura
 * el contexto de Spring Boot con todas las funcionalidades necesarias
 * para el sistema de gestión de préstamos de biblioteca.
 *
 * Características principales:
 * - Configuración automática de Spring Boot
 * - Inicialización del contexto de aplicación
 * - Configuración de componentes y beans
 * - Arranque del servidor web embebido
 *
 * La aplicación implementa arquitectura hexagonal con:
 * - Domain: Modelos y reglas de negocio
 * - Application: Casos de uso y DTOs
 * - Infrastructure: Adaptadores REST y JPA
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
public class BibliotecaApplication {

	/**
	 * Método principal que inicia la aplicación Spring Boot.
	 *
	 * Configura y arranca el contexto de Spring, inicializa todos
	 * los componentes necesarios y pone en funcionamiento el servidor
	 * web para recibir peticiones HTTP.
	 *
	 * @param args Argumentos de línea de comandos (no utilizados)
	 */
	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
	}

}
