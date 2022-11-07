package com.leorizick.recipeapp.web.like;

import com.leorizick.recipeapp.web.tools.AuthenticationHelper;
import com.leorizick.recipeapp.web.tools.RecipeHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LikeManagementFlowTest {

    private static final String LIKE_RECIPE_API = "/api/recipe/{id}/like";
    private static final String DISLIKE_RECIPE_API = "/api/recipe/{id}/dislike";

    @Test
    public void shouldLikeARecipeAndDislikeAfter(){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();
        var recipe = RecipeHelper.createAnRecipe();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .pathParam("id", recipe.getId())
                .post(LIKE_RECIPE_API);

        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());

        var recipeLiked = RecipeHelper.findRecipe(recipe);

        Assertions.assertTrue(recipeLiked.isLiked());
        Assertions.assertTrue(recipeLiked.getLikesCount() > 0);

        var dislikeResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .pathParam("id", recipe.getId())
                .delete(DISLIKE_RECIPE_API);

        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), dislikeResponse.getStatusCode());

        var recipeDisliked = RecipeHelper.findRecipe(recipe);

        Assertions.assertFalse(recipeDisliked.isLiked());
        Assertions.assertTrue(recipeDisliked.getLikesCount() == 0);

    }


}
