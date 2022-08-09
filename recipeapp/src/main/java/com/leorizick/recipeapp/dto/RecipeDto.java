package com.leorizick.recipeapp.dto;

import com.leorizick.recipeapp.entities.RecipeCategory;
import com.leorizick.recipeapp.entities.RecipeSteps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDto {

    private Long id;
    private String name;
    private String description;
//    private String ingredients;
//    private List<String> steps;
    private Boolean isActive;
    private RecipeCategory category;


    private String ingredients;
    private List<RecipeSteps> steps = new ArrayList<>();
}
