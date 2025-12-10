package br.com.isa.frutas.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.isa.frutas.dto.UserDTO;
import br.com.isa.frutas.entity.User;
import br.com.isa.frutas.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public ResponseEntity<?> getUserDetails() {
        String userName = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<User> opUser = userService.findByUsername(userName);
        if (opUser.isPresent()) {
            User user = opUser.get();
            return ResponseEntity.ok(new UserDTO(
                    user.getUid(), user.getUsername(),
                    user.getName(), user.getEmail()
            ));
        }
        return ResponseEntity.notFound().build();
    }
}
