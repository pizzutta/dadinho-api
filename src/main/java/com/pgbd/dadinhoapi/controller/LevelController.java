package com.pgbd.dadinhoapi.controller;

import com.pgbd.dadinhoapi.dto.LevelByUserDTO;
import com.pgbd.dadinhoapi.dto.LevelResponseDTO;
import com.pgbd.dadinhoapi.dto.VerifyUserAnswerDTO;
import com.pgbd.dadinhoapi.service.LevelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/level")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class LevelController {

    @Autowired
    private LevelService service;

    @GetMapping("/{id}")
    public ResponseEntity getLevel(@PathVariable(value = "id") Long id) {
        LevelResponseDTO dto = service.findByIdWithOptions(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getLevelsByUser(@PathVariable(name = "userId") Long userId) {
        List<LevelByUserDTO> levelsByUser = service.getLevelsByUser(userId);
        return !levelsByUser.isEmpty() ? ResponseEntity.ok(levelsByUser) : ResponseEntity.noContent().build();
    }

    @PostMapping("/verify-answer")
    public ResponseEntity verifyUserAnswer(@RequestBody @Valid VerifyUserAnswerDTO data) {
        Boolean success = service.verifyUserAnswer(data);
        return ResponseEntity.ok(success);
    }
}
