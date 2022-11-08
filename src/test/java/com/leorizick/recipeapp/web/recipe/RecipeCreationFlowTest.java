package com.leorizick.recipeapp.web.recipe;

import com.leorizick.recipeapp.dto.recipe.RecipeCreationRequest;
import com.leorizick.recipeapp.dto.recipe.RecipeCrudResponse;
import com.leorizick.recipeapp.web.tools.AccountHelper;
import com.leorizick.recipeapp.web.tools.AuthenticationHelper;
import com.leorizick.recipeapp.web.tools.RecipeHelper;
import com.leorizick.recipeapp.web.tools.RestPageImpl;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RecipeCreationFlowTest {

    private static final String BASIC_RECIPE_API = "api/recipe/create";
    private static final String RECIPE_API = "api/recipe/{id}";
    private static final String GET_ALL_RECIPE_API = "api/recipe";

    private static final String ANY_RECIPE_NAME = "Boulu de murangu";
    private static final String ANY_RECIPE_NAME_UPDATED = "\"Receita atualizada\"";
    private static final String ANY_RECIPE_DESCRIPTION = "Como fazer bolo de morango";
    private static final String ANY_RECIPE_INGREDIENTS = "Morango, 300g farinha, 3 ovos";
    private static final String ANY_RECIPE_CATEGORY = "Aves";
    private static final String ANY_RECIPE_STEPS ="Misture a farinha com os ovos";

    @Test
    public void shouldCreateARecipe(){
        var response = RecipeHelper.createARecipe();

        Assertions.assertNotNull(response.getId());
        Assertions.assertEquals(ANY_RECIPE_NAME, response.getName());
        Assertions.assertEquals(ANY_RECIPE_DESCRIPTION, response.getDescription());
        Assertions.assertEquals(ANY_RECIPE_INGREDIENTS, response.getIngredients());
        Assertions.assertEquals(ANY_RECIPE_CATEGORY, response.getCategory());
        Assertions.assertTrue(response.getStep().get(0).getStep().equals(ANY_RECIPE_STEPS));
        Assertions.assertTrue(response.isEnabled());
    }

    @Test
    public void shouldUpdateAnExistingRecipe(){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var request = RecipeCreationRequest.builder()
                .name(ANY_RECIPE_NAME)
                .description(ANY_RECIPE_DESCRIPTION)
                .ingredients(ANY_RECIPE_INGREDIENTS)
                .categoryId(1L)
                .steps(new ArrayList<>())
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .post(BASIC_RECIPE_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var recipeResponse = response.as(RecipeCrudResponse.class);

        request.setName(ANY_RECIPE_NAME_UPDATED);

        var updatedResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipeResponse.getId())
                .put(RECIPE_API);

        Assertions.assertEquals(HttpStatus.OK.value(), updatedResponse.getStatusCode());

        var updatedRecipeResponse = RecipeHelper.findRecipe(recipeResponse);

        Assertions.assertNotEquals(updatedRecipeResponse.getName(), recipeResponse.getName());
        Assertions.assertEquals(updatedRecipeResponse.getId(), recipeResponse.getId());
        Assertions.assertEquals(updatedRecipeResponse.getName(), ANY_RECIPE_NAME_UPDATED );
    }

    @Test
    public void shouldReturnErrorTryingToEditARecipeWithAnotherAuthorAccount(){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var request = RecipeCreationRequest.builder()
                .name(ANY_RECIPE_NAME)
                .description(ANY_RECIPE_DESCRIPTION)
                .ingredients(ANY_RECIPE_INGREDIENTS)
                .categoryId(1L)
                .steps(new ArrayList<>())
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .post(BASIC_RECIPE_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var recipeResponse = response.as(RecipeCrudResponse.class);

        request.setName(ANY_RECIPE_NAME_UPDATED);

        var account = AccountHelper.createCommonUserAccount();
        var anotherToken = AuthenticationHelper.getToken(account.getEmail(), "QWERT");

        var updatedResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, anotherToken)
                .body(request)
                .pathParam("id", recipeResponse.getId())
                .put(RECIPE_API);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), updatedResponse.getStatusCode());
    }

    @Test
    public void shouldFindARecipeById(){
        var recipe = RecipeHelper.createARecipe();
        var findRecipe = RecipeHelper.findRecipe(recipe);

        Assertions.assertEquals(recipe.getId(), findRecipe.getId());
    }

    @Test
    public void shouldFindAllRecipes() {
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var recipe = RecipeHelper.createARecipe();

        var findRecipe = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .get(GET_ALL_RECIPE_API);

        var responseBody = (Page<RecipeCrudResponse>) findRecipe.as(new TypeReference<RestPageImpl<RecipeCrudResponse>>(){}.getType());
        Assertions.assertEquals(HttpStatus.OK.value(), findRecipe.getStatusCode());
        Assertions.assertTrue(responseBody.stream()
                .map(x -> x.getName()).collect(Collectors.toSet())
                .contains(recipe.getName()));

    }

    @Test
    public void shouldDeleteARecipeById(){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var recipe = RecipeHelper.createARecipe();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .pathParam("id", recipe.getId())
                .delete(RECIPE_API);

        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());
    }

}
