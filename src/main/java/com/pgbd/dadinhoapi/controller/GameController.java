package com.pgbd.dadinhoapi.controller;

import com.pgbd.dadinhoapi.dto.LevelProgressDTO;
import com.pgbd.dadinhoapi.dto.LevelSetupDTO;
import com.pgbd.dadinhoapi.dto.UserAnswerDTO;
import com.pgbd.dadinhoapi.game.model.Result;
import com.pgbd.dadinhoapi.service.GameService;
import com.pgbd.dadinhoapi.service.LevelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService service;
    @Autowired
    private LevelService levelService;

    @GetMapping("/setup/{levelId}")
    public ResponseEntity<LevelSetupDTO> getLevelSetup(@PathVariable Long levelId) {
        LevelSetupDTO setup = levelService.mountLevelSetup(levelId);
        return ResponseEntity.ok(setup);
    }

    @GetMapping("/progress/{userId}")
    public ResponseEntity<List<LevelProgressDTO>> getUserProgress(@PathVariable Long userId) {
        List<LevelProgressDTO> progress = service.getProgressByUserId(userId);
        return progress.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(progress);
    }

    @PostMapping("/submit")
    public ResponseEntity<Result> submit(@RequestBody @Valid UserAnswerDTO data) {
        Result result = service.submit(data);
        return ResponseEntity.ok(result);
    }
}
