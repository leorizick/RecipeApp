package com.leorizick.recipeapp.repositories;

import com.leorizick.recipeapp.entities.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
