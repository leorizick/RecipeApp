package com.leorizick.recipeapp.services.domain.service.recipe;

import com.leorizick.recipeapp.entities.recipe.RecipeStep;
import com.leorizick.recipeapp.repositories.RecipeStepsRepository;
import com.leorizick.recipeapp.services.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RecipeStepCrud {

    private final RecipeStepsRepository recipeStepsRepository;

    public RecipeStep findById(Long id) {
        return recipeStepsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Step not found! Id: " + id));
    }
    @Transactional
    public RecipeStep save(RecipeStep recipeStep){
        return recipeStepsRepository.save(recipeStep);
    }

    public void delete(Long id){
        RecipeStep recipeStep = findById(id);
        recipeStepsRepository.deleteById(id);
    }

    public void DeleteByRecipeId(Long recipeId){
        recipeStepsRepository.deleteByRecipeId(recipeId);
    }
}
