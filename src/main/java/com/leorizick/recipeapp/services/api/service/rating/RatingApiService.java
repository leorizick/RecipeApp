package com.leorizick.recipeapp.services.api.service.rating;

import com.leorizick.recipeapp.dto.rating.RatingDTO;
import com.leorizick.recipeapp.dto.recipe.IngredientCreationRequest;
import com.leorizick.recipeapp.dto.recipe.IngredientCrudResponse;
import com.leorizick.recipeapp.entities.rating.Rating;
import com.leorizick.recipeapp.entities.recipe.Ingredient;
import com.leorizick.recipeapp.entities.recipe.Recipe;
import com.leorizick.recipeapp.services.domain.service.rating.RatingManagement;
import com.leorizick.recipeapp.services.domain.service.recipe.IngredientCrud;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeCrud;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingApiService {

    private final RatingManagement ratingManagement;
    private final RecipeCrud recipeCrud;
    private final ModelMapper modelMapper;

    public void create(Long id, RatingDTO ratingDTO) {
        Recipe recipe = recipeCrud.findById(id);
        Rating rating = modelMapper.map(ratingDTO, Rating.class);
        rating.setRecipe(recipe);
        ratingManagement.DeleteByRecipeId(id, rating.getAccount().getId());
        ratingManagement.save(rating);
    }

}
