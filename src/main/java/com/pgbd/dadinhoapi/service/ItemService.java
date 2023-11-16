package com.pgbd.dadinhoapi.service;

import com.pgbd.dadinhoapi.dto.ItemRegisterDTO;
import com.pgbd.dadinhoapi.model.Item;
import com.pgbd.dadinhoapi.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;

    public List<Item> findAll() {
        return repository.findAll();
    }

    public Item save(ItemRegisterDTO data) {
        Item item = new Item();
        item.setIcon(data.icon());
        item.setName(data.name());

        return repository.save(item);
    }
}
