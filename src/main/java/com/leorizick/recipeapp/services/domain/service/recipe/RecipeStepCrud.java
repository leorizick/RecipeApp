package com.leorizick.recipeapp.services.domain.service.recipe;

import com.leorizick.recipeapp.entities.recipe.RecipeStep;
import com.leorizick.recipeapp.repositories.RecipeStepsRepository;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import com.leorizick.recipeapp.services.exceptions.AccountTypeNotAllowed;
import com.leorizick.recipeapp.services.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RecipeStepCrud {

    private final RecipeStepsRepository recipeStepsRepository;
    private final AuthenticationContext authenticationContext;

    public RecipeStep findById(Long id) {
        return recipeStepsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Step not found! Id: " + id));
    }
    @Transactional
    public RecipeStep save(RecipeStep recipeStep){
        verifyAuthorIdAndAuthAccountId(recipeStep);
        return recipeStepsRepository.save(recipeStep);
    }

    public void delete(Long id){
        RecipeStep recipeStep = findById(id);
        verifyAuthorIdAndAuthAccountId(recipeStep);
        recipeStepsRepository.deleteById(id);
    }

    public void DeleteByRecipeId(Long recipeId){
        recipeStepsRepository.deleteByRecipeId(recipeId);
    }

    private void verifyAuthorIdAndAuthAccountId(RecipeStep recipeStep){
        if(!authenticationContext.getAccountId().equals(recipeStep.getRecipe().getAuthor().getId())){
            throw new AccountTypeNotAllowed("author");
        }
    }
}
