package com.leorizick.recipeapp.web.calculator;

import com.leorizick.recipeapp.dto.calculator.CalculatorFinalResponse;
import com.leorizick.recipeapp.dto.calculator.FoodCreationRequest;
import com.leorizick.recipeapp.services.api.service.calculator.CalorieCalculatorApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CalorieCalculatorController {

    private final CalorieCalculatorApiService calorieCalculatorApiService;

    @PreAuthorize("hasAuthority('CALCULATOR_CREATE')")
    @PostMapping(value = "/api/calculator/create")
    public ResponseEntity<CalculatorFinalResponse> likeRecipe(@RequestBody List<FoodCreationRequest> foodList){
        CalculatorFinalResponse calculatorFinalResponse = calorieCalculatorApiService.create(foodList);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(calculatorFinalResponse);
    }

}
