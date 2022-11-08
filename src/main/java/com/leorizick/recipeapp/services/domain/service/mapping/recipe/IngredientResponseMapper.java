package com.leorizick.recipeapp.services.domain.service.mapping.recipe;

import com.leorizick.recipeapp.dto.recipe.IngredientCrudResponse;
import com.leorizick.recipeapp.dto.recipe.RecipeStepCrudResponse;
import com.leorizick.recipeapp.entities.recipe.Ingredient;
import com.leorizick.recipeapp.entities.recipe.RecipeStep;
import com.leorizick.recipeapp.services.domain.service.recipe.IngredientCrud;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class IngredientResponseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    public void configure() {
        createFromRecipeStep();
    }

    private void createFromRecipeStep() {
        modelMapper.createTypeMap(Ingredient.class, IngredientCrudResponse.class)
                .setConverter(mappingContext -> {
                    var src = mappingContext.getSource();

                    return IngredientCrudResponse.builder()
                            .id(src.getId())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .ingredient(src.getIngredient())
                            .recipe(src.getRecipe().getId())
                            .build();
                });
    }
}
