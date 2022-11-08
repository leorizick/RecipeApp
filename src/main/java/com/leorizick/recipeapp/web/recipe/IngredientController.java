package com.leorizick.recipeapp.web.recipe;


import com.leorizick.recipeapp.dto.recipe.IngredientCreationRequest;
import com.leorizick.recipeapp.dto.recipe.IngredientCrudResponse;
import com.leorizick.recipeapp.dto.recipe.RecipeStepCreationRequest;
import com.leorizick.recipeapp.dto.recipe.RecipeStepCrudResponse;
import com.leorizick.recipeapp.services.api.service.recipe.IngredientApiService;
import com.leorizick.recipeapp.services.api.service.recipe.RecipeStepApiService;
import com.leorizick.recipeapp.services.domain.service.recipe.IngredientCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientApiService ingredientApiService;

    @PreAuthorize("hasAuthority('CREATE_RECIPE_INGREDIENT')")
    @PostMapping(value = "api/recipe/{id}/ingredient")
    public ResponseEntity<IngredientCrudResponse> create(@PathVariable Long id, @RequestBody IngredientCreationRequest ingredientCreationRequest) {
        IngredientCrudResponse ingredientCrudResponse = ingredientApiService.create(id, ingredientCreationRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ingredientCrudResponse);
    }

    @PreAuthorize("hasAuthority('UPDATE_RECIPE_INGREDIENT')")
    @PutMapping(value = "api/recipe/{recipeId}/ingredient/{ingredientId}")
    public ResponseEntity<IngredientCrudResponse> update(@PathVariable Long recipeId, @RequestBody IngredientCreationRequest ingredientCreationRequest, @PathVariable Long ingredientId) {
        IngredientCrudResponse ingredientCrudResponse = ingredientApiService.update(recipeId, ingredientCreationRequest, ingredientId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PreAuthorize("hasAuthority('DELETE_RECIPE_INGREDIENT')")
    @DeleteMapping(value = "api/recipe/ingredient/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ingredientApiService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
