package com.pgbd.dadinhoapi.controller;

import com.pgbd.dadinhoapi.dto.ItemRecipeRegisterDTO;
import com.pgbd.dadinhoapi.model.ItemRecipe;
import com.pgbd.dadinhoapi.service.ItemRecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/item-recipe")
public class ItemRecipeController {

    @Autowired
    private ItemRecipeService service;

    @PostMapping
    public ResponseEntity saveItem(@RequestBody @Valid ItemRecipeRegisterDTO data) {
        ItemRecipe itemRecipe = service.save(data);
        return ResponseEntity.created(URI.create("/item-recipe/" + itemRecipe.getId())).build();
    }
}
