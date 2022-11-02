package com.leorizick.recipeapp.web.account;

import com.auth0.jwt.JWT;
import com.leorizick.recipeapp.dto.account.AccountCreationRequest;
import com.leorizick.recipeapp.dto.account.AccountCreationResponse;
import com.leorizick.recipeapp.dto.auth.AuthenticationRequest;
import com.leorizick.recipeapp.dto.auth.AuthenticationResponse;
import com.leorizick.recipeapp.web.tools.AuthenticationHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AdminAccountCreationFlowTest {

    private static final String CREATE_DEFAULT_ACCOUNT_API = "/api/admin/account/create";
    private static final String AUTHENTICATION_API = "/api/v1/auth/token";

    private static final String ANY_ACCOUNT_NAME = "Luis José Carlos";
    private static final LocalDate ANY_ACCOUNT_BIRTH = LocalDate.of(2008,12,4);
    private static final String ANY_ACCOUNT_EMAIL = "luis.jose@recipeapp.com";
    private static final String ANY_ACCOUNT_USERNAME = "luis.jose";
    private static final String ANY_ACCOUNT_PASSWORD = "12werba!";
    private static final String ANY_ACCOUNT_WRONG_PASSWORD = "0000";

    private static final Set<String> EXPECTED_DEFAULT_ACCOUNT_PROFILES = Stream.of("USER","ADMIN").collect(Collectors.toCollection(HashSet::new));

    private static final String CLAIM_NAME_ACCOUNT_ID = "id";
    private static final String CLAIM_NAME_USERNAME = "username";
    private static final String CLAIM_NAME_PROFILES_LIST = "profiles";

    @Test
    public void shouldCreateAdminUserAccountUsingDefaultAdminAccountAndExecuteLoginWithEmailAndWithUsername() {

        tryToCreateWithoutAdminTokenAndReceiveAForbiddenError();

        var createdAccount = createAdminUserAccount();

        var credentialsUsingUsername = AuthenticationRequest.builder()
                .username(ANY_ACCOUNT_USERNAME)
                .password(ANY_ACCOUNT_PASSWORD)
                .build();

        authenticateWithSuccess(credentialsUsingUsername, createdAccount);

        var credentialsUsingEmail = AuthenticationRequest.builder()
                .username(ANY_ACCOUNT_EMAIL)
                .password(ANY_ACCOUNT_PASSWORD)
                .build();

        authenticateWithSuccess(credentialsUsingEmail, createdAccount);

        var credentialsWrongPassword = AuthenticationRequest.builder()
                .username(ANY_ACCOUNT_EMAIL)
                .password(ANY_ACCOUNT_WRONG_PASSWORD)
                .build();

        var authWrongPasswordResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(credentialsWrongPassword)
                .post(AUTHENTICATION_API);

        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), authWrongPasswordResponse.getStatusCode());
    }

    private void tryToCreateWithoutAdminTokenAndReceiveAForbiddenError() {

        var request = AccountCreationRequest.builder()
                .name(ANY_ACCOUNT_NAME)
                .birth(ANY_ACCOUNT_BIRTH)
                .email(ANY_ACCOUNT_EMAIL)
                .username(ANY_ACCOUNT_USERNAME)
                .password(ANY_ACCOUNT_PASSWORD)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .post(CREATE_DEFAULT_ACCOUNT_API);

        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCode());

    }

    private AccountCreationResponse createAdminUserAccount() {

        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var request = AccountCreationRequest.builder()
                .name(ANY_ACCOUNT_NAME)
                .birth(ANY_ACCOUNT_BIRTH)
                .email(ANY_ACCOUNT_EMAIL)
                .username(ANY_ACCOUNT_USERNAME)
                .password(ANY_ACCOUNT_PASSWORD)
                .build();

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(request)
                .post(CREATE_DEFAULT_ACCOUNT_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var responseBody = response.as(AccountCreationResponse.class);

        Assertions.assertNotNull(responseBody.getId());
        Assertions.assertEquals(ANY_ACCOUNT_NAME, responseBody.getName());
        Assertions.assertEquals(ANY_ACCOUNT_BIRTH, responseBody.getBirth());
        Assertions.assertEquals(ANY_ACCOUNT_EMAIL, responseBody.getEmail());
        Assertions.assertEquals(ANY_ACCOUNT_USERNAME, responseBody.getUsername());
        Assertions.assertTrue(responseBody.isEnabled());
        Assertions.assertIterableEquals(EXPECTED_DEFAULT_ACCOUNT_PROFILES, responseBody.getProfiles());

        return responseBody;
    }

    private void authenticateWithSuccess(AuthenticationRequest credentials, AccountCreationResponse createdAccount) {

        var authWithUsernameResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .post(AUTHENTICATION_API);

        Assertions.assertEquals(HttpStatus.OK.value(), authWithUsernameResponse.getStatusCode());

        var tokenResponseWithUsername = authWithUsernameResponse.as(AuthenticationResponse.class);

        var decodedToken = JWT.decode(tokenResponseWithUsername.getToken());

        Assertions.assertEquals(createdAccount.getId().toString(), decodedToken.getClaim(CLAIM_NAME_ACCOUNT_ID).asString());
        Assertions.assertEquals(ANY_ACCOUNT_EMAIL, decodedToken.getSubject());
        Assertions.assertEquals(ANY_ACCOUNT_USERNAME, decodedToken.getClaim(CLAIM_NAME_USERNAME).asString());
        Assertions.assertIterableEquals(EXPECTED_DEFAULT_ACCOUNT_PROFILES, decodedToken.getClaim(CLAIM_NAME_PROFILES_LIST).asList(String.class));
    }

}