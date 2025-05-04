package com.pgbd.dadinhoapi.controller;

import com.pgbd.dadinhoapi.dto.IdDTO;
import com.pgbd.dadinhoapi.dto.LevelRegisterDTO;
import com.pgbd.dadinhoapi.dto.LevelUpdateDTO;
import com.pgbd.dadinhoapi.model.Level;
import com.pgbd.dadinhoapi.service.LevelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/level")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class LevelController {

    @Autowired
    private LevelService service;

    @GetMapping
    public ResponseEntity<List<Level>> getAll() {
        List<Level> levels = service.findAll();
        return levels.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(levels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Level> getById(@PathVariable(value = "id") Long id) {
        Optional<Level> level = service.findById(id);
        return level.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Level> save(@RequestBody @Valid LevelRegisterDTO data) {
        Level level = service.save(data);
        return ResponseEntity.created(URI.create("/level/" + level.getId())).body(level);
    }

    @PutMapping
    public ResponseEntity<Level> update(@RequestBody @Valid LevelUpdateDTO data) {
        Level level = service.save(data);
        return ResponseEntity.ok(level);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody @Valid IdDTO data) {
        service.deleteById(data.id());
        return ResponseEntity.noContent().build();
    }
}
