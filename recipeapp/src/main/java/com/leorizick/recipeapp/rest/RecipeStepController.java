package com.leorizick.recipeapp.rest;


import com.leorizick.recipeapp.entities.RecipeSteps;
import com.leorizick.recipeapp.services.RecipeStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public class RecipeStepController {

    @Autowired
    private RecipeStepService service;

    @GetMapping
    public ResponseEntity<RecipeSteps> find(@PathVariable Long id) {
        RecipeSteps step = service.find(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(step);
    }

    @GetMapping
    public ResponseEntity<Page<RecipeSteps>> findAll(Pageable pageable) {
        Page<RecipeSteps> stepPages = service.findAll(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stepPages);

    }

    @PostMapping
    public ResponseEntity<RecipeSteps> save(@RequestBody RecipeSteps recipeSteps) {
        RecipeSteps steps = service.save(recipeSteps);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(steps);
    }

    @PutMapping
    public ResponseEntity<RecipeSteps> update(@RequestBody RecipeSteps recipeSteps) {
        RecipeSteps steps = service.save(recipeSteps);
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
