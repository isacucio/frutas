package br.com.isa.frutas.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.isa.frutas.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
