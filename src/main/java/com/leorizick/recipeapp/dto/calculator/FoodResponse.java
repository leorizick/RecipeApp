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
public class FoodResponse {

    private String name;

    private BigDecimal calories;

    private BigDecimal weight;

    private BigDecimal carbohydrate;

    private BigDecimal protein;

    private BigDecimal fat;



}
