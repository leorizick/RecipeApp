package com.leorizick.recipeapp.repositories.recipe;

import com.leorizick.recipeapp.entities.recipe.Comment;
import com.leorizick.recipeapp.entities.recipe.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Override
    @Query("select r from Recipe r where r.enabled = true")
    Page<Recipe> findAll(Pageable pageable);

    @Query("select r from Recipe r where r.author.id = :accountId and r.enabled = true")
    Page<Recipe> findAllByAccountId(Long accountId, Pageable pageable);


}
