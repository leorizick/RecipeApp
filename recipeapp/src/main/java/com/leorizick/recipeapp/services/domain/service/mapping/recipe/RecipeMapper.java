package com.leorizick.recipeapp.services.domain.service.mapping.recipe;

import com.leorizick.recipeapp.dto.recipe.RecipeCreationRequest;
import com.leorizick.recipeapp.dto.recipe.RecipeUpdateRequest;
import com.leorizick.recipeapp.entities.recipe.Recipe;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeCategoryCrud;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Configuration
@RequiredArgsConstructor
public class RecipeMapper {
    private final ModelMapper modelMapper;
    private final RecipeCategoryCrud recipeCategoryCrud;
    private final AuthenticationContext authenticationContext;

    @PostConstruct
    public void configure() {
        createFromRecipeCreationRequest();
    }

    private void createFromRecipeCreationRequest() {
        modelMapper.createTypeMap(RecipeCreationRequest.class, Recipe.class).setConverter(context -> {
            var src = context.getSource();
            return createFromCommonRecipeCreationRequestDTO(src);
        });
    }

    private void updateRecipeFromRecipeCreationRequest() {
        modelMapper.createTypeMap(RecipeUpdateRequest.class, Recipe.class).setConverter(context -> {
            var src = context.getSource();
            return createFromCommonRecipeCreationRequestDTO(src);
        });
    }

    private Recipe createFromCommonRecipeCreationRequestDTO(RecipeCreationRequest recipe) {

        return Recipe.builder()
                .name(recipe.getName())
                .category(recipeCategoryCrud.findById(recipe.getCategoryId()))
                .description(recipe.getDescription())
                .ingredients(recipe.getIngredients())
                .step(new ArrayList<>())
                .enabled(true)
                .author(authenticationContext.getAccount())
                .build();
    }
}
