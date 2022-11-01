package com.leorizick.recipeapp.services.api.service.recipe;

import com.leorizick.recipeapp.dto.recipe.RecipeCreationRequest;
import com.leorizick.recipeapp.dto.recipe.RecipeCrudResponse;
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

    public RecipeCrudResponse findById(Long id) {
        Recipe recipe = recipeCrud.findById(id);
        return modelMapper.map(recipe, RecipeCrudResponse.class);
    }

    public Page<RecipeCrudResponse> findAll(Pageable pageable) {
        Page<Recipe> recipePage = recipeCrud.findAll(pageable);
        return recipePage.map(recipe -> modelMapper.map(recipe, RecipeCrudResponse.class));
    }

    @Transactional
    public RecipeCrudResponse create(RecipeCreationRequest recipeCreationRequest) {
        Recipe recipe = modelMapper.map(recipeCreationRequest, Recipe.class);
        recipe = recipeCrud.create(recipe);
        return modelMapper.map(recipe, RecipeCrudResponse.class);
    }

    public RecipeCrudResponse update(Long id, RecipeCreationRequest recipeCreationRequest) {
        Recipe recipe = recipeCrud.findById(id);
        modelMapper.map(recipeCreationRequest, recipe);
        recipe = recipeCrud.create(recipe);
        return modelMapper.map(recipe, RecipeCrudResponse.class);
    }

    @Transactional
    public void deleteById(Long id) {
        recipeCrud.deleteById(id);

    }

}
