package com.leorizick.recipeapp.services.domain.service.mapping.recipe;

import com.leorizick.recipeapp.dto.account.AccountSummaryResponse;
import com.leorizick.recipeapp.dto.recipe.RecipeSummaryResponse;
import com.leorizick.recipeapp.entities.recipe.Recipe;
import com.leorizick.recipeapp.repositories.like.RecipeLikeRepository;
import com.leorizick.recipeapp.services.api.service.file.FileApiService;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import com.leorizick.recipeapp.services.domain.service.rating.RatingManagement;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class RecipeSummaryResponseMapper {
    private final ModelMapper modelMapper;
    private final RecipeLikeRepository recipeLikeRepository;
    private final AuthenticationContext authenticationContext;
    private final RatingManagement ratingManagement;

    private final FileApiService fileApiService;

    @PostConstruct
    public void configure() {
        createFromRecipeSummary();
    }

    private void createFromRecipeSummary() {
        modelMapper.createTypeMap(Recipe.class, RecipeSummaryResponse.class)
                .setConverter(mappingContext -> {
                    var src = mappingContext.getSource();
                    var author = modelMapper.map(src.getAuthor(), AccountSummaryResponse.class);

                    var liker = authenticationContext.getAccountId();

                    var ratesCount = ratingManagement.getRatingCount(src.getId());

                    var rating = ratingManagement.getRatingTotal(src.getId());

                    var coverName = fileApiService.searchRecipeCoverImgByRecipeId(src.getId());

                    return RecipeSummaryResponse.builder()
                            .id(src.getId())
                            .author(author)
                            .name(src.getName())
                            .enabled(src.isEnabled())
                            .category(src.getCategory().getName())
                            .isLiked(recipeLikeRepository.isLiked(src.getId(), liker))
                            .likesCount(recipeLikeRepository.likeCount(src.getId()))
                            .ratesCount(ratesCount)
                            .rating(rating)
                            .coverName(coverName)
                            .build();

                });
    }
}
