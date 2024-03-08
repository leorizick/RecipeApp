package com.leorizick.recipeapp.repositories.file;

import com.leorizick.recipeapp.entities.file.RecipeImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeImgRepository extends JpaRepository<RecipeImg, Long > {

    @Modifying
    @Query("delete from RecipeImg ri where ri.recipe.id = :recipeId and ri.cover = true")
    void deleteByRecipeAndAccountId(Long recipeId);

    @Modifying
    @Query("delete from RecipeImg ri where ri.recipe.id = :id and ri.name = :name")
    void deleteRecipeImgByName(Long id, String name);

    @Query("select ri from RecipeImg ri where ri.recipe.id = :recipeId and cover = true")
    RecipeImg getRecipeCoverImg(Long recipeId);

    @Query("select ri from RecipeImg ri where ri.recipe.id = :recipeId and cover = false")
    List<RecipeImg> findAllNotCoverByRecipeId(Long recipeId);

    List<RecipeImg> findAllByRecipeId(Long recipeId);

}
