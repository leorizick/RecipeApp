package com.leorizick.recipeapp.services;

import com.leorizick.recipeapp.entities.RecipeSteps;
import com.leorizick.recipeapp.repositories.RecipeStepsRepository;
import com.leorizick.recipeapp.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipeStepService {

    @Autowired
    private RecipeStepsRepository repository;

    public RecipeSteps find(Long id){
        Optional<RecipeSteps> steps = repository.findById(id);
        return steps.orElseThrow(() -> new ObjectNotFoundException("Não foram encontrados passos para essa receita! Id:" + id));
    }

    public Page<RecipeSteps> findAll(Pageable pageable) {
        Page<RecipeSteps> stepsPages = repository.findAll(pageable);
        return stepsPages;
    }

    public RecipeSteps save(RecipeSteps recipeSteps){
        return repository.save(recipeSteps);
    }

    public RecipeSteps update(RecipeSteps recipeSteps){
        return repository.save(recipeSteps);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}
