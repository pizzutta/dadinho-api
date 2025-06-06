package com.pgbd.dadinhoapi.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name = "tb_level")
public class Level {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column
    private String icon;
    @Column
    private String title;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "level_id")
    private List<ItemRecipe> recipe = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "level_id")
    private List<Basket> baskets = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ItemRecipe> getRecipe() {
        return recipe;
    }

    public void setRecipe(List<ItemRecipe> recipe) {
        this.recipe = recipe;
    }

    public List<Basket> getBaskets() {
        return baskets;
    }

    public void setBaskets(List<Basket> baskets) {
        this.baskets = baskets;
    }
}
