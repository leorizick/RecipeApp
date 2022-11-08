package com.leorizick.recipeapp.services.api.service.recipe;

import com.leorizick.recipeapp.dto.recipe.IngredientCreationRequest;
import com.leorizick.recipeapp.dto.recipe.IngredientCrudResponse;
import com.leorizick.recipeapp.dto.recipe.RecipeStepCreationRequest;
import com.leorizick.recipeapp.dto.recipe.RecipeStepCrudResponse;
import com.leorizick.recipeapp.entities.recipe.Ingredient;
import com.leorizick.recipeapp.entities.recipe.Recipe;
import com.leorizick.recipeapp.entities.recipe.RecipeStep;
import com.leorizick.recipeapp.services.domain.service.recipe.IngredientCrud;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeCrud;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeStepCrud;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientApiService {

    private final IngredientCrud ingredientCrud;
    private final RecipeCrud recipeCrud;
    private final ModelMapper modelMapper;

    public IngredientCrudResponse create(Long id, IngredientCreationRequest ingredientCreationRequest){
        Recipe recipe = recipeCrud.findById(id);
        Ingredient ingredient = modelMapper.map(ingredientCreationRequest, Ingredient.class);
        ingredient.setRecipe(recipe);
        ingredientCrud.save(ingredient);
        return modelMapper.map(ingredient, IngredientCrudResponse.class);
    }

    public IngredientCrudResponse update(Long recipeId, IngredientCreationRequest ingredientCreationRequest, Long ingredientId){
        Recipe recipe = recipeCrud.findById(recipeId);
        Ingredient original = ingredientCrud.findById(ingredientId);
        modelMapper.map(ingredientCreationRequest, original);
        original.setRecipe(recipe);
        var updated = ingredientCrud.save(original);
        return modelMapper.map(updated, IngredientCrudResponse.class);
    }

    public void delete(Long id){
        ingredientCrud.delete(id);
    }
}
