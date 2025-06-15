package com.pgbd.dadinhoapi.service;

import com.pgbd.dadinhoapi.dto.LevelRegisterDTO;
import com.pgbd.dadinhoapi.dto.LevelSetupDTO;
import com.pgbd.dadinhoapi.dto.LevelUpdateDTO;
import com.pgbd.dadinhoapi.model.Level;
import com.pgbd.dadinhoapi.repository.LevelRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LevelService {

    @Autowired
    private LevelRepository repository;

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

        repository.save(level);

        return level;
    }

    @Transactional
    public Level save(LevelUpdateDTO data) {
        Level level = repository.findById(data.id()).orElseThrow(EntityNotFoundException::new);

        level.setIcon(data.icon());
        level.setTitle(data.title());

        repository.save(level);

        return level;
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public LevelSetupDTO mountLevelSetup(Long levelId) {
        Level level = repository.findById(levelId).orElseThrow(EntityNotFoundException::new);

        return new LevelSetupDTO(
                level.getId(),
                level.getIcon(),
                level.getTitle(),
                level.getRecipe(),
                level.getBaskets()
        );
    }
}
