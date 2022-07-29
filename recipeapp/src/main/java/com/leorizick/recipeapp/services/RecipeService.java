package com.leorizick.recipeapp.services;

import com.leorizick.recipeapp.dto.RecipeDto;
import com.leorizick.recipeapp.entities.Recipe;
import com.leorizick.recipeapp.repositories.RecipeRepository;
import com.leorizick.recipeapp.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository repository;

    public Recipe find(Long id) {
        Optional<Recipe> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Receita não encontrada! Id: " + id));
    }

    public Page<Recipe> findAll(Pageable pageable) {
        Page<Recipe> recipePages = repository.findAll(pageable);
        return recipePages;
    }

    @Transactional
    public Recipe save(RecipeDto recipeDto) {
        Recipe recipe = transformDto(recipeDto);
        repository.save(recipe);
        return (recipe);
    }

    @Transactional
    public Recipe update(RecipeDto recipeDto) {
        Recipe updatedRecipe = find(recipeDto.getId());
        updateData(recipeDto, updatedRecipe);
        return repository.save(updatedRecipe);
    }

    @Transactional
    public void delete(Long id) {
        Recipe recipe = find(id);
        recipe.setIsActive(false);
        repository.save(recipe);

    }

    private void updateData(RecipeDto recipeDto, Recipe updatedRecipe) {
        updatedRecipe.setName(recipeDto.getName());
        updatedRecipe.setDescription(recipeDto.getDescription());
        updatedRecipe.setIngredients(recipeDto.getIngredients());
        updatedRecipe.setSteps(recipeDto.getSteps());
        updatedRecipe.setIsActive(true);
        updatedRecipe.setCategory(recipeDto.getCategory());
    }

    private Recipe transformDto(RecipeDto recipeDto) {
        Recipe recipe = Recipe.builder()
                .id(recipeDto.getId())
                .name(recipeDto.getName())
                .description(recipeDto.getDescription())
                .ingredients(recipeDto.getIngredients())
                .steps(recipeDto.getSteps())
                .build();
        return recipe;
    }
}
