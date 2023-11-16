package com.pgbd.dadinhoapi.controller;

import com.pgbd.dadinhoapi.dto.ItemRegisterDTO;
import com.pgbd.dadinhoapi.model.Item;
import com.pgbd.dadinhoapi.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService service;

    @GetMapping
    public ResponseEntity findAllItems() {
        List<Item> items = service.findAll();
        return !items.isEmpty() ? ResponseEntity.ok(items) : ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity saveItem(@RequestBody @Valid ItemRegisterDTO data) {
        Item item = service.save(data);
        return ResponseEntity.created(URI.create("/item/" + item.getId())).build();
    }
}
