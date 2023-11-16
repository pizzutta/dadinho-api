package com.pgbd.dadinhoapi.service;

import com.pgbd.dadinhoapi.dto.LevelByUserDTO;
import com.pgbd.dadinhoapi.model.Level;
import com.pgbd.dadinhoapi.model.User;
import com.pgbd.dadinhoapi.repository.LevelRepository;
import com.pgbd.dadinhoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LevelService {

    @Autowired
    private LevelRepository repository;
    @Autowired
    private UserRepository userRepository;

    public Optional<Level> findById(Long id) {
        return repository.findById(id);
    }

    public List<LevelByUserDTO> getLevelsByUser(Long userId) {
        List<Level> levels = repository.findAll();
        User user = userRepository.findById(userId).get();
        List<LevelByUserDTO> levelsByUser = new ArrayList<>();

        for (Level level : levels) {
            Boolean isConcluded = user.getConcludedLevels().contains(level);
            LevelByUserDTO dto = new LevelByUserDTO(level.getId(), level.getIcon(), isConcluded);
            levelsByUser.add(dto);
        }

        return levelsByUser;
    }
}
