package com.leorizick.recipeapp.web.tools;

import com.leorizick.recipeapp.dto.auth.AuthenticationRequest;
import com.leorizick.recipeapp.dto.auth.AuthenticationResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;

public class AuthenticationHelper {

    public static final String DEFAULT_ACCOUNT_USERNAME = "admin-1";
    public static final String DEFAULT_ACCOUNT_EMAIL = "admin@recipe.com";
    public static final String DEFAULT_ACCOUNT_PASSWORD = "QWERT";

    public static final String AUTHENTICATION_URL = "/api/v1/auth/token";

    public static final String AUTHENTICATION_TOKEN_PREFIX = "Bearer ";

    public static String getToken(String usernameOrEmail, String password) {
        var request = AuthenticationRequest.builder()
                .username(usernameOrEmail)
                .password(password)
                .build();

        return getToken(request);
    }

    public static String getTokenDefaultAccount() {
        var request = AuthenticationRequest.builder()
                .username(DEFAULT_ACCOUNT_EMAIL)
                .password(DEFAULT_ACCOUNT_PASSWORD)
                .build();

        return getToken(request);
    }

    public static String getToken(AuthenticationRequest request) {
        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .post(AUTHENTICATION_URL);

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        return AUTHENTICATION_TOKEN_PREFIX + response.as(AuthenticationResponse.class)
                .getToken();
    }

}
