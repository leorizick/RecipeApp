package com.leorizick.recipeapp.services.api.service.recipe;

import com.leorizick.recipeapp.dto.recipe.RecipeCreationRequest;
import com.leorizick.recipeapp.dto.recipe.RecipeCreationResponse;
import com.leorizick.recipeapp.entities.recipe.Recipe;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeCrud;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class RecipeApiService {
    private final RecipeCrud recipeCrud;
    private final ModelMapper modelMapper;

    public Recipe find(Long id) {
        return recipeCrud.find(id);
    }

    public Page<Recipe> findAll(Pageable pageable) {
        return recipeCrud.findAll(pageable);
    }

    @Transactional
    public RecipeCreationResponse save(RecipeCreationRequest recipeCreationRequest) {
        Recipe recipe = modelMapper.map(recipeCreationRequest, Recipe.class);
        recipe = recipeCrud.save(recipe);
        return modelMapper.map(recipe, RecipeCreationResponse.class);
    }

    @Transactional
    public void delete(Long id) {
        Recipe recipe = find(id);
        recipe.setEnabled(false);
        recipeCrud.save(recipe);

    }

    public Recipe updateRecipe(RecipeCreationRequest recipeCreationRequest) {
        return null;
    }
}
