package com.leorizick.recipeapp.web.like;

import com.leorizick.recipeapp.services.api.service.like.LikeApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeApiService likeApiService;

    @PreAuthorize("hasAuthority('LIKE_RECIPE')")
    @PostMapping(value = "/api/recipe/{id}/like")
    public ResponseEntity<Void> likeRecipe(@PathVariable Long id){
        likeApiService.likeRecipe(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAuthority('DISLIKE_RECIPE')")
    @DeleteMapping(value = "/api/recipe/{id}/dislike")
    public ResponseEntity<Void> dislikeRecipe(@PathVariable Long id){
        likeApiService.dislikeRecipe(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
