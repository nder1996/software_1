package com.ceiba.biblioteca.domain.model;

/**
 * Enumeración que define los tipos de usuarios permitidos en el sistema de biblioteca.
 *
 * Esta enumeración encapsula las reglas de negocio relacionadas con los diferentes
 * tipos de usuarios y sus privilegios de préstamo. Cada tipo tiene asociado un valor
 * numérico para identificación y una cantidad específica de días de préstamo permitidos.
 *
 * Los tipos de usuario son:
 * - AFILIADO: Usuario con membresía completa (10 días de préstamo)
 * - EMPLEADO: Personal de la institución (8 días de préstamo)
 * - INVITADO: Usuario temporal con restricciones (7 días de préstamo, solo 1 libro)
 *
 * @author Sistema Biblioteca Ceiba
 * @version 1.0
 * @since 1.0
 */
public enum TipoUsuario {

    /**
     * Usuario afiliado con membresía completa.
     * Tiene 10 días de préstamo y sin restricciones de cantidad.
     */
    AFILIADO(1, 10),

    /**
     * Empleado de la institución.
     * Tiene 8 días de préstamo y privilegios especiales.
     */
    EMPLEADO(2, 8),

    /**
     * Usuario invitado temporal.
     * Tiene 7 días de préstamo y solo puede tener 1 libro prestado a la vez.
     */
    INVITADO(3, 7);

    /**
     * Valor numérico que identifica el tipo de usuario.
     * Se utiliza para persistencia y comunicación con sistemas externos.
     */
    private final int valor;

    /**
     * Número de días que este tipo de usuario puede mantener un libro prestado.
     * Define el período máximo de préstamo según las reglas de negocio.
     */
    private final int diasPrestamo;

    /**
     * Constructor del enum que inicializa los valores asociados a cada tipo de usuario.
     *
     * @param valor Identificador numérico del tipo de usuario
     * @param diasPrestamo Días de préstamo permitidos para este tipo de usuario
     */
    TipoUsuario(int valor, int diasPrestamo) {
        this.valor = valor;
        this.diasPrestamo = diasPrestamo;
    }

    /**
     * Obtiene el valor numérico identificador del tipo de usuario.
     *
     * @return Valor entero que identifica este tipo de usuario
     */
    public int getValor() {
        return valor;
    }

    /**
     * Obtiene la cantidad de días de préstamo permitidos para este tipo de usuario.
     *
     * @return Número de días que puede durar un préstamo para este tipo de usuario
     */
    public int getDiasPrestamo() {
        return diasPrestamo;
    }

    /**
     * Método factory que convierte un valor numérico en el tipo de usuario correspondiente.
     *
     * Realiza la validación del valor de entrada y busca el tipo de usuario
     * que corresponde al valor proporcionado.
     *
     * @param valor Valor numérico del tipo de usuario (1=AFILIADO, 2=EMPLEADO, 3=INVITADO)
     * @return El tipo de usuario correspondiente al valor
     *
     * @throws IllegalArgumentException si el valor es nulo o no corresponde a ningún tipo válido
     */
    public static TipoUsuario fromValor(Integer valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Tipo de usuario no puede ser nulo");
        }

        for (TipoUsuario tipo : values()) {
            if (tipo.valor == valor) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de usuario no permitido en la biblioteca");
    }

    /**
     * Verifica si el tipo de usuario actual es un invitado.
     *
     * Los usuarios invitados tienen restricciones especiales como
     * solo poder tener un libro prestado a la vez.
     *
     * @return true si es un usuario invitado, false en caso contrario
     */
    public boolean esInvitado() {
        return this == INVITADO;
    }
}
