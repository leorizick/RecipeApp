package com.leorizick.recipeapp.services.domain.service.mapping.recipe;

import com.leorizick.recipeapp.dto.rating.RatingDTO;
import com.leorizick.recipeapp.entities.rating.Rating;
import com.leorizick.recipeapp.entities.recipe.Ingredient;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeCategoryCrud;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class RatingMapper {
    private final ModelMapper modelMapper;
    private final AuthenticationContext authenticationContext;

    @PostConstruct
    public void configure() {
        createRatingFromRatinDTO();
    }

    private void createRatingFromRatinDTO() {
        modelMapper.createTypeMap(RatingDTO.class, Rating.class).setConverter(context -> {
            var src = context.getSource();

            return Rating.builder()
                    .account(authenticationContext.getAccount())
                    .rating(src.getRating())
                    .build();


        });
    }
}
