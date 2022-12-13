package com.leorizick.recipeapp.services.api.service.recipe;

import com.leorizick.recipeapp.dto.recipe.RecipeCreationRequest;
import com.leorizick.recipeapp.dto.recipe.RecipeCrudResponse;
import com.leorizick.recipeapp.dto.recipe.RecipeSummaryResponse;
import com.leorizick.recipeapp.entities.recipe.Ingredient;
import com.leorizick.recipeapp.entities.recipe.Recipe;
import com.leorizick.recipeapp.services.domain.service.recipe.IngredientCrud;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeCrud;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeStepCrud;
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

    private final RecipeStepApiService recipeStepApiService;
    private final IngredientApiService ingredientApiService;

    private final RecipeStepCrud recipeStepCrud;
    private final IngredientCrud ingredientCrud;

    public RecipeCrudResponse findById(Long id) {
        Recipe recipe = recipeCrud.findById(id);
        return modelMapper.map(recipe, RecipeCrudResponse.class);
    }

    public Page<RecipeSummaryResponse> findAll(Pageable pageable) {
        Page<Recipe> recipePage = recipeCrud.findAll(pageable);
        return recipePage.map(recipe -> modelMapper.map(recipe, RecipeSummaryResponse.class));
    }

    @Transactional
    public RecipeCrudResponse create(RecipeCreationRequest recipeCreationRequest) {
        Recipe recipe = modelMapper.map(recipeCreationRequest, Recipe.class);
        recipe = recipeCrud.save(recipe);
        recipeCrud.saveStepsAndIngredients(recipe);
        return modelMapper.map(recipe, RecipeCrudResponse.class);
    }

    public RecipeCrudResponse update(Long id, RecipeCreationRequest recipeCreationRequest) {
        Recipe recipe = recipeCrud.findById(id);
        modelMapper.map(recipeCreationRequest, recipe);
        ingredientCrud.DeleteByRecipeId(id);
        recipeStepCrud.DeleteByRecipeId(id);
        recipeCreationRequest.getIngredients().forEach(ingredientCreationRequest -> ingredientApiService.create(id, ingredientCreationRequest));
        recipeCreationRequest.getSteps().forEach(recipeStepCreationRequest -> recipeStepApiService.create(id,recipeStepCreationRequest));
        recipe = recipeCrud.save(recipe);
        return modelMapper.map(recipe, RecipeCrudResponse.class);
    }

    @Transactional
    public void deleteById(Long id) {
        recipeCrud.deleteById(id);

    }

    public Page<RecipeSummaryResponse> findAllByAccountId(Pageable pageable) {
        Page<Recipe> recipePage = recipeCrud.findAllByAccountId(pageable);
        return recipePage.map(recipe -> modelMapper.map(recipe, RecipeSummaryResponse.class));
    }
}
