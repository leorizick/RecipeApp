package com.leorizick.recipeapp.dto.calculator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalculatorFinalResponse {

    private List<FoodResponse> foodList;
    private CalculatorTotalResponse calculatorTotalResponse;
}
