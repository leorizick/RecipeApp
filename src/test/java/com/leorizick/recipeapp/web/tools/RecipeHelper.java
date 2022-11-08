package com.leorizick.recipeapp.web.tools;

import com.leorizick.recipeapp.dto.recipe.RecipeCreationRequest;
import com.leorizick.recipeapp.dto.recipe.RecipeCrudResponse;
import com.leorizick.recipeapp.dto.recipe.RecipeStepCreationRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class RecipeHelper {

    private static final String BASIC_RECIPE_API = "api/recipe/create";
    private static final String GET_RECIPE_API = "api/recipe/{id}";

    private static final String ANY_RECIPE_NAME = "Boulu de murangu";
    private static final String ANY_RECIPE_DESCRIPTION = "Como fazer bolo de morango";
    private static final String ANY_RECIPE_INGREDIENTS = "Morango, 300g farinha, 3 ovos";
    private static final Long ANY_RECIPE_CATEGORY = 1L;
    private static final List<RecipeStepCreationRequest> ANY_RECIPE_STEPS = new ArrayList<>();



    public static RecipeCrudResponse createARecipe(){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        ANY_RECIPE_STEPS.add(new RecipeStepCreationRequest(null, "Misture a farinha com os ovos"));
        var request = RecipeCreationRequest.builder()
                .name(ANY_RECIPE_NAME)
                .description(ANY_RECIPE_DESCRIPTION)
                .ingredients(ANY_RECIPE_INGREDIENTS)
                .categoryId(ANY_RECIPE_CATEGORY)
                .steps(ANY_RECIPE_STEPS)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .post(BASIC_RECIPE_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        return response.as(RecipeCrudResponse.class);
    }

    public static RecipeCrudResponse findRecipe(RecipeCrudResponse recipe){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var getRecipeResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .pathParam("id", recipe.getId())
                .get(GET_RECIPE_API);

        return getRecipeResponse.as(RecipeCrudResponse.class);
    }

}
