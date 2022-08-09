package com.leorizick.recipeapp.repositories;

import com.leorizick.recipeapp.entities.RecipeSteps;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeStepsRepository extends JpaRepository<RecipeSteps, Long> {
}
