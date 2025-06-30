package com.pgbd.dadinhoapi.game.model;

import com.pgbd.dadinhoapi.model.Basket;
import com.pgbd.dadinhoapi.model.Item;

public class Command {
    Actions action;
    Integer quantity;
    Item item;
    Basket basket;

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }
}
