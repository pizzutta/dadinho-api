package com.pgbd.dadinhoapi.game;

import com.pgbd.dadinhoapi.dto.UserAnswerDTO;
import com.pgbd.dadinhoapi.game.model.Command;
import com.pgbd.dadinhoapi.game.model.Result;
import com.pgbd.dadinhoapi.model.Basket;
import com.pgbd.dadinhoapi.model.Item;
import com.pgbd.dadinhoapi.model.ItemRecipe;
import com.pgbd.dadinhoapi.model.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.pgbd.dadinhoapi.game.GameCommandInterpreter.run;
import static com.pgbd.dadinhoapi.game.model.Actions.valueOf;
import static com.pgbd.dadinhoapi.game.model.Status.*;
import static java.util.stream.Collectors.toMap;

public class GameCommandValidator {

    private static List<Basket> baskets;
    private static List<Item> items;

    public static Result validate(UserAnswerDTO data, Level level, List<Item> allItems) {
        Result result = new Result();
        setup(result, level, allItems);

        List<Command> commands = new ArrayList<>();
        for (String answer : data.userAnswers()) {
            Command command = new Command();
            handleAction(answer, command, result);
            commands.add(command);
        }

        if (!result.isValid()) return result;

        run(commands, result);
        if (!result.isValid()) return result;

        validateResult(result);
        return result;
    }

    private static void setup(Result result, Level level, List<Item> allItems) {
        items = allItems;
        baskets = level.getBaskets();

        Map<Item, Integer> expected = level.getRecipe().stream().collect(toMap(ItemRecipe::getItem,
                ItemRecipe::getQuantity));
        result.setExpected(expected);
    }

    private static void handleAction(String commandString, Command command, Result result) {
        int firstSpace = commandString.indexOf(' ');
        String action = commandString.substring(0, firstSpace).toUpperCase();

        switch (action) {
            case "PEGUE", "REMOVA" -> command.setAction(valueOf(action));
            default -> {
                result.setStatus(SYNTAX_ERROR);
                return;
            }
        }

        handleQuantity(commandString.substring(firstSpace + 1), command, result);
    }

    private static void handleQuantity(String commandString, Command command, Result result) {
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
            case "UM", "UMA" -> command.setQuantity(1);
            case "DOIS", "DUAS" -> command.setQuantity(2);
            case "TRÊS" -> command.setQuantity(3);
            case "QUATRO" -> command.setQuantity(4);
            default -> {
                result.setStatus(SYNTAX_ERROR);
                return;
            }
        }

        handleItem(commandString.substring(firstSpace + 1), command, result);
    }

    private static void handleItem(String commandString, Command command, Result result) {
        int firstSpace = commandString.indexOf(' ');
        String item = commandString.substring(0, firstSpace).toUpperCase();
        boolean isValid = validateItem(item, command, result);

        if (!isValid) {
            return;
        }

        handleBasket(commandString.substring(firstSpace + 1), command, result);
    }

    private static void handleBasket(String commandString, Command command, Result result) {
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
                    validateBasket(basket, command, result);
                } else {
                    if (basket.equalsIgnoreCase("MEU CESTO")) {
                        result.setStatus(INVALID_BASKET);
                        result.setErrorDetail(basket);
                    } else {
                        result.setStatus(SYNTAX_ERROR);
                    }
                }
            }
            case REMOVA -> {
                if (!basket.equalsIgnoreCase("MEU CESTO")) {
                    result.setStatus(INVALID_BASKET);
                    result.setErrorDetail(basket);
                }
            }
        }

    }

    private static boolean validateItem(String item, Command command, Result result) {
        Optional<Item> optionalItem =
                items.stream().filter(it -> it.getName().equalsIgnoreCase(item) || it.getIcon().equals(item)).findFirst();

        optionalItem.ifPresentOrElse(
                command::setItem,
                () -> result.setStatus(SYNTAX_ERROR)
        );

        return optionalItem.isPresent();
    }

    private static boolean validateBasket(String basket, Command command, Result result) {
        Optional<Basket> optionalBasket = baskets.stream().filter(it -> it.getName().equalsIgnoreCase(basket)).findFirst();

        optionalBasket.ifPresentOrElse(
                command::setBasket,
                () -> {
                    result.setStatus(INVALID_BASKET);
                    result.setErrorDetail(basket);
                }
        );

        return optionalBasket.isPresent();
    }

    private static void validateResult(Result result) {
        Map<Item, Integer> expected = result.getExpected();
        Map<Item, Integer> finalBasket = result.getFinalBasket();

        expected.forEach((item, expectedQuantity) -> {
            Integer resultQuantity = finalBasket.get(item);
            if (resultQuantity.equals(expectedQuantity)) {
                result.setStatus(CORRECT);
            } else {
                result.setStatus(INCORRECT);
            }
        });
    }
}
