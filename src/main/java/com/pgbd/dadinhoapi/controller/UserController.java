package com.pgbd.dadinhoapi.controller;

import com.pgbd.dadinhoapi.model.User;
import com.pgbd.dadinhoapi.model.UserRole;
import com.pgbd.dadinhoapi.service.UserService;
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
    public ResponseEntity findAllUsers(@RequestParam(name = "role", required = false) UserRole role) {
        List<User> users;

        if (role == null) {
            users = service.findAll();
        } else {
            users = service.findByRole(role);
        }

        return !users.isEmpty() ? ResponseEntity.ok(users) : ResponseEntity.noContent().build();
    }
}
