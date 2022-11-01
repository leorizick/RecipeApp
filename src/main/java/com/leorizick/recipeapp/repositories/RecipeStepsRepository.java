package com.leorizick.recipeapp.repositories;

import com.leorizick.recipeapp.entities.recipe.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeStepsRepository extends JpaRepository<RecipeStep, Long> {
}
