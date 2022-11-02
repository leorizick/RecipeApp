package com.leorizick.recipeapp.dto.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeStepCreationRequest {
    private Long id;
    private String step;
}
