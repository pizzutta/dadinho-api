package com.pgbd.dadinhoapi.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name = "tb_basket")
public class Basket {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "tb_basket_item",
            joinColumns = @JoinColumn(name = "basket_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
