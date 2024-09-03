package com.wellwishers.talent_api.controller;

import com.wellwishers.talent_api.model.Credentials;
import com.wellwishers.talent_api.model.User;
import com.wellwishers.talent_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<User> login(@RequestBody Credentials credentials) {
        Optional<User> user = userRepository.findByUsername(credentials.getUsername());

        if (user.isPresent() && user.get().getPassword().equals(credentials.getPassword())) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

}
