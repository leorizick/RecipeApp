package com.leorizick.recipeapp.services.domain.service.mapping.recipe;

import com.leorizick.recipeapp.dto.account.AccountSummaryResponse;
import com.leorizick.recipeapp.dto.recipe.RecipeCrudResponse;
import com.leorizick.recipeapp.dto.recipe.RecipeStepCrudResponse;
import com.leorizick.recipeapp.entities.recipe.Recipe;
import com.leorizick.recipeapp.entities.recipe.RecipeStep;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class RecipeStepResponseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    public void configure() {
        createFromRecipeStep();
    }

    private void createFromRecipeStep() {
        modelMapper.createTypeMap(RecipeStep.class, RecipeStepCrudResponse.class)
                .setConverter(mappingContext -> {
                    var src = mappingContext.getSource();

                    return RecipeStepCrudResponse.builder()
                            .id(src.getId())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .step(src.getStep())
                            .recipe(src.getRecipe().getId())
                            .build();
                });
    }
}
