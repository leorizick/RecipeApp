package com.leorizick.recipeapp.repositories.calculator;

import com.leorizick.recipeapp.entities.calculator.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
