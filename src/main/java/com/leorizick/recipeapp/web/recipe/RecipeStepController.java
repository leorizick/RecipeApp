package com.leorizick.recipeapp.web.recipe;


import com.leorizick.recipeapp.dto.recipe.RecipeStepCreationRequest;
import com.leorizick.recipeapp.dto.recipe.RecipeStepCrudResponse;
import com.leorizick.recipeapp.entities.recipe.RecipeStep;
import com.leorizick.recipeapp.services.api.service.recipe.RecipeStepApiService;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeStepCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value = "api/recipe/{id}/step")
    public ResponseEntity<RecipeStepCrudResponse> create(@PathVariable Long id, @RequestBody RecipeStepCreationRequest recipeStepCreationRequest) {
        RecipeStepCrudResponse recipeStepCrudResponse = recipeStepApiService.create(id, recipeStepCreationRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(recipeStepCrudResponse);
    }

    @PutMapping(value = "api/recipe/{recipeId}/step/{stepId}")
    public ResponseEntity<RecipeStepCrudResponse> update(@PathVariable Long recipeId, @RequestBody RecipeStepCreationRequest recipeStepCreationRequest, @PathVariable Long stepId) {
        RecipeStepCrudResponse recipeStepCrudResponse = recipeStepApiService.update(recipeId, recipeStepCreationRequest, stepId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping(value = "api/recipe/step/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeStepApiService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
