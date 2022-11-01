package com.leorizick.recipeapp.web.recipe;


import com.leorizick.recipeapp.entities.recipe.RecipeStep;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeStepCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public class RecipeStepController {

    @Autowired
    private RecipeStepCrud service;

    @GetMapping
    public ResponseEntity<RecipeStep> find(@PathVariable Long id) {
        RecipeStep step = service.find(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(step);
    }

    @GetMapping
    public ResponseEntity<Page<RecipeStep>> findAll(Pageable pageable) {
        Page<RecipeStep> stepPages = service.findAll(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stepPages);

    }

    @PostMapping
    public ResponseEntity<RecipeStep> save(@RequestBody RecipeStep recipeSteps) {
        RecipeStep steps = service.save(recipeSteps);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(steps);
    }

    @PutMapping
    public ResponseEntity<RecipeStep> update(@RequestBody RecipeStep recipeSteps) {
        RecipeStep steps = service.save(recipeSteps);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(Long id) {
        service.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
