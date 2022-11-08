package com.leorizick.recipeapp.services.domain.service.mapping.recipe;

import com.leorizick.recipeapp.dto.account.AccountSummaryResponse;
import com.leorizick.recipeapp.dto.recipe.CommentSummaryResponse;
import com.leorizick.recipeapp.dto.recipe.RecipeCrudResponse;
import com.leorizick.recipeapp.dto.recipe.RecipeStepSummaryResponse;
import com.leorizick.recipeapp.entities.recipe.Recipe;
import com.leorizick.recipeapp.repositories.RecipeLikeRepository;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
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
    private final RecipeLikeRepository recipeLikeRepository;
    private final AuthenticationContext authenticationContext;

    @PostConstruct
    public void configure() {
        createFromRecipe();
    }

    private void createFromRecipe() {
        modelMapper.createTypeMap(Recipe.class, RecipeCrudResponse.class)
                .setConverter(mappingContext -> {
                    var src = mappingContext.getSource();
                    var author = modelMapper.map(src.getAuthor(), AccountSummaryResponse.class);

                    var comments = src.getComment()
                            .stream().map(comment -> modelMapper.map(comment, CommentSummaryResponse.class)).toList();

                    var steps = src.getStep()
                            .stream().map(step -> new RecipeStepSummaryResponse(step.getId(), step.getStep())).toList();

                    var liker = authenticationContext.getAccountId();

                    return RecipeCrudResponse.builder()
                            .id(src.getId())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .author(author)
                            .name(src.getName())
                            .description(src.getDescription())
                            .enabled(src.isEnabled())
                            .step(steps)
                            .ingredients(src.getIngredients())
                            .comment(comments)
                            .category(src.getCategory().getName())
                            .isLiked(recipeLikeRepository.isLiked(src.getId(), liker))
                            .likesCount(recipeLikeRepository.likeCount(src.getId()))
                            .build();
                });
    }
}
