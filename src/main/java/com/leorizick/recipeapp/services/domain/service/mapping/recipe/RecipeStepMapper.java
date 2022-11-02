package com.leorizick.recipeapp.services.domain.service.mapping.recipe;

import com.leorizick.recipeapp.dto.recipe.RecipeStepCreationRequest;
import com.leorizick.recipeapp.entities.recipe.RecipeStep;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeCategoryCrud;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class RecipeStepMapper {
    private final ModelMapper modelMapper;
    private final RecipeCategoryCrud recipeCategoryCrud;
    private final AuthenticationContext authenticationContext;

    @PostConstruct
    public void configure() {
        createFromRecipeStepCreationRequest();
    }

    private void createFromRecipeStepCreationRequest() {
        modelMapper.createTypeMap(RecipeStepCreationRequest.class, RecipeStep.class).setConverter(context -> {
            var src = context.getSource();
            var recipeStep = context.getDestination();
            if(recipeStep == null){
                recipeStep = new RecipeStep();
            }
            recipeStep.setStep(src.getStep());
            return recipeStep;
        });
    }
}
