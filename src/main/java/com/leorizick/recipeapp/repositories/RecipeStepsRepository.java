package com.leorizick.recipeapp.repositories;

import com.leorizick.recipeapp.entities.recipe.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RecipeStepsRepository extends JpaRepository<RecipeStep, Long> {

    @Modifying
    @Query("delete from RecipeStep rs where rs.recipe.id = :recipeId")
    void deleteByRecipeId(Long recipeId);
}
