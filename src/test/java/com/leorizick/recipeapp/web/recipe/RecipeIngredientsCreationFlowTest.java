package com.leorizick.recipeapp.web.recipe;

import com.leorizick.recipeapp.dto.recipe.IngredientCreationRequest;
import com.leorizick.recipeapp.dto.recipe.IngredientCrudResponse;
import com.leorizick.recipeapp.web.tools.AccountHelper;
import com.leorizick.recipeapp.web.tools.AuthenticationHelper;
import com.leorizick.recipeapp.web.tools.RecipeHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RecipeIngredientsCreationFlowTest {

    private final static String CREATE_INGREDIENT_API = "/api/recipe/{id}/ingredient";
    private final static String UPDATE_INGREDIENT_API = "/api/recipe/{recipeId}/ingredient/{ingredientId}";
    private final static String DELETE_INGREDIENT_API = "/api/recipe/ingredient/{id}";

    private final static String ANY_INGREDIENT = "500g de farinha";
    private final static String UPDATED_INGREDIENT = "Update de ingrediente";

    @Test
    public void shouldCreateARecipeIngredient(){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var recipe = RecipeHelper.createARecipe();

        var request = IngredientCreationRequest.builder()
                .ingredient(ANY_INGREDIENT)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipe.getId())
                .post(CREATE_INGREDIENT_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var recipeIngredientResponse = response.as(IngredientCrudResponse.class);

        Assertions.assertNotNull(recipeIngredientResponse.getId());
        Assertions.assertEquals(recipe.getId(), recipeIngredientResponse.getRecipe());
        Assertions.assertEquals(ANY_INGREDIENT, recipeIngredientResponse.getIngredient());

    }

    @Test
    public void shouldUpdateARecipeIngredient(){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var recipe = RecipeHelper.createARecipe();

        var request = IngredientCreationRequest.builder()
                .ingredient(ANY_INGREDIENT)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipe.getId())
                .post(CREATE_INGREDIENT_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var recipeIngredientResponse = response.as(IngredientCrudResponse.class);

        Assertions.assertNotNull(recipeIngredientResponse.getId());
        Assertions.assertEquals(recipe.getId(), recipeIngredientResponse.getRecipe());
        Assertions.assertEquals(ANY_INGREDIENT, recipeIngredientResponse.getIngredient());

        var updateRequest = IngredientCreationRequest.builder()
                .ingredient(UPDATED_INGREDIENT)
                .build();

        var updatedResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(updateRequest)
                .pathParam("recipeId", recipe.getId())
                .pathParam("ingredientId", recipeIngredientResponse.getId())
                .put(UPDATE_INGREDIENT_API);

        Assertions.assertEquals(HttpStatus.OK.value(), updatedResponse.getStatusCode());

        var updatedIngredientResponse = RecipeHelper.findRecipe(recipe);

        Assertions.assertEquals(recipeIngredientResponse.getRecipe(), updatedIngredientResponse.getId());
        Assertions.assertTrue(updatedIngredientResponse.getIngredients().stream().anyMatch(ingredient ->
                Objects.equals(ingredient.getIngredient(), UPDATED_INGREDIENT)));
    }

    @Test
    public void shouldReturnErrorWhenTryToUpdateAnRecipeIngredientWithNotAuthorAccountToken(){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var recipe = RecipeHelper.createARecipe();

        var request = IngredientCreationRequest.builder()
                .ingredient(ANY_INGREDIENT)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipe.getId())
                .post(CREATE_INGREDIENT_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var recipeIngredientResponse = response.as(IngredientCrudResponse.class);

        Assertions.assertNotNull(recipeIngredientResponse.getId());
        Assertions.assertEquals(recipe.getId(), recipeIngredientResponse.getRecipe());
        Assertions.assertEquals(ANY_INGREDIENT, recipeIngredientResponse.getIngredient());

        var account = AccountHelper.createCommonUserAccount("commonUserIngredient", "commonUserIngredient@gmail.com");
        var anotherAccountToken = AuthenticationHelper.getToken(account.getEmail(), "QWERT");

        var updateRequest = IngredientCreationRequest.builder()
                .ingredient(UPDATED_INGREDIENT)
                .build();

        var updatedResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, anotherAccountToken)
                .body(updateRequest)
                .pathParam("recipeId", recipe.getId())
                .pathParam("ingredientId", recipeIngredientResponse.getId())
                .put(UPDATE_INGREDIENT_API);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), updatedResponse.getStatusCode());
    }

    @Test
    public void shouldDeleteARecipeIngredientById(){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var recipe = RecipeHelper.createARecipe();

        var request = IngredientCreationRequest.builder()
                .ingredient(ANY_INGREDIENT)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipe.getId())
                .post(CREATE_INGREDIENT_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var recipeIngredientResponse = response.as(IngredientCrudResponse.class);

        var deleteResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipeIngredientResponse.getId())
                .delete(DELETE_INGREDIENT_API);

        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), deleteResponse.getStatusCode());
    }

    @Test
    public void shouldReturnErrorWhenTryToDeleteARecipeIngredientWithNotAuthorToken(){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var recipe = RecipeHelper.createARecipe();

        var request = IngredientCreationRequest.builder()
                .ingredient(ANY_INGREDIENT)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipe.getId())
                .post(CREATE_INGREDIENT_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var recipeIngredientResponse = response.as(IngredientCrudResponse.class);

        var account = AccountHelper.createCommonUserAccount("commonUserIngredient1", "commonUserIngredient1@gmail.com");
        var anotherAccountToken = AuthenticationHelper.getToken(account.getEmail(), "QWERT");

        var deleteResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, anotherAccountToken)
                .body(request)
                .pathParam("id", recipeIngredientResponse.getId())
                .delete(DELETE_INGREDIENT_API);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), deleteResponse.getStatusCode());
    }

}
