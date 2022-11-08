package com.leorizick.recipeapp.web.recipe;

import com.leorizick.recipeapp.dto.recipe.CommentCreationRequest;
import com.leorizick.recipeapp.dto.recipe.CommentCrudResponse;
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

import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CommentCreationFlowTest {

    private static final String CREATE_COMMENT_API = "/api/recipe/{id}/comment";
    private static final String BASIC_COMMENT_API = "/api/recipe/comment/{id}";
    private static final String GET_ALL_COMMENTS_API = "/api/recipe/{id}/comment";

    private static final String ANY_COMMENT_BODY = "Huuum que receita deliciosa!";
    private static final String ANOTHER_COMMENT_BODY = "Comentario atualizado!";

    @Test
    public void shouldCreateAnComment() {
        var recipe = RecipeHelper.createARecipe();
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();


        var request = CommentCreationRequest.builder()
                .body(ANY_COMMENT_BODY)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .headers(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipe.getId())
                .post(CREATE_COMMENT_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var commentResponse = response.as(CommentCrudResponse.class);

        Assertions.assertEquals(ANY_COMMENT_BODY, commentResponse.getBody());
        Assertions.assertEquals(recipe.getId(), commentResponse.getRecipe());
    }

    @Test
    public void shouldUpdateAnExistingComment() {
        var recipe = RecipeHelper.createARecipe();
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();


        var request = CommentCreationRequest.builder()
                .body(ANY_COMMENT_BODY)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .headers(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipe.getId())
                .post(CREATE_COMMENT_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var original = response.as(CommentCrudResponse.class);

        request.setBody(ANOTHER_COMMENT_BODY);

        var updatedResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .headers(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", original.getId())
                .put(BASIC_COMMENT_API);

        Assertions.assertEquals(HttpStatus.OK.value(), updatedResponse.getStatusCode());

        var findResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .headers(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", original.getId())
                .get(BASIC_COMMENT_API);

        Assertions.assertEquals(HttpStatus.OK.value(), findResponse.getStatusCode());

        var comment = findResponse.as(CommentCrudResponse.class);

        Assertions.assertEquals(ANOTHER_COMMENT_BODY, comment.getBody());
        Assertions.assertEquals(recipe.getId(), comment.getRecipe());
    }

    @Test
    public void shouldReturnErrorTryingToEditAnCommentWithAnotherAuthorAccount() {
        var recipe = RecipeHelper.createARecipe();
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();


        var request = CommentCreationRequest.builder()
                .body(ANY_COMMENT_BODY)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .headers(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipe.getId())
                .post(CREATE_COMMENT_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var original = response.as(CommentCrudResponse.class);

        request.setBody(ANOTHER_COMMENT_BODY);
        var account = AccountHelper.createCommonUserAccount("commonAccount1", "commonAccount1@gmail.com");
        var anotherToken = AuthenticationHelper.getToken(account.getEmail(), "QWERT");

        var updatedResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .headers(HttpHeaders.AUTHORIZATION, anotherToken)
                .body(request)
                .pathParam("id", original.getId())
                .put(BASIC_COMMENT_API);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), updatedResponse.getStatusCode());
    }

    @Test
    public void shouldFindAnCommentById() {
        var recipe = RecipeHelper.createARecipe();
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var request = CommentCreationRequest.builder()
                .body(ANY_COMMENT_BODY)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .headers(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipe.getId())
                .post(CREATE_COMMENT_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var original = response.as(CommentCrudResponse.class);

        var findResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .headers(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", original.getId())
                .get(BASIC_COMMENT_API);

        Assertions.assertEquals(HttpStatus.OK.value(), findResponse.getStatusCode());

        var comment = findResponse.as(CommentCrudResponse.class);

        Assertions.assertEquals(ANY_COMMENT_BODY, comment.getBody());
        Assertions.assertEquals(recipe.getId(), comment.getRecipe());
    }

    @Test
    public void shouldFindAllRecipes() {
        var recipe = RecipeHelper.createARecipe();
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var request = CommentCreationRequest.builder()
                .body(ANY_COMMENT_BODY)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .headers(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipe.getId())
                .post(CREATE_COMMENT_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var findComment = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .pathParam("id", recipe.getId())
                .get(GET_ALL_COMMENTS_API);

        var responseBody = (Page<CommentCrudResponse>) findComment.as(new TypeReference<RestPageImpl<CommentCrudResponse>>() {
        }.getType());
        Assertions.assertEquals(HttpStatus.OK.value(), findComment.getStatusCode());
        Assertions.assertTrue(responseBody.stream()
                .map(x -> x.getBody()).collect(Collectors.toSet())
                .contains(ANY_COMMENT_BODY));
    }

    @Test
    public void shouldDeleteAnRecipeById() {
        var recipe = RecipeHelper.createARecipe();
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var request = CommentCreationRequest.builder()
                .body(ANY_COMMENT_BODY)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .headers(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .pathParam("id", recipe.getId())
                .post(CREATE_COMMENT_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var comment = response.as(CommentCrudResponse.class);

        var deleteResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .pathParam("id", comment.getId())
                .delete(BASIC_COMMENT_API);

        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), deleteResponse.getStatusCode());
    }

}
