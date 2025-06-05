package com.pgbd.dadinhoapi.service;

import com.pgbd.dadinhoapi.dto.UserAnswerDTO;
import com.pgbd.dadinhoapi.game.model.Result;
import com.pgbd.dadinhoapi.model.Item;
import com.pgbd.dadinhoapi.model.Level;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.pgbd.dadinhoapi.game.GameCommandValidator.validate;

@Service
public class GameService {

    @Autowired
    private LevelService levelService;
    @Autowired
    private ItemService itemService;

    public Result submit(UserAnswerDTO data) {
        Level level = levelService.findById(data.levelId()).orElseThrow(EntityNotFoundException::new);
        List<Item> allItems = itemService.findAll();
        return validate(data, level, allItems);
    }

}
