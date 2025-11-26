package br.com.isa.frutas.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import br.com.isa.frutas.entity.Compra;

public interface CompraRepository extends JpaRepository <Compra, Long> {
    @Query(value = "SELECT * FROM compra WHERE id = ?1", nativeQuery = true)
    List<Compra> findById(long id);

    @Query(value = "SELECT c FROM Compra c WHERE c.uuid = :uuid")
    Optional<Compra> findByUUID(UUID uuid);

    @Query(value = "SELECT c "
        + " FROM Compra c JOIN FETCH c.fruta f "
        + " JOIN FETCH c.pessoa p JOIN FETCH f.pais p1 "
        + " JOIN FETCH p.pais p2")
    @NonNull
    List<Compra> findAll();

    @Query(value = "SELECT c FROM Compra c WHERE c.data = :data")
    List<Compra> findAllByDate(String data, Pageable p);

}
