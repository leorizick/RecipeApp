package com.leorizick.recipeapp.dto.recipe;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientSummaryResponse {

    private Long id;
    private String ingredient;

}
