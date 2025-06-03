package com.pgbd.dadinhoapi.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name = "tb_item_recipe")
public class ItemRecipe {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @Column
    private Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
