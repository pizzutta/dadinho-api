package com.pgbd.dadinhoapi.game;

import com.pgbd.dadinhoapi.dto.UserAnswerDTO;
import com.pgbd.dadinhoapi.model.Basket;
import com.pgbd.dadinhoapi.model.Item;
import com.pgbd.dadinhoapi.model.ItemRecipe;
import com.pgbd.dadinhoapi.model.Level;
import com.pgbd.dadinhoapi.service.LevelService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Component
public class GameCommandValidator {

    private final LevelService levelService;

    private List<Basket> baskets;

    private Map<Item, Integer> expected;
    private Map<Item, Integer> actual;

    public GameCommandValidator(LevelService levelService) {
        this.levelService = levelService;
    }

    public void run(UserAnswerDTO data) {
        this.setup(data);
    }

    private void setup(UserAnswerDTO data) {
        Level level = levelService.findById(data.levelId()).orElseThrow(EntityNotFoundException::new);

        baskets = level.getBaskets();
        expected = level.getRecipe().stream().collect(toMap(ItemRecipe::getItem, ItemRecipe::getQuantity));
    }
}
