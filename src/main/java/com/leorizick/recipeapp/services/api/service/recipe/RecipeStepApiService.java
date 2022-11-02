package com.leorizick.recipeapp.services.api.service.recipe;

import com.leorizick.recipeapp.dto.recipe.RecipeStepCreationRequest;
import com.leorizick.recipeapp.dto.recipe.RecipeStepCrudResponse;
import com.leorizick.recipeapp.entities.recipe.Recipe;
import com.leorizick.recipeapp.entities.recipe.RecipeStep;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeCrud;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeStepCrud;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeStepApiService {

    private final RecipeStepCrud recipeStepCrud;
    private final RecipeCrud recipeCrud;
    private final ModelMapper modelMapper;

    public RecipeStepCrudResponse create(Long id, RecipeStepCreationRequest recipeStepCreationRequest){
        Recipe recipe = recipeCrud.findById(id);
        RecipeStep recipeStep = modelMapper.map(recipeStepCreationRequest, RecipeStep.class);
        recipeStep.setRecipe(recipe);
        recipeStepCrud.save(recipeStep);
        return modelMapper.map(recipeStep, RecipeStepCrudResponse.class);
    }

    public RecipeStepCrudResponse update(Long recipeId, RecipeStepCreationRequest recipeStepCreationRequest, Long stepId){
        Recipe recipe = recipeCrud.findById(recipeId);
        RecipeStep original = recipeStepCrud.findById(stepId);
        modelMapper.map(recipeStepCreationRequest, original);
        original.setRecipe(recipe);
        var updated = recipeStepCrud.save(original);
        return modelMapper.map(updated, RecipeStepCrudResponse.class);
    }

    public void delete(Long id){
        recipeStepCrud.delete(id);
    }
}
