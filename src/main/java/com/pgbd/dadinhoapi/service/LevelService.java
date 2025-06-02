package com.pgbd.dadinhoapi.service;

import com.pgbd.dadinhoapi.dto.LevelByUserDTO;
import com.pgbd.dadinhoapi.dto.LevelRegisterDTO;
import com.pgbd.dadinhoapi.dto.LevelResponseDTO;
import com.pgbd.dadinhoapi.dto.VerifyUserAnswerDTO;
import com.pgbd.dadinhoapi.model.Level;
import com.pgbd.dadinhoapi.model.User;
import com.pgbd.dadinhoapi.model.UserConcludedLevel;
import com.pgbd.dadinhoapi.repository.LevelRepository;
import com.pgbd.dadinhoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.stream;

@Service
public class LevelService {

    @Autowired
    private LevelRepository repository;
    @Autowired
    private UserRepository userRepository;

    public List<Level> findAllLevels() {
        return repository.findAll();
    }

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

    public List<LevelByUserDTO> findLevelsByUser(Long userId) {
        List<Level> levels = repository.findAll();
        User user = userRepository.findById(userId).get();
        List<LevelByUserDTO> levelsByUser = new ArrayList<>();

        for (Level level : levels) {
            Boolean isConcluded = user.getConcludedLevels().stream().map(UserConcludedLevel::getLevel).toList().contains(level);
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
            User user = userRepository.getReferenceById(data.userId());

            UserConcludedLevel concludedLevel = new UserConcludedLevel();
            concludedLevel.setLevel(level);
            concludedLevel.setTotalTime(data.totalTime());

            user.getConcludedLevels().add(concludedLevel);
            userRepository.save(user);
        }

        return success;
    }

    public Level save(LevelRegisterDTO data) {
        Level level = new Level();
        level.setIcon(data.icon());
        level.setTitle(data.title());
        level.setAnswers(data.answers());
        repository.save(level);

        return level;
    }
}
