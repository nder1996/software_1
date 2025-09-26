package com.ceiba.biblioteca.infrastructure.adapter.out.persistence.repository;

import com.ceiba.biblioteca.infrastructure.adapter.out.persistence.entity.LibroJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibroJpaRepository extends JpaRepository<LibroJpaEntity, Integer> {

    @Query("SELECT l FROM LibroJpaEntity l WHERE l.isbn = :isbn")
    Optional<LibroJpaEntity> findByIsbn(@Param("isbn") String isbn);
}