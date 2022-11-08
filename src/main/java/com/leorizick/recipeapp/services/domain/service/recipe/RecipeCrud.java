package com.leorizick.recipeapp.services.domain.service.recipe;

import com.leorizick.recipeapp.entities.recipe.Ingredient;
import com.leorizick.recipeapp.entities.recipe.Recipe;
import com.leorizick.recipeapp.entities.recipe.RecipeStep;
import com.leorizick.recipeapp.repositories.recipe.RecipeRepository;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import com.leorizick.recipeapp.services.exceptions.AccountTypeNotAllowed;
import com.leorizick.recipeapp.services.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RecipeCrud {

    private final RecipeRepository recipeRepository;
    private final AuthenticationContext authenticationContext;
    private final RecipeStepCrud recipeStepCrud;
    private final IngredientCrud ingredientCrud;

    public Recipe findById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Receita não encontrada! Id: " + id));
    }

    public Page<Recipe> findAll(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    @Transactional
    public Recipe save(Recipe recipe) {
       verifyAuthorIdAndAuthAccountId(recipe);
        return recipeRepository.save(recipe);
    }

    @Transactional
    public void deleteById(Long id) {
        Recipe recipe = findById(id);
        verifyAuthorIdAndAuthAccountId(recipe);
        recipe.setEnabled(false);
        recipeRepository.save(recipe);

    }

    private void verifyAuthorIdAndAuthAccountId(Recipe recipe) {
        var authAccountId = authenticationContext.getAccountId();
        if (recipe.getId() != null && !authAccountId.equals(recipe.getAuthor().getId())) {
            throw new AccountTypeNotAllowed("author");
        }
    }

    public void saveStepsAndIngredients(Recipe recipe){
        recipeStepCrud.DeleteByRecipeId(recipe.getId());
        for (RecipeStep step: recipe.getStep()) {
            step.setRecipe(recipe);
            recipeStepCrud.save(step);
        }
        ingredientCrud.DeleteByRecipeId(recipe.getId());
        for (Ingredient ing: recipe.getIngredients()) {
            ing.setRecipe(recipe);
            ingredientCrud.save(ing);
        }
    }

}
