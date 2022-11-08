package com.leorizick.recipeapp.web.recipe;

import com.leorizick.recipeapp.dto.recipe.RecipeStepCreationRequest;
import com.leorizick.recipeapp.dto.recipe.RecipeStepCrudResponse;
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
public class RecipeStepCreationFlowTest {

    private final static String CREATE_RECIPE_STEP_API = "/api/recipe/{id}/step";
    private final static String UPDATE_RECIPE_STEP_API = "/api/recipe/{recipeId}/step/{stepId}";
    private final static String DELETE_RECIPE_STEP_API = "/api/recipe/step/{id}";

    private final static String ANY_STEP = "Adicione os ingredientes em um recipiente";
    private final static String UPDATED_STEP = "Update de passo";

    @Test
    public void shouldCreateARecipeStep(){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var recipe = RecipeHelper.createARecipe();

        var request = RecipeStepCreationRequest.builder()
                .step(ANY_STEP)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipe.getId())
                .post(CREATE_RECIPE_STEP_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var recipeStepResponse = response.as(RecipeStepCrudResponse.class);

        Assertions.assertNotNull(recipeStepResponse.getId());
        Assertions.assertEquals(recipe.getId(), recipeStepResponse.getRecipe());
        Assertions.assertEquals(ANY_STEP, recipeStepResponse.getStep());

    }

    @Test
    public void shouldUpdateARecipeStep(){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var recipe = RecipeHelper.createARecipe();

        var request = RecipeStepCreationRequest.builder()
                .step(ANY_STEP)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipe.getId())
                .post(CREATE_RECIPE_STEP_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var recipeStepResponse = response.as(RecipeStepCrudResponse.class);

        Assertions.assertNotNull(recipeStepResponse.getId());
        Assertions.assertEquals(recipe.getId(), recipeStepResponse.getRecipe());
        Assertions.assertEquals(ANY_STEP, recipeStepResponse.getStep());

        var updateRequest = RecipeStepCreationRequest.builder()
                .step(UPDATED_STEP)
                .build();

        var updatedResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(updateRequest)
                .pathParam("recipeId", recipe.getId())
                .pathParam("stepId", recipeStepResponse.getId())
                .put(UPDATE_RECIPE_STEP_API);

        Assertions.assertEquals(HttpStatus.OK.value(), updatedResponse.getStatusCode());

        var updatedStepResponse = RecipeHelper.findRecipe(recipe);

        Assertions.assertEquals(recipeStepResponse.getRecipe(), updatedStepResponse.getId());
        Assertions.assertTrue(updatedStepResponse.getStep().stream().anyMatch(step ->
                Objects.equals(step.getStep(), UPDATED_STEP)));
    }

    @Test
    public void shouldReturnErrorWhenTryToUpdateAnRecipeStepWithNotAuthorAccountToken(){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var recipe = RecipeHelper.createARecipe();

        var request = RecipeStepCreationRequest.builder()
                .step(ANY_STEP)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipe.getId())
                .post(CREATE_RECIPE_STEP_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var recipeStepResponse = response.as(RecipeStepCrudResponse.class);

        Assertions.assertNotNull(recipeStepResponse.getId());
        Assertions.assertEquals(recipe.getId(), recipeStepResponse.getRecipe());
        Assertions.assertEquals(ANY_STEP, recipeStepResponse.getStep());

        var account = AccountHelper.createCommonUserAccount("commonUser", "commonUser@gmail.com");
        var anotherAccountToken = AuthenticationHelper.getToken(account.getEmail(), "QWERT");

        var updateRequest = RecipeStepCreationRequest.builder()
                .step(UPDATED_STEP)
                .build();

        var updatedResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, anotherAccountToken)
                .body(updateRequest)
                .pathParam("recipeId", recipe.getId())
                .pathParam("stepId", recipeStepResponse.getId())
                .put(UPDATE_RECIPE_STEP_API);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), updatedResponse.getStatusCode());
    }

    @Test
    public void shouldDeleteARecipeStepById(){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var recipe = RecipeHelper.createARecipe();

        var request = RecipeStepCreationRequest.builder()
                .step(ANY_STEP)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipe.getId())
                .post(CREATE_RECIPE_STEP_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var recipeStepResponse = response.as(RecipeStepCrudResponse.class);

        var deleteResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipeStepResponse.getId())
                .delete(DELETE_RECIPE_STEP_API);

        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), deleteResponse.getStatusCode());
    }

    @Test
    public void shouldReturnErrorWhenTryToDeleteARecipeStepWithNotAuthorToken(){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var recipe = RecipeHelper.createARecipe();

        var request = RecipeStepCreationRequest.builder()
                .step(ANY_STEP)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipe.getId())
                .post(CREATE_RECIPE_STEP_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var recipeStepResponse = response.as(RecipeStepCrudResponse.class);

        var account = AccountHelper.createCommonUserAccount("commonUser2", "commonUser2@gmail.com");
        var anotherAccountToken = AuthenticationHelper.getToken(account.getEmail(), "QWERT");

        var deleteResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, anotherAccountToken)
                .body(request)
                .pathParam("id", recipeStepResponse.getId())
                .delete(DELETE_RECIPE_STEP_API);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), deleteResponse.getStatusCode());
    }

}
