package br.com.isa.frutas.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.isa.frutas.entity.Pais;

public interface PaisRepository extends JpaRepository<Pais, Long> {

    @Query(value = "SELECT * FROM pais WHERE nome = ?1", nativeQuery = true)
    List<Pais> findByName(String name);

    @Query(value = "SELECT p FROM Pais p WHERE p.uuid = :uuid")
    Optional<Pais> findByUUID(UUID uuid);

    @Query(value = "SELECT p FROM Pais p WHERE lower(p.nome) LIKE %:nome%")
    List<Pais> findAllByName(String nome, Pageable p);

}
