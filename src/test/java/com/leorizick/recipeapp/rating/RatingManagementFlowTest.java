package com.leorizick.recipeapp.rating;

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
public class RatingManagementFlowTest {

    private static final String RATING_RECIPE_API = "/api/recipe/{id}/rate";
    private static final Integer ANY_RATING = 5;

    @Test
    public void shouldRateARecipe(){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();
        var recipe = RecipeHelper.createARecipe();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .pathParam("id", recipe.getId())
                .body(ANY_RATING)
                .post(RATING_RECIPE_API);

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        var recipeLiked = RecipeHelper.findRecipe(recipe);

        Assertions.assertEquals(ANY_RATING, recipeLiked.getRating());
    }

    @Test
    public void shouldRateARecipeTwiceWithSameUserAndReturnJustOneRatesCount(){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();
        var recipe = RecipeHelper.createARecipe();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .pathParam("id", recipe.getId())
                .body(ANY_RATING)
                .post(RATING_RECIPE_API);

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        var recipeLiked = RecipeHelper.findRecipe(recipe);

        Assertions.assertEquals(ANY_RATING, recipeLiked.getRating());

        var twiceResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .pathParam("id", recipe.getId())
                .body(ANY_RATING)
                .post(RATING_RECIPE_API);

        Assertions.assertEquals(HttpStatus.OK.value(), twiceResponse.getStatusCode());

        recipeLiked = RecipeHelper.findRecipe(recipe);

        Assertions.assertEquals(ANY_RATING, recipeLiked.getRating());
        Assertions.assertTrue(recipeLiked.getLikesCount() < 2);
    }

}
