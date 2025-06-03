package com.pgbd.dadinhoapi.service;

import com.pgbd.dadinhoapi.dto.ItemRegisterDTO;
import com.pgbd.dadinhoapi.dto.ItemUpdateDTO;
import com.pgbd.dadinhoapi.model.Item;
import com.pgbd.dadinhoapi.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;

    public Optional<Item> findById(Long id) {
        return repository.findById(id);
    }

    public List<Item> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Item save(ItemRegisterDTO data) {
        Item item = new Item();
        item.setIcon(data.icon());
        item.setName(data.name());

        return repository.save(item);
    }

    @Transactional
    public Item save(ItemUpdateDTO data) {
        Item item = repository.findById(data.id()).orElseThrow(EntityNotFoundException::new);
        item.setIcon(data.icon());
        item.setName(data.name());

        return repository.save(item);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
