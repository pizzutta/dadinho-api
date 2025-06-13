package com.pgbd.dadinhoapi.game;

import com.pgbd.dadinhoapi.game.model.Command;
import com.pgbd.dadinhoapi.game.model.Result;
import com.pgbd.dadinhoapi.model.Basket;
import com.pgbd.dadinhoapi.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pgbd.dadinhoapi.game.model.Status.INSUFFICIENT_ITEMS_ON_BASKET;

public class GameCommandInterpreter {

    public static void run(List<Command> commands, Result result) {
        Map<Item, Integer> finalBasket = new HashMap<>();

        for (Command command : commands) {
            switch (command.getAction()) {
                case PEGUE -> handleSelect(result, command, finalBasket);
                case REMOVA -> handleDelete(result, command, finalBasket);
            }
        }

        result.setFinalBasket(finalBasket);
    }

    private static void handleSelect(Result result, Command command, Map<Item, Integer> finalBasket) {
        Basket basket = copyBasket(command.getBasket());
        Item item = command.getItem();
        finalBasket.putIfAbsent(item, 0);

        if (command.getQuantity() != null) {
            for (int i = 0; i < command.getQuantity(); i++) {
                if (!basket.getItems().contains(item)) {
                    result.setStatus(INSUFFICIENT_ITEMS_ON_BASKET);
                    result.setErrorDetail(basket.getName());
                    break;
                } else {
                    int index = basket.getItems().indexOf(item);
                    putItemIntoFinalBasket(basket, index, finalBasket);
                }
            }
        } else {
            while (basket.getItems().contains(item)) {
                int index = basket.getItems().indexOf(item);
                putItemIntoFinalBasket(basket, index, finalBasket);
            }
        }
    }

    private static void handleDelete(Result result, Command command, Map<Item, Integer> finalBasket) {
        Item item = command.getItem();
        Integer commandQuantity = command.getQuantity();
        Integer basketQuantity = finalBasket.get(item);
        if (commandQuantity > basketQuantity) {
            result.setStatus(INSUFFICIENT_ITEMS_ON_BASKET);
            result.setErrorDetail("Seu Cesto");
        } else {
            finalBasket.put(item, basketQuantity - commandQuantity);
        }
    }

    private static Basket copyBasket(Basket original) {
        Basket copy = new Basket();
        copy.setId(original.getId()); // manter ID se necessário para referência
        copy.setItems(new ArrayList<>(original.getItems())); // shallow copy suficiente
        return copy;
    }

    private static void putItemIntoFinalBasket(Basket basket, int index, Map<Item, Integer> result) {
        Item foundItem = basket.getItems().remove(index);
        result.put(foundItem, result.get(foundItem) + 1);
    }
}
