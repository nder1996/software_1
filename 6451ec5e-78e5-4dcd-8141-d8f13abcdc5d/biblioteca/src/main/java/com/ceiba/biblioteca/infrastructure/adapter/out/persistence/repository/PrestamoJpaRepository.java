package com.ceiba.biblioteca.infrastructure.adapter.out.persistence.repository;

import com.ceiba.biblioteca.infrastructure.adapter.out.persistence.entity.PrestamoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrestamoJpaRepository extends JpaRepository<PrestamoJpaEntity, Integer> {

    @Query("SELECT p FROM PrestamoJpaEntity p WHERE p.identificacionUsuario = :identificacionUsuario")
    Optional<PrestamoJpaEntity> findByIdentificacionUsuario(@Param("identificacionUsuario") String identificacionUsuario);

    @Query("SELECT COUNT(p) FROM PrestamoJpaEntity p WHERE p.identificacionUsuario = :identificacionUsuario")
    long countByIdentificacionUsuario(@Param("identificacionUsuario") String identificacionUsuario);
}