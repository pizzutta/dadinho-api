package com.pgbd.dadinhoapi.service;

import com.pgbd.dadinhoapi.dto.*;
import com.pgbd.dadinhoapi.model.Level;
import com.pgbd.dadinhoapi.model.User;
import com.pgbd.dadinhoapi.model.UserConcludedLevel;
import com.pgbd.dadinhoapi.repository.LevelRepository;
import com.pgbd.dadinhoapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.stream;
import static java.util.Collections.shuffle;

@Service
public class LevelService {

    @Autowired
    private LevelRepository repository;
    @Autowired
    private UserRepository userRepository;

    public Optional<Level> findById(Long id) {
        return repository.findById(id);
    }

    public List<Level> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Level save(LevelRegisterDTO data) {
        Level level = new Level();

        level.setIcon(data.icon());
        level.setTitle(data.title());
        level.setAnswers(data.answers());

        repository.save(level);

        return level;
    }

    @Transactional
    public Level save(LevelUpdateDTO data) {
        Level level = repository.findById(data.id()).orElseThrow(EntityNotFoundException::new);

        level.setIcon(data.icon());
        level.setTitle(data.title());
        level.setAnswers(data.answers());

        repository.save(level);

        return level;
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public LevelSetupDTO mountLevelSetup(Long levelId) {
        Level level = repository.findById(levelId).orElseThrow(EntityNotFoundException::new);
        List<String> options = new ArrayList<>();

        for (String answer : level.getAnswers()) {
            options.addAll(stream(answer.split("\\|")).toList());
        }

        shuffle(options);

        return new LevelSetupDTO(
                level.getId(),
                level.getIcon(),
                level.getTitle(),
                level.getRecipe(),
                level.getBaskets(),
                options
        );
    }

    public List<LevelProgressDTO> getUserProgress(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        List<Level> levels = repository.findAll();
        List<Long> concludedLevelsIds = user.getConcludedLevels().stream()
                .map(UserConcludedLevel::getLevel).map(Level::getId).toList();
        List<LevelProgressDTO> levelProgresses = new ArrayList<>();

        for (Level level : levels) {
            Boolean isConcluded = concludedLevelsIds.contains(level.getId());
            LevelProgressDTO dto = new LevelProgressDTO(level.getId(), level.getIcon(), isConcluded);
            levelProgresses.add(dto);
        }

        return levelProgresses;
    }
}
