package com.pgbd.dadinhoapi.dto;

import com.pgbd.dadinhoapi.model.Basket;
import com.pgbd.dadinhoapi.model.ItemRecipe;

import java.util.List;

public record LevelSetupDTO(
        Long id,
        String icon,
        String title,
        List<ItemRecipe> recipe,
        List<Basket> baskets,
        List<String> options
) {
}
