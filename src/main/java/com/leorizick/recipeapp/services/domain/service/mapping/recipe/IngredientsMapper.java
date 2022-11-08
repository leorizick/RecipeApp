package com.leorizick.recipeapp.services.domain.service.mapping.recipe;

import com.leorizick.recipeapp.entities.recipe.Ingredient;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeCategoryCrud;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class IngredientsMapper {
    private final ModelMapper modelMapper;
    private final RecipeCategoryCrud recipeCategoryCrud;
    private final AuthenticationContext authenticationContext;

    @PostConstruct
    public void configure() {
        createIngredientsFromRecipeCreationRequest();
    }

    private void createIngredientsFromRecipeCreationRequest() {
        modelMapper.createTypeMap(String.class, Ingredient.class).setConverter(context -> {
            var src = context.getSource();
            var ingredients = context.getDestination();
            if(ingredients == null){
                ingredients = new Ingredient();
            }
            ingredients.setIngredient(src);
            return ingredients;
        });
    }
}
