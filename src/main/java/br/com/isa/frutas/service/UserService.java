package br.com.isa.frutas.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.isa.frutas.entity.User;
import br.com.isa.frutas.repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void create(User user) {
        user.setUid(UUID.randomUUID().toString());
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
