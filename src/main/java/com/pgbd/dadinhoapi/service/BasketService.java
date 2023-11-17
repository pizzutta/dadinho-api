package com.pgbd.dadinhoapi.service;

import com.pgbd.dadinhoapi.dto.BasketRegisterDTO;
import com.pgbd.dadinhoapi.model.Basket;
import com.pgbd.dadinhoapi.model.Level;
import com.pgbd.dadinhoapi.repository.BasketRepository;
import com.pgbd.dadinhoapi.repository.ItemRepository;
import com.pgbd.dadinhoapi.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {

    @Autowired
    private BasketRepository repository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private LevelRepository levelRepository;

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
}
