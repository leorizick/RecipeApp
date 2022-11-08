package com.leorizick.recipeapp.repositories.recipe;

import com.leorizick.recipeapp.entities.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Long> {

}