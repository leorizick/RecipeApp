package com.leorizick.recipeapp.dto.calculator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalculatorTotalResponse {

    private BigDecimal totalCalories;

    private BigDecimal totalWeight;

    private BigDecimal totalCarbohydrate;

    private BigDecimal totalProtein;

    private BigDecimal totalFat;
}
