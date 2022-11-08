package com.leorizick.recipeapp.services.domain.service.like;

import com.leorizick.recipeapp.entities.account.Account;
import com.leorizick.recipeapp.entities.like.RecipeLike;
import com.leorizick.recipeapp.entities.recipe.Recipe;
import com.leorizick.recipeapp.repositories.RecipeLikeRepository;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import com.leorizick.recipeapp.services.domain.service.recipe.RecipeCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LikeManagement {

    private final RecipeLikeRepository recipeLikeRepository;
    private final AuthenticationContext authenticationContext;
    private final RecipeCrud recipeCrud;

    @Transactional
    public void likeRecipe(Long id) {
        if (recipeLikeRepository.isLiked(id, authenticationContext.getAccountId())){
            return;
        }
        Recipe recipe = recipeCrud.findById(id);
        Account liker = authenticationContext.getAccount();
        RecipeLike recipeLike = new RecipeLike(recipe, liker);
        recipeLikeRepository.save(recipeLike);
    }

    @Transactional
    public void dislikeRecipe(Long recipeId) {
        Long liker = authenticationContext.getAccountId();
        recipeLikeRepository.deleteByRecipeAndAccountId(recipeId, liker);
    }
}
