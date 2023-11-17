package com.pgbd.dadinhoapi.repository;

import com.pgbd.dadinhoapi.model.ItemRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRecipeRepository extends JpaRepository<ItemRecipe, Long> {
}
