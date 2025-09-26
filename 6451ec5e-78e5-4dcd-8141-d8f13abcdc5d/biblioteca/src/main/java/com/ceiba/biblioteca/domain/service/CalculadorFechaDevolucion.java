package com.ceiba.biblioteca.domain.service;

import com.ceiba.biblioteca.domain.model.TipoUsuario;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Servicio de dominio responsable de calcular la fecha máxima de devolución de préstamos.
 *
 * Este servicio implementa las reglas de negocio para determinar cuándo debe ser
 * devuelto un libro según el tipo de usuario y excluyendo fines de semana del cálculo.
 *
 * La lógica de cálculo considera:
 * - Los días de préstamo específicos para cada tipo de usuario
 * - Exclusión de sábados y domingos del conteo de días hábiles
 * - Flexibilidad para calcular desde una fecha base específica
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
public class CalculadorFechaDevolucion {

    /**
     * Calcula la fecha máxima de devolución usando la fecha actual como punto de partida.
     *
     * Este método es un wrapper de conveniencia que utiliza la fecha actual
     * como fecha base para el cálculo de la fecha de devolución.
     *
     * @param tipoUsuario Tipo de usuario que determina los días de préstamo permitidos
     * @return Fecha máxima de devolución calculada desde hoy, excluyendo fines de semana
     */
    public LocalDate calcular(TipoUsuario tipoUsuario) {
        return calcular(tipoUsuario, LocalDate.now());
    }

    /**
     * Calcula la fecha máxima de devolución desde una fecha base específica.
     *
     * Suma los días de préstamo correspondientes al tipo de usuario,
     * excluyendo sábados y domingos del conteo. Solo cuenta días hábiles
     * (lunes a viernes) para el cálculo.
     *
     * @param tipoUsuario Tipo de usuario que determina los días de préstamo permitidos
     * @param fechaBase Fecha desde la cual se inicia el cálculo del período de préstamo
     * @return Fecha máxima de devolución calculada excluyendo fines de semana
     *
     * @throws IllegalArgumentException si el tipo de usuario es nulo
     */
    public LocalDate calcular(TipoUsuario tipoUsuario, LocalDate fechaBase) {
        return addDaysSkippingWeekends(fechaBase, tipoUsuario.getDiasPrestamo());
    }

    /**
     * Método auxiliar que suma días hábiles a una fecha, excluyendo fines de semana.
     *
     * Implementa la lógica de avanzar día por día contando solo los días
     * de lunes a viernes hasta alcanzar el número de días requeridos.
     * Los sábados y domingos se saltan sin contar hacia el total.
     *
     * @param date Fecha inicial desde la que se empiezan a contar los días
     * @param days Número de días hábiles a sumar (excluyendo fines de semana)
     * @return Nueva fecha que resulta de sumar los días hábiles especificados
     */
    private LocalDate addDaysSkippingWeekends(LocalDate date, int days) {
        LocalDate result = date;
        int addedDays = 0;
        while (addedDays < days) {
            result = result.plusDays(1);
            if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                ++addedDays;
            }
        }
        return result;
    }
}