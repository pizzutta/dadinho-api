package com.pgbd.dadinhoapi.service;

import com.pgbd.dadinhoapi.dto.ItemRecipeRegisterDTO;
import com.pgbd.dadinhoapi.model.ItemRecipe;
import com.pgbd.dadinhoapi.model.Level;
import com.pgbd.dadinhoapi.repository.ItemRecipeRepository;
import com.pgbd.dadinhoapi.repository.ItemRepository;
import com.pgbd.dadinhoapi.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemRecipeService {

    @Autowired
    private ItemRecipeRepository repository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private LevelRepository levelRepository;

    public ItemRecipe save(ItemRecipeRegisterDTO data) {
        ItemRecipe itemRecipe = new ItemRecipe();
        itemRecipe.setItem(itemRepository.findById(data.itemId()).get());
        itemRecipe.setQuantity(data.quantity());
        repository.save(itemRecipe);

        Level level = levelRepository.findById(data.levelId()).get();
        level.getRecipe().add(itemRecipe);
        levelRepository.save(level);

        return itemRecipe;
    }
}
