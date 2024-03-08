package com.leorizick.recipeapp.services.domain.service.mapping.recipe;

import com.leorizick.recipeapp.dto.recipe.RecipeCreationRequest;
import com.leorizick.recipeapp.entities.recipe.Ingredient;
import com.leorizick.recipeapp.entities.recipe.Recipe;
import com.leorizick.recipeapp.entities.recipe.RecipeStep;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeCategoryCrud;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.stream.Collectors;

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
            var recipe = context.getDestination();
            if (recipe == null) {
                recipe = new Recipe();
                recipe.setAuthor(authenticationContext.getAccount());

            }

            var ingredients = src.getIngredients()
                    .stream().map(ingredient -> modelMapper.map(ingredient, Ingredient.class)).collect(Collectors.toList());


            var steps = src.getSteps()
                    .stream().map(step -> modelMapper.map(step, RecipeStep.class)).collect(Collectors.toList());

            recipe.setName(src.getName());
            recipe.setCategory(recipeCategoryCrud.findById(src.getCategoryId()));
            recipe.setDescription(src.getDescription());
            recipe.setIngredients(ingredients);
            recipe.setStep(steps);

            recipe.setComment(new ArrayList<>());
            recipe.setEnabled(true);

            return recipe;
        });
    }
}
