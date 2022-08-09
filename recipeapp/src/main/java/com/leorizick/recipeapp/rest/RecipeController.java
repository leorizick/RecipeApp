package com.leorizick.recipeapp.rest;

import com.leorizick.recipeapp.dto.RecipeDto;
import com.leorizick.recipeapp.entities.Recipe;
import com.leorizick.recipeapp.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Recipe> find(@PathVariable Long id) {
        Recipe recipe = service.find(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recipe);
    }

    @GetMapping
    public ResponseEntity<Page<Recipe>> findAll(Pageable pageable) {
        Page<Recipe> recipePage = service.findAll(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recipePage);
    }

    @PostMapping
    public ResponseEntity<Recipe> save(@RequestBody RecipeDto recipeDto) {
        Recipe response = service.save(recipeDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Recipe> update(@PathVariable Integer id, @RequestBody RecipeDto recipeDto) {
        Recipe updatedRecipe = service.update(recipeDto);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }


}
