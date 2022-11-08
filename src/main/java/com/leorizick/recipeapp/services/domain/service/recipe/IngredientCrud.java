package com.leorizick.recipeapp.services.domain.service.recipe;

import com.leorizick.recipeapp.entities.recipe.Ingredient;
import com.leorizick.recipeapp.repositories.recipe.IngredientRepository;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import com.leorizick.recipeapp.services.exceptions.AccountTypeNotAllowed;
import com.leorizick.recipeapp.services.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class IngredientCrud {

    private final IngredientRepository ingredientRepository;
    private final AuthenticationContext authenticationContext;

    public Ingredient findById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Step not found! Id: " + id));
    }
    @Transactional
    public Ingredient save(Ingredient ingredient){
        verifyAuthorIdAndAuthAccountId(ingredient);
        return ingredientRepository.save(ingredient);
    }

    @Transactional
    public void delete(Long id){
        Ingredient ingredient = findById(id);
        verifyAuthorIdAndAuthAccountId(ingredient);
        ingredientRepository.deleteById(id);
    }

    @Transactional
    public void DeleteByRecipeId(Long recipeId){
        
        ingredientRepository.deleteByRecipeId(recipeId);
    }

    private void verifyAuthorIdAndAuthAccountId(Ingredient ingredient){
        if(!authenticationContext.getAccountId().equals(ingredient.getRecipe().getAuthor().getId())){
            throw new AccountTypeNotAllowed("author");
        }
    }
    
}
