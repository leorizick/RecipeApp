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

    public Recipe findById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Receita não encontrada! Id: " + id));
    }


    public Page<Recipe> findAll(Pageable pageable) {
        return recipeRepository.findAll(pageable);
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
    public void deleteById(Long id) {
        Recipe recipe = findById(id);
        recipe.setEnabled(false);
        recipeRepository.save(recipe);

    }

}
