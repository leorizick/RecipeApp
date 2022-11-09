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
public class FoodCreationRequest {

    private Long foodId;
    private BigDecimal multiplier;
}
