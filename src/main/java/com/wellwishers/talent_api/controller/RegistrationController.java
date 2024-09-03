package com.wellwishers.talent_api.controller;

import com.wellwishers.talent_api.model.User;
import com.wellwishers.talent_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<User> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(409).body(null); // Username already exists
        }

        // default user type
        user.setType("candidate");
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

}
