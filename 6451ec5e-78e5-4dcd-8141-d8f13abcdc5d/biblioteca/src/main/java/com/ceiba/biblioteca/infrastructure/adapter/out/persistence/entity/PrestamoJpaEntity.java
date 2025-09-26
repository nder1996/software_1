package com.ceiba.biblioteca.infrastructure.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "prestamo")
public class PrestamoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @Column(name = "fecha_maxima_devolucion", nullable = false)
    private LocalDate fechaMaximaDevolucion;

    @Column(name = "identificacion_usuario", nullable = false, length = 20)
    private String identificacionUsuario;

    @Column(name = "tipo_usuario")
    private Integer tipoUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_libro", nullable = false)
    private LibroJpaEntity libro;
}