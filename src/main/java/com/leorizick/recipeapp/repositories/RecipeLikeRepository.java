package com.leorizick.recipeapp.repositories;

import com.leorizick.recipeapp.entities.like.RecipeLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RecipeLikeRepository extends JpaRepository<RecipeLike, Long> {

    @Modifying
    @Query("delete from RecipeLike rl where rl.recipe.id = :recipeId and rl.account.id = :accountId")
    void deleteByRecipeAndAccountId(Long recipeId, Long accountId);

    @Query("select count(*) from RecipeLike rl where rl.recipe.id = :recipeId")
    Long likeCount(Long recipeId);

    @Query("select count(*) > 0 from RecipeLike rl where rl.recipe.id = :recipeId and rl.account.id = :accountId")
    boolean isLiked(Long recipeId, Long accountId);
}
