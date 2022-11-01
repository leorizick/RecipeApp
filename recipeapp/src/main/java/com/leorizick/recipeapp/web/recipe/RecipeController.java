package com.leorizick.recipeapp.web.recipe;

import com.leorizick.recipeapp.dto.recipe.RecipeCreationRequest;
import com.leorizick.recipeapp.dto.recipe.RecipeCreationResponse;
import com.leorizick.recipeapp.entities.recipe.Recipe;
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
    public ResponseEntity<Recipe> findRecipeById(@PathVariable Long id) {
        Recipe recipe = recipeApiService.find(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recipe);
    }

    @PreAuthorize("hasAuthority('GET_RECIPE')")
    @GetMapping(value = "/api/recipe")
    public ResponseEntity<Page<Recipe>> findAll(Pageable pageable) {
        Page<Recipe> recipePage = recipeApiService.findAll(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recipePage);
    }

    @PreAuthorize("hasAuthority('CREATE_RECIPE')")
    @PostMapping(value = "/api/recipe/create")
    public ResponseEntity<RecipeCreationResponse> createRecipe(@RequestBody RecipeCreationRequest recipeCreationRequest) {
        RecipeCreationResponse recipeCreationResponse = recipeApiService.save(recipeCreationRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(recipeCreationResponse);
    }

    @PreAuthorize("hasAuthority('UPDATE_RECIPE')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Integer id, @RequestBody RecipeCreationRequest recipeCreationRequest) {
        Recipe updatedRecipe = recipeApiService.updateRecipe(recipeCreationRequest);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PreAuthorize("hasAuthority('DELETE_RECIPE')")
    @DeleteMapping(value = "/api/recipe/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeApiService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }


}
