package com.leorizick.recipeapp.services.domain.service.recipe;

import com.leorizick.recipeapp.entities.recipe.RecipeStep;
import com.leorizick.recipeapp.repositories.RecipeStepsRepository;
import com.leorizick.recipeapp.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipeStepCrud {

    @Autowired
    private RecipeStepsRepository repository;

    public RecipeStep find(Long id){
        Optional<RecipeStep> steps = repository.findById(id);
        return steps.orElseThrow(() -> new NotFoundException("Não foram encontrados passos para essa receita! Id:" + id));
    }

    public Page<RecipeStep> findAll(Pageable pageable) {
        Page<RecipeStep> stepsPages = repository.findAll(pageable);
        return stepsPages;
    }

    public RecipeStep save(RecipeStep recipeSteps){
        return repository.save(recipeSteps);
    }

    public RecipeStep update(RecipeStep recipeSteps){
        return repository.save(recipeSteps);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}
