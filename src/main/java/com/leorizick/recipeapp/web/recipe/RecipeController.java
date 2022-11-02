package com.leorizick.recipeapp.web.recipe;

import com.leorizick.recipeapp.dto.recipe.RecipeCreationRequest;
import com.leorizick.recipeapp.dto.recipe.RecipeCrudResponse;
import com.leorizick.recipeapp.services.api.service.recipe.RecipeApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController

public class RecipeController {

    @Autowired
    private RecipeApiService recipeApiService;

    @PreAuthorize("hasAuthority('GET_RECIPE')")
    @GetMapping(value = "/api/recipe/{id}")
    public ResponseEntity<RecipeCrudResponse> findRecipeById(@PathVariable Long id) {
        RecipeCrudResponse recipe = recipeApiService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recipe);
    }

    @PreAuthorize("hasAuthority('GET_RECIPE')")
    @GetMapping(value = "/api/recipe")
    public ResponseEntity<Page<RecipeCrudResponse>> findAll(Pageable pageable) {
        Page<RecipeCrudResponse> recipePage = recipeApiService.findAll(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recipePage);
    }

    @PreAuthorize("hasAuthority('CREATE_RECIPE')")
    @PostMapping(value = "/api/recipe/create")
    public ResponseEntity<RecipeCrudResponse> create(@RequestBody RecipeCreationRequest recipeCreationRequest) {
        RecipeCrudResponse recipeCrudResponse = recipeApiService.create(recipeCreationRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(recipeCrudResponse);
    }

    @PreAuthorize("hasAuthority('UPDATE_RECIPE')")
    @PutMapping(value = "/api/recipe/{id}")
    public ResponseEntity<RecipeCrudResponse> update(@PathVariable Long id, @RequestBody RecipeCreationRequest recipeCreationRequest) {
        RecipeCrudResponse updatedRecipe = recipeApiService.update(id, recipeCreationRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PreAuthorize("hasAuthority('DELETE_RECIPE')")
    @DeleteMapping(value = "/api/recipe/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeApiService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }

}
