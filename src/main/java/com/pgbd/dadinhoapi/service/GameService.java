package com.pgbd.dadinhoapi.service;

import com.pgbd.dadinhoapi.dto.UserAnswerDTO;
import com.pgbd.dadinhoapi.game.model.Result;
import com.pgbd.dadinhoapi.model.Item;
import com.pgbd.dadinhoapi.model.Level;
import com.pgbd.dadinhoapi.model.User;
import com.pgbd.dadinhoapi.model.UserLevelMetrics;
import com.pgbd.dadinhoapi.repository.ItemRepository;
import com.pgbd.dadinhoapi.repository.LevelRepository;
import com.pgbd.dadinhoapi.repository.UserLevelMetricsRepository;
import com.pgbd.dadinhoapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.pgbd.dadinhoapi.game.GameCommandValidator.validate;

@Service
public class GameService {

    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserLevelMetricsRepository userLevelMetricsRepository;

    public Result submit(UserAnswerDTO data) {
        Level level = levelRepository.findById(data.levelId()).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findById(data.userId()).orElseThrow(EntityNotFoundException::new);
        List<Item> allItems = itemRepository.findAll();

        Result result = validate(data.userAnswers(), level, allItems);
        saveMetrics(user, level, result, data.totalTime());

        return result;
    }

    private void saveMetrics(User user, Level level, Result result, Integer totalTime) {
        UserLevelMetrics metrics =
                userLevelMetricsRepository.findByUser_IdAndLevel_Id(user.getId(), level.getId()).orElseGet(UserLevelMetrics::new);

        metrics.setUser(user);
        metrics.setLevel(level);

        if (result.isValid()) {
            metrics.setConcluded(true);
            metrics.setTotalTime(totalTime);
        } else {
            metrics.setConcluded(false);
            metrics.setAttempts(metrics.getAttempts() != null ? metrics.getAttempts() + 1 : 1);
        }

        userLevelMetricsRepository.save(metrics);
    }

}
