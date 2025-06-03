package com.pgbd.dadinhoapi.controller;

import com.pgbd.dadinhoapi.dto.IdDTO;
import com.pgbd.dadinhoapi.dto.ItemRegisterDTO;
import com.pgbd.dadinhoapi.dto.ItemUpdateDTO;
import com.pgbd.dadinhoapi.model.Item;
import com.pgbd.dadinhoapi.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class ItemController {

    @Autowired
    private ItemService service;

    @GetMapping
    public ResponseEntity<List<Item>> getAll() {
        List<Item> items = service.findAll();
        return items.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getById(@PathVariable Long id) {
        Optional<Item> item = service.findById(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Item> save(@RequestBody @Valid ItemRegisterDTO data) {
        Item item = service.save(data);
        return ResponseEntity.created(URI.create("/item/" + item.getId())).body(item);
    }

    @PutMapping
    public ResponseEntity<Item> update(@RequestBody @Valid ItemUpdateDTO data) {
        Item item = service.save(data);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody @Valid IdDTO data) {
        service.deleteById(data.id());
        return ResponseEntity.noContent().build();
    }
}
