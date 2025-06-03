package com.pgbd.dadinhoapi.controller;

import com.pgbd.dadinhoapi.dto.ClassRegisterDTO;
import com.pgbd.dadinhoapi.dto.ClassUpdateDTO;
import com.pgbd.dadinhoapi.dto.IdDTO;
import com.pgbd.dadinhoapi.model.Class;
import com.pgbd.dadinhoapi.service.ClassService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/class")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class ClassController {

    @Autowired
    private ClassService service;

    @GetMapping
    public ResponseEntity<List<Class>> getAll() {
        List<Class> classes = service.findAll();
        return classes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(classes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Class> getById(@PathVariable Long id) {
        Optional<Class> aClass = service.findById(id);
        return aClass.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Class> save(@RequestBody @Valid ClassRegisterDTO data) {
        Class aClass = service.save(data);
        return ResponseEntity.created(URI.create("/class/" + aClass.getId())).body(aClass);
    }

    @PutMapping
    public ResponseEntity<Class> update(@RequestBody @Valid ClassUpdateDTO data) {
        Class aClass = service.save(data);
        return ResponseEntity.ok(aClass);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody @Valid IdDTO data) {
        service.deleteById(data.id());
        return ResponseEntity.noContent().build();
    }
}
