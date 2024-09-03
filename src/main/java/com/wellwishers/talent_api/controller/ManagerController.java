package com.wellwishers.talent_api.controller;

import com.wellwishers.talent_api.model.Manager;
import com.wellwishers.talent_api.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerRepository managerRepository;

    // GET all managers
    @GetMapping
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    // GET manager by ID
    @GetMapping("/{id}")
    public ResponseEntity<Manager> getManagerById(@PathVariable Integer id) {
        Optional<Manager> manager = managerRepository.findById(id);
        return manager.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST create a new manager
    @PostMapping
    public Manager createManager(@RequestBody Manager manager) {
        return managerRepository.save(manager);
    }

    // PUT update an existing manager
    @PutMapping("/{id}")
    public ResponseEntity<Manager> updateManager(@PathVariable Integer id, @RequestBody Manager managerDetails) {
        Optional<Manager> manager = managerRepository.findById(id);

        if (manager.isPresent()) {
            Manager updatedManager = manager.get();
            updatedManager.setFullName(managerDetails.getFullName());
            updatedManager.setEmail(managerDetails.getEmail());
            updatedManager.setDepartment(managerDetails.getDepartment());
            updatedManager.setPhone(managerDetails.getPhone());
            updatedManager.setUserId(managerDetails.getUserId());

            Manager savedManager = managerRepository.save(updatedManager);
            return ResponseEntity.ok(savedManager);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE a manager
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Integer id) {
        if (managerRepository.existsById(id)) {
            managerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
