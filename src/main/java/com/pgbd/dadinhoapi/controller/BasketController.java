package com.pgbd.dadinhoapi.controller;

import com.pgbd.dadinhoapi.dto.BasketRegisterDTO;
import com.pgbd.dadinhoapi.model.Basket;
import com.pgbd.dadinhoapi.service.BasketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/basket")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class BasketController {

    @Autowired
    private BasketService service;

    @PostMapping
    public ResponseEntity saveBasket(@RequestBody @Valid BasketRegisterDTO data) {
        Basket basket = service.save(data);
        return ResponseEntity.created(URI.create("/basket/" + basket.getId())).build();
    }
}
