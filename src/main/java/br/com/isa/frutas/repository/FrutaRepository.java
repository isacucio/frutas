package br.com.isa.frutas.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.isa.frutas.entity.Fruta;

public interface FrutaRepository extends JpaRepository <Fruta, Long> {
    @Query(value = "SELECT * FROM fruta WHERE nome = ?1", nativeQuery = true)
    List<Fruta> findByName(String name);

    @Query(value = "SELECT f FROM Fruta f WHERE f.uuid = :uuid")
    Optional<Fruta> findByUUID(UUID uuid);

    @Query(value = "SELECT f FROM Fruta f WHERE lower(f.nome) LIKE %:nome%")
    List<Fruta> findAllByName(String nome,Pageable p);
}
