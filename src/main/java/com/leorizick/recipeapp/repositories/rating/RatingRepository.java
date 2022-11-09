package com.leorizick.recipeapp.repositories.rating;

import com.leorizick.recipeapp.entities.rating.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Modifying
    @Query("delete from Rating r where r.recipe.id = :recipeId and r.account.id = :accountId")
    void deleteByRecipeAndAccountId(Long recipeId, Long accountId);

    @Query("select count(*) from Rating r where r.recipe.id = :recipeId")
    Integer ratesCount(Long recipeId);

    @Query("SELECT SUM(r.rating) FROM Rating r where r.recipe.id = :recipeId")
    Integer ratingSum(Long recipeId);
}
