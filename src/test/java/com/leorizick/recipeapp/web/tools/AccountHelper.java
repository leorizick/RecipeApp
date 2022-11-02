package com.leorizick.recipeapp.web.tools;

import com.leorizick.recipeapp.dto.account.AccountCreationRequest;
import com.leorizick.recipeapp.dto.account.AccountCreationResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Set;

public class AccountHelper {

    public static final String CREATE_DEFAULT_ACCOUNT_API = "/api/account/create";
    private static final String ANY_ACCOUNT_NAME = "Jurema Joseta Jasmin";
    private static final LocalDate ANY_ACCOUNT_BIRTH = LocalDate.of(2022,10,6);
    private static final String ANY_ACCOUNT_EMAIL = "jurema@gmail.com";
    private static final String ANY_ACCOUNT_USERNAME = "jurema";
    private static final String ANY_ACCOUNT_PASSWORD = "QWERT";

    private static final Set<String> EXPECTED_DEFAULT_ACCOUNT_PROFILES = Set.of("USER");

    public static AccountCreationResponse createCommonUserAccount(String username, String email) {
        var request = AccountCreationRequest.builder()
                .name(ANY_ACCOUNT_NAME)
                .birth(ANY_ACCOUNT_BIRTH)
                .email(email)
                .username(username)
                .password(ANY_ACCOUNT_PASSWORD)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .post(CREATE_DEFAULT_ACCOUNT_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var responseBody = response.as(AccountCreationResponse.class);

        Assertions.assertNotNull(responseBody.getId());
        Assertions.assertEquals(ANY_ACCOUNT_NAME, responseBody.getName());
        Assertions.assertEquals(ANY_ACCOUNT_BIRTH, responseBody.getBirth());
        Assertions.assertEquals(username, responseBody.getUsername());
        Assertions.assertTrue(responseBody.isEnabled());
        Assertions.assertIterableEquals(EXPECTED_DEFAULT_ACCOUNT_PROFILES, responseBody.getProfiles());

        return responseBody;
    }

    public static AccountCreationResponse createCommonUserAccount() {
        return createCommonUserAccount(ANY_ACCOUNT_USERNAME, ANY_ACCOUNT_EMAIL);
    }
}
