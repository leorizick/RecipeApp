package com.leorizick.recipeapp.calculator;

import com.leorizick.recipeapp.dto.calculator.CalculatorFinalResponse;
import com.leorizick.recipeapp.dto.calculator.FoodCreationRequest;
import com.leorizick.recipeapp.dto.calculator.FoodResponse;
import com.leorizick.recipeapp.web.tools.AuthenticationHelper;
import com.leorizick.recipeapp.web.tools.RecipeHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CalorieCalculatorManagementFlowTest {

    private final String BASIC_CALCULATOR_API = "/api/calculator/create";

    private final Long ANY_FOOD_ID = 1L;
    private final BigDecimal ANY_FOOD_MULTIPLIER = BigDecimal.valueOf(2);

    @Test
    public void shouldCalculateAListOfFood(){
        var preRegisteredAdminToken = AuthenticationHelper.getTokenDefaultAccount();

        var request = FoodCreationRequest.builder()
                .foodId(ANY_FOOD_ID)
                .multiplier(ANY_FOOD_MULTIPLIER)
                .build();

        var request1 = FoodCreationRequest.builder()
                .foodId(ANY_FOOD_ID)
                .multiplier(ANY_FOOD_MULTIPLIER)
                .build();

        List<FoodCreationRequest> foodList = new ArrayList<>();
        foodList.add(request);
        foodList.add(request1);

        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, preRegisteredAdminToken)
                .body(foodList)
                .post(BASIC_CALCULATOR_API);

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        var calculated = response.as(CalculatorFinalResponse.class);

        Assertions.assertNotNull(calculated.getFoodList());
        Assertions.assertTrue(calculated.getCalculatorTotalResponse().getTotalCalories().doubleValue() > 0.0);
        Assertions.assertTrue(calculated.getFoodList().size() > 1);
        
    }
}
