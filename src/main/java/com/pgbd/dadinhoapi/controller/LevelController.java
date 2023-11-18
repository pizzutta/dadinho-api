package com.pgbd.dadinhoapi.controller;

import com.pgbd.dadinhoapi.dto.LevelByUserDTO;
import com.pgbd.dadinhoapi.dto.LevelRegisterDTO;
import com.pgbd.dadinhoapi.dto.LevelResponseDTO;
import com.pgbd.dadinhoapi.dto.VerifyUserAnswerDTO;
import com.pgbd.dadinhoapi.model.Level;
import com.pgbd.dadinhoapi.service.LevelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/level")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class LevelController {

    @Autowired
    private LevelService service;

    @GetMapping
    public ResponseEntity findAllLevels() {
        List<Level> levels = service.findAllLevels();
        return !levels.isEmpty() ? ResponseEntity.ok(levels) : ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity findLevelById(@PathVariable(value = "id") Long id) {
        LevelResponseDTO dto = service.findByIdWithOptions(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity findLevelsByUser(@PathVariable(name = "userId") Long userId) {
        List<LevelByUserDTO> levelsByUser = service.findLevelsByUser(userId);
        return !levelsByUser.isEmpty() ? ResponseEntity.ok(levelsByUser) : ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity saveLevel(@RequestBody @Valid LevelRegisterDTO data) {
        Level level = service.save(data);
        return ResponseEntity.created(URI.create("/level/" + level.getId())).build();
    }

    @PostMapping("/verify-answer")
    public ResponseEntity verifyUserAnswer(@RequestBody @Valid VerifyUserAnswerDTO data) {
        Boolean success = service.verifyUserAnswer(data);
        return ResponseEntity.ok(success);
    }
}
