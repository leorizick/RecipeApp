package com.leorizick.recipeapp.services.domain.service.recipe;

import com.leorizick.recipeapp.entities.recipe.Recipe;
import com.leorizick.recipeapp.repositories.RecipeRepository;
import com.leorizick.recipeapp.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class RecipeCrud {

    @Autowired
    private RecipeRepository recipeRepository;

    public Recipe find(Long id) {
        Optional<Recipe> obj = recipeRepository.findById(id);
        return obj.orElseThrow(() -> new NotFoundException("Receita não encontrada! Id: " + id));
    }


    public Page<Recipe> findAll(Pageable pageable) {
        Page<Recipe> recipePages = recipeRepository.findAll(pageable);
        return recipePages;
    }

    @Transactional
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

//    @Transactional
//    public Recipe update(RecipeDto recipeDto) {
//        Recipe updatedRecipe = findById(recipeDto.getId());
//        return recipeRepository.save(updatedRecipe);
//    }

    @Transactional
    public void delete(Long id) {
        Recipe recipe = find(id);
        recipe.setEnabled(false);
        recipeRepository.save(recipe);

    }

}
