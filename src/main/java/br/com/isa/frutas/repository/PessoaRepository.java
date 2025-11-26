package br.com.isa.frutas.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.isa.frutas.entity.Pessoa;

public interface PessoaRepository extends JpaRepository <Pessoa, Long> {
    @Query(value = "SELECT * FROM pessoa WHERE nome = ?1", nativeQuery = true)
    List<Pessoa> findByName(String name);

    @Query(value = "SELECT p FROM Pessoa p WHERE p.uuid = :uuid")
    Optional<Pessoa> findByUUID(UUID uuid);

    @Query(value = "SELECT p FROM Pessoa p WHERE lower(p.nome) LIKE %:nome%")
    List<Pessoa> findAllByName(String nome, Pageable p);

}
