package com.leorizick.recipeapp.services.api.service.like;

import com.leorizick.recipeapp.services.domain.service.like.LikeManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeApiService {

    private final LikeManagement likeManagement;

    public void likeRecipe(Long id) {
        likeManagement.likeRecipe(id);
    }

    public void dislikeRecipe(Long id) {
        likeManagement.dislikeRecipe(id);

    }
}
