package com.pgbd.dadinhoapi.repository;

import com.pgbd.dadinhoapi.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}
