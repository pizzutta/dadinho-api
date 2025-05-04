package com.pgbd.dadinhoapi.service;

import com.pgbd.dadinhoapi.dto.ItemRecipeRegisterDTO;
import com.pgbd.dadinhoapi.dto.ItemRecipeUpdateDTO;
import com.pgbd.dadinhoapi.model.Item;
import com.pgbd.dadinhoapi.model.ItemRecipe;
import com.pgbd.dadinhoapi.model.Level;
import com.pgbd.dadinhoapi.repository.ItemRecipeRepository;
import com.pgbd.dadinhoapi.repository.ItemRepository;
import com.pgbd.dadinhoapi.repository.LevelRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemRecipeService {

    @Autowired
    private ItemRecipeRepository repository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private LevelRepository levelRepository;

    public Optional<ItemRecipe> findById(Long id) {
        return repository.findById(id);
    }

    public List<ItemRecipe> findAll() {
        return repository.findAll();
    }

    @Transactional
    public ItemRecipe save(ItemRecipeRegisterDTO data) {
        ItemRecipe itemRecipe = new ItemRecipe();
        Item item = itemRepository.findById(data.itemId()).orElseThrow(EntityNotFoundException::new);
        Level level = levelRepository.findById(data.levelId()).orElseThrow(EntityNotFoundException::new);

        itemRecipe.setItem(item);
        itemRecipe.setQuantity(data.quantity());
        repository.save(itemRecipe);

        level.getRecipe().add(itemRecipe);
        levelRepository.save(level);

        return itemRecipe;
    }

    @Transactional
    public ItemRecipe save(ItemRecipeUpdateDTO data) {
        ItemRecipe itemRecipe = repository.findById(data.id()).orElseThrow(EntityNotFoundException::new);
        Item item = itemRepository.findById(data.itemId()).orElseThrow(EntityNotFoundException::new);

        itemRecipe.setItem(item);
        itemRecipe.setQuantity(data.quantity());
        repository.save(itemRecipe);

        return itemRecipe;
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
