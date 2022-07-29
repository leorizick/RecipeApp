package com.leorizick.recipeapp.dto;

import com.leorizick.recipeapp.entities.RecipeCategory;
import com.leorizick.recipeapp.entities.StepsAndIngredients;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
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


    private List<StepsAndIngredients> ingredients;


    private List<StepsAndIngredients> steps;
}
