package com.leorizick.recipeapp.dto.recipe;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeStepSummaryResponse {

    private Long id;
    private String step;

}
