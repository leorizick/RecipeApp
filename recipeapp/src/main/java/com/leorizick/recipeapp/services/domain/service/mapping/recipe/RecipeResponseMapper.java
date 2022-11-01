package com.leorizick.recipeapp.services.domain.service.mapping.recipe;

import com.leorizick.recipeapp.dto.account.AccountSummaryResponse;
import com.leorizick.recipeapp.dto.recipe.RecipeCreationResponse;
import com.leorizick.recipeapp.entities.recipe.Recipe;
import com.leorizick.recipeapp.entities.recipe.RecipeStep;
import com.leorizick.recipeapp.services.domain.service.mapping.account.AccountSummaryResponseMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class RecipeResponseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    public void configure() {
        createFromRecipe();
    }

    private void createFromRecipe() {
        modelMapper.createTypeMap(Recipe.class, RecipeCreationResponse.class)
                .setConverter(mappingContext -> {
                    var src = mappingContext.getSource();
                    var author = modelMapper.map(src.getAuthor(), AccountSummaryResponse.class);

                    var steps = src.getStep().stream().map(RecipeStep::getStep).collect(Collectors.toList());

                    return RecipeCreationResponse.builder()
                            .id(src.getId())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .author(author)
                            .name(src.getName())
                            .description(src.getDescription())
                            .enabled(src.isEnabled())
                            .step(steps)
                            .ingredients(src.getIngredients())
                            .category(src.getCategory().getName())
                            .build();
                });
    }
}
