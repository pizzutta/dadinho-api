package com.pgbd.dadinhoapi.controller;

import com.pgbd.dadinhoapi.dto.IdDTO;
import com.pgbd.dadinhoapi.dto.UserResponseDTO;
import com.pgbd.dadinhoapi.dto.UserUpdateDTO;
import com.pgbd.dadinhoapi.model.User;
import com.pgbd.dadinhoapi.model.UserRole;
import com.pgbd.dadinhoapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "role", required = false) UserRole role) {
        if (role == null) {
            List<User> users = service.findAll();
            return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
        } else {
            List<UserResponseDTO> users = service.findByRole(role);
            return !users.isEmpty() ? ResponseEntity.ok(users) : ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        UserResponseDTO dto = service.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody @Valid UserUpdateDTO data) {
        User user = service.save(data);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody @Valid IdDTO data) {
        service.deleteById(data.id());
        return ResponseEntity.noContent().build();
    }
}
