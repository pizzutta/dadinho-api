package com.pgbd.dadinhoapi.service;

import com.pgbd.dadinhoapi.dto.BasketRegisterDTO;
import com.pgbd.dadinhoapi.dto.BasketUpdateDTO;
import com.pgbd.dadinhoapi.model.Basket;
import com.pgbd.dadinhoapi.model.Item;
import com.pgbd.dadinhoapi.model.Level;
import com.pgbd.dadinhoapi.repository.BasketRepository;
import com.pgbd.dadinhoapi.repository.ItemRepository;
import com.pgbd.dadinhoapi.repository.LevelRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BasketService {

    @Autowired
    private BasketRepository repository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private LevelRepository levelRepository;

    public Optional<Basket> findById(Long id) {
        return repository.findById(id);
    }

    public List<Basket> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Basket save(BasketRegisterDTO data) {
        Basket basket = new Basket();
        for (Long itemId : data.itemsIds()) {
            basket.getItems().add(itemRepository.findById(itemId).get());
        }
        repository.save(basket);

        Level level = levelRepository.findById(data.levelId()).get();
        level.getBaskets().add(basket);
        levelRepository.save(level);

        return basket;
    }

    @Transactional
    public Basket save(BasketUpdateDTO data) {
        Basket basket = repository.findById(data.id()).orElseThrow(EntityNotFoundException::new);

        List<Item> items = new ArrayList<>();
        for (Long itemId : data.itemsIds()) {
            items.add(itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new));
        }
        basket.setItems(items);

        repository.save(basket);

        return basket;
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
