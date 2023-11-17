package com.pgbd.dadinhoapi.service;

import com.pgbd.dadinhoapi.dto.LevelByUserDTO;
import com.pgbd.dadinhoapi.dto.LevelResponseDTO;
import com.pgbd.dadinhoapi.dto.VerifyUserAnswerDTO;
import com.pgbd.dadinhoapi.model.Level;
import com.pgbd.dadinhoapi.model.User;
import com.pgbd.dadinhoapi.repository.LevelRepository;
import com.pgbd.dadinhoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Arrays.stream;

@Service
public class LevelService {

    @Autowired
    private LevelRepository repository;
    @Autowired
    private UserRepository userRepository;

    public LevelResponseDTO findByIdWithOptions(Long id) {
        Optional<Level> levelOptional = repository.findById(id);

        if (levelOptional.isEmpty()) {
            return null;
        }

        Level level = levelOptional.get();
        List<String> options = new ArrayList<>();

        for (String answer : level.getAnswers()) {
            options.addAll(stream(answer.split("\\|")).toList());
        }

        LevelResponseDTO dto = new LevelResponseDTO(
                level.getId(),
                level.getIcon(),
                level.getTitle(),
                level.getRecipe(),
                level.getBaskets(),
                options
        );
        Collections.shuffle(dto.options());

        return dto;
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

    public Boolean verifyUserAnswer(VerifyUserAnswerDTO data) {
        Level level = repository.findById(data.levelId()).get();
        List<String> levelAnswers = level.getAnswers();

        Boolean success = levelAnswers.containsAll(data.userAnswers());

        if (success) {
            User user = userRepository.findById(data.userId()).get();
            user.getConcludedLevels().add(level);
            userRepository.save(user);
        }

        return success;
    }
}
