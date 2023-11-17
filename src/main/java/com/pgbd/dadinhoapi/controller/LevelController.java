package com.pgbd.dadinhoapi.controller;

import com.pgbd.dadinhoapi.dto.LevelByUserDTO;
import com.pgbd.dadinhoapi.model.Level;
import com.pgbd.dadinhoapi.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/level")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class LevelController {

    @Autowired
    private LevelService service;

    @GetMapping("/{id}")
    public ResponseEntity getLevel(@PathVariable(value = "id") Long id) {
        Optional<Level> level = service.findById(id);
        return level.isPresent() ? ResponseEntity.ok(level.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getLevelsByUser(@PathVariable(name = "userId") Long userId) {
        List<LevelByUserDTO> levelsByUser = service.getLevelsByUser(userId);
        return !levelsByUser.isEmpty() ? ResponseEntity.ok(levelsByUser) : ResponseEntity.noContent().build();
    }
}
