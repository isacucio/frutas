package br.com.isa.frutas.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.isa.frutas.entity.Cidade;

public interface CidadeRepository extends JpaRepository <Cidade, Long> {
    @Query(value = "SELECT * FROM cidade WHERE nome = ?1", nativeQuery = true)
    List<Cidade> findByName(String nome);

    @Query(value = "SELECT c FROM Cidade c WHERE c.uuid = :uuid")
    Optional<Cidade> findByUUID(UUID uuid);

    @Query(value = "SELECT c FROM Cidade c WHERE lower(c.nome) LIKE %:nome%")
    List<Cidade> findAllByName(String nome, Pageable p);
    

}
