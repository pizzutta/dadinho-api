package com.pgbd.dadinhoapi.controller;

import com.pgbd.dadinhoapi.dto.ClassRegisterDTO;
import com.pgbd.dadinhoapi.model.Class;
import com.pgbd.dadinhoapi.service.ClassService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/class")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class ClassController {

    @Autowired
    private ClassService service;

    @GetMapping
    public ResponseEntity findAllClasses() {
        List<Class> classes = service.findAll();
        return !classes.isEmpty() ? ResponseEntity.ok(classes) : ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity saveClass(@RequestBody @Valid ClassRegisterDTO data) {
        Class c;
        try {
            c = service.save(data);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
        return ResponseEntity.created(URI.create("/class/" + c.getId())).build();
    }
}
