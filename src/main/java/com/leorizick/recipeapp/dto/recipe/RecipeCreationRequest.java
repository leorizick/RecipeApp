package com.leorizick.recipeapp.dto.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RecipeCreationRequest {
    private String name;
    private String description;
    private String ingredients;
    private Long categoryId;
    private List<RecipeStepCreationRequest> steps;
}
