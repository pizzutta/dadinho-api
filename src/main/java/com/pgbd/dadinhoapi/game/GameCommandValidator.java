package com.pgbd.dadinhoapi.game;

import com.pgbd.dadinhoapi.dto.UserAnswerDTO;
import com.pgbd.dadinhoapi.game.model.Command;
import com.pgbd.dadinhoapi.game.model.Result;
import com.pgbd.dadinhoapi.model.Basket;
import com.pgbd.dadinhoapi.model.Item;
import com.pgbd.dadinhoapi.model.ItemRecipe;
import com.pgbd.dadinhoapi.model.Level;
import com.pgbd.dadinhoapi.service.ItemService;
import com.pgbd.dadinhoapi.service.LevelService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.pgbd.dadinhoapi.game.model.Actions.valueOf;
import static com.pgbd.dadinhoapi.game.model.ValidationStatus.INVALID_BASKET;
import static com.pgbd.dadinhoapi.game.model.ValidationStatus.SYNTAX_ERROR;
import static java.util.stream.Collectors.toMap;

@Component
@Scope("prototype")
public class GameCommandValidator {

    private final LevelService levelService;
    private final ItemService itemService;

    private List<Basket> baskets;
    private List<Item> items;

    private Map<Item, Integer> expected;
    private Map<Item, Integer> actual;

    private final Result result = new Result();
    private final Command command = new Command();

    public GameCommandValidator(LevelService levelService, ItemService itemService) {
        this.levelService = levelService;
        this.itemService = itemService;
    }

    public Result run(UserAnswerDTO data) {
        this.setup(data);
        this.handleAction(data.userAnswers().get(0));
        return this.result;
    }

    private void setup(UserAnswerDTO data) {
        Level level = levelService.findById(data.levelId()).orElseThrow(EntityNotFoundException::new);
        items = itemService.findAll();

        baskets = level.getBaskets();
        expected = level.getRecipe().stream().collect(toMap(ItemRecipe::getItem, ItemRecipe::getQuantity));
    }

    private void handleAction(String commandString) {
        int firstSpace = commandString.indexOf(' ');
        String action = commandString.substring(0, firstSpace).toUpperCase();

        switch (action) {
            case "PEGUE", "REMOVA" -> command.setAction(valueOf(action));
            default -> {
                result.setStatus(SYNTAX_ERROR);
                return;
            }
        }

        handleQuantity(commandString.substring(firstSpace + 1));
    }

    private void handleQuantity(String commandString) {
        int firstSpace = commandString.indexOf(' ');
        String quantity = commandString.substring(0, firstSpace).toUpperCase();

        switch (quantity) {
            case "TODOS", "TODAS" -> {
                String newCommandString = commandString.substring(firstSpace + 1);
                int nextSpace = newCommandString.indexOf(' ');
                String article = newCommandString.substring(0, nextSpace).toUpperCase();

                switch (article) {
                    case "OS", "AS" -> {
                        commandString = newCommandString;
                        firstSpace = nextSpace;
                    }
                    default -> {
                        result.setStatus(SYNTAX_ERROR);
                        return;
                    }
                }
            }
            case "UM", "UMA" -> {
                command.setQuantity(1);
            }
            case "DOIS", "DUAS" -> {
                command.setQuantity(2);
            }
            case "TRÊS" -> {
                command.setQuantity(3);
            }
            case "QUATRO" -> {
                command.setQuantity(4);
            }
            default -> {
                result.setStatus(SYNTAX_ERROR);
                return;
            }
        }

        handleItem(commandString.substring(firstSpace + 1));
    }

    private void handleItem(String commandString) {
        int firstSpace = commandString.indexOf(' ');
        String item = commandString.substring(0, firstSpace).toUpperCase();
        boolean isValid = validateItem(item);

        if (!isValid) {
            return;
        }

        handleBasket(commandString.substring(firstSpace + 1));
    }

    private void handleBasket(String commandString) {
        int firstSpace = commandString.indexOf(' ');
        String basket = commandString.substring(0, firstSpace).toUpperCase();

        if (!basket.equalsIgnoreCase("DO")) {
            result.setStatus(SYNTAX_ERROR);
            return;
        }

        commandString = commandString.substring(firstSpace + 1);
        basket = commandString.toUpperCase();

        switch (command.getAction()) {
            case PEGUE -> {
                if (basket.startsWith("CESTO")) {
                    validateBasket(basket);
                } else {
                    if (basket.equalsIgnoreCase("MEU CESTO")) {
                        result.setStatus(INVALID_BASKET);
                    } else {
                        result.setStatus(SYNTAX_ERROR);
                    }
                }
            }
            case REMOVA -> {
                if (!basket.equalsIgnoreCase("MEU CESTO")) {
                    result.setStatus(INVALID_BASKET);
                }
            }
        }

    }

    private boolean validateItem(String item) {
        Optional<Item> optionalItem =
                items.stream().filter(it -> it.getName().equalsIgnoreCase(item) || it.getIcon().equals(item)).findFirst();

        optionalItem.ifPresentOrElse(
                it -> command.setItem(it),
                () -> result.setStatus(SYNTAX_ERROR)
        );

        return optionalItem.isPresent();
    }

    private boolean validateBasket(String basket) {
        Optional<Basket> optionalBasket = baskets.stream().filter(it -> it.getName().equalsIgnoreCase(basket)).findFirst();

        optionalBasket.ifPresentOrElse(
                it -> command.setBasket(it),
                () -> result.setStatus(INVALID_BASKET)
        );

        return optionalBasket.isPresent();
    }
}
