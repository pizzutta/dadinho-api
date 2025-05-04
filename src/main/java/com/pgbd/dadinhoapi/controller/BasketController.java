package com.pgbd.dadinhoapi.controller;

import com.pgbd.dadinhoapi.dto.BasketRegisterDTO;
import com.pgbd.dadinhoapi.dto.BasketUpdateDTO;
import com.pgbd.dadinhoapi.dto.IdDTO;
import com.pgbd.dadinhoapi.model.Basket;
import com.pgbd.dadinhoapi.service.BasketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/basket")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class BasketController {

    @Autowired
    private BasketService service;

    @GetMapping
    public ResponseEntity<List<Basket>> getAll() {
        List<Basket> baskets = service.findAll();
        return baskets.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(baskets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Basket> getById(@PathVariable Long id) {
        Optional<Basket> basket = service.findById(id);
        return basket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Basket> save(@RequestBody @Valid BasketRegisterDTO data) {
        Basket basket = service.save(data);
        return ResponseEntity.created(URI.create("/basket/" + basket.getId())).body(basket);
    }

    @PutMapping
    public ResponseEntity<Basket> update(@RequestBody @Valid BasketUpdateDTO data) {
        Basket basket = service.save(data);
        return ResponseEntity.ok(basket);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody @Valid IdDTO data) {
        service.deleteById(data.id());
        return ResponseEntity.noContent().build();
    }
}
