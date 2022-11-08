package com.leorizick.recipeapp.web.recipe;


import com.leorizick.recipeapp.dto.recipe.RecipeStepCreationRequest;
import com.leorizick.recipeapp.dto.recipe.RecipeStepCrudResponse;
import com.leorizick.recipeapp.services.api.service.recipe.RecipeStepApiService;
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
public class RecipeStepController {

    private final RecipeStepApiService recipeStepApiService;

    @PreAuthorize("hasAuthority('CREATE_RECIPE_STEP')")
    @PostMapping(value = "api/recipe/{id}/step")
    public ResponseEntity<RecipeStepCrudResponse> create(@PathVariable Long id, @RequestBody RecipeStepCreationRequest recipeStepCreationRequest) {
        RecipeStepCrudResponse recipeStepCrudResponse = recipeStepApiService.create(id, recipeStepCreationRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(recipeStepCrudResponse);
    }

    @PreAuthorize("hasAuthority('UPDATE_RECIPE_STEP')")
    @PutMapping(value = "api/recipe/{recipeId}/step/{stepId}")
    public ResponseEntity<RecipeStepCrudResponse> update(@PathVariable Long recipeId, @RequestBody RecipeStepCreationRequest recipeStepCreationRequest, @PathVariable Long stepId) {
        RecipeStepCrudResponse recipeStepCrudResponse = recipeStepApiService.update(recipeId, recipeStepCreationRequest, stepId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PreAuthorize("hasAuthority('DELETE_RECIPE_STEP')")
    @DeleteMapping(value = "api/recipe/step/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeStepApiService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
