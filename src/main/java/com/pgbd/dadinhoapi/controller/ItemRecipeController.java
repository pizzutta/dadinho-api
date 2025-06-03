package com.pgbd.dadinhoapi.controller;

import com.pgbd.dadinhoapi.dto.IdDTO;
import com.pgbd.dadinhoapi.dto.ItemRecipeRegisterDTO;
import com.pgbd.dadinhoapi.dto.ItemRecipeUpdateDTO;
import com.pgbd.dadinhoapi.model.ItemRecipe;
import com.pgbd.dadinhoapi.service.ItemRecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item-recipe")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class ItemRecipeController {

    @Autowired
    private ItemRecipeService service;

    @GetMapping
    public ResponseEntity<List<ItemRecipe>> getAll() {
        List<ItemRecipe> itemRecipes = service.findAll();
        return itemRecipes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(itemRecipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemRecipe> getById(@PathVariable Long id) {
        Optional<ItemRecipe> itemRecipe = service.findById(id);
        return itemRecipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ItemRecipe> save(@RequestBody @Valid ItemRecipeRegisterDTO data) {
        ItemRecipe itemRecipe = service.save(data);
        return ResponseEntity.created(URI.create("/item-recipe/" + itemRecipe.getId())).body(itemRecipe);
    }

    @PutMapping
    public ResponseEntity<ItemRecipe> update(@RequestBody @Valid ItemRecipeUpdateDTO data) {
        ItemRecipe basket = service.save(data);
        return ResponseEntity.ok(basket);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody @Valid IdDTO data) {
        service.deleteById(data.id());
        return ResponseEntity.noContent().build();
    }
}
