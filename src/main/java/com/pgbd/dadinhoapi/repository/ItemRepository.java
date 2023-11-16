package com.pgbd.dadinhoapi.repository;

import com.pgbd.dadinhoapi.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
