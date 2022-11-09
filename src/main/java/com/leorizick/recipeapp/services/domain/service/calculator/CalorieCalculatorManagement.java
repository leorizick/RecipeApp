package com.leorizick.recipeapp.services.domain.service.calculator;

import com.leorizick.recipeapp.dto.calculator.CalculatorFinalResponse;
import com.leorizick.recipeapp.dto.calculator.CalculatorTotalResponse;
import com.leorizick.recipeapp.dto.calculator.FoodResponse;
import com.leorizick.recipeapp.entities.calculator.Food;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalorieCalculatorManagement {

    public CalculatorFinalResponse multiplyFoodContent(List<Food> foods) {
        List<FoodResponse> foodResponseList = new ArrayList<>();

        CalculatorTotalResponse calculatorTotalResponse = CalculatorTotalResponse.builder()
                .totalCalories(new BigDecimal(0))
                .totalCarbohydrate(new BigDecimal(0))
                .totalFat(new BigDecimal(0))
                .totalProtein(new BigDecimal(0))
                .totalWeight(new BigDecimal(0))
                .build();

        for (Food food : foods) {
            var multiplier = food.getMultiplier();

            FoodResponse foodResponse = FoodResponse.builder()
                    .name(food.getName())
                    .calories(food.getCalories().multiply(multiplier))
                    .carbohydrate(food.getCarbohydrate().multiply(multiplier))
                    .protein(food.getProtein().multiply(multiplier))
                    .weight(food.getWeight().multiply(multiplier))
                    .fat(food.getFat().multiply(multiplier))
                    .build();
            foodResponseList.add(foodResponse);

            calculatorTotalResponse.setTotalCalories(calculatorTotalResponse.getTotalCalories()
                    .add(foodResponse.getCalories()));
            calculatorTotalResponse.setTotalWeight(calculatorTotalResponse.getTotalWeight()
                    .add(foodResponse.getWeight()));
            calculatorTotalResponse.setTotalCarbohydrate(calculatorTotalResponse.getTotalCarbohydrate()
                    .add(foodResponse.getCarbohydrate()));
            calculatorTotalResponse.setTotalProtein(calculatorTotalResponse.getTotalProtein()
                    .add(foodResponse.getProtein()));
            calculatorTotalResponse.setTotalFat(calculatorTotalResponse.getTotalFat()
                    .add((foodResponse.getFat())));
        }

        return CalculatorFinalResponse.builder()
                .foodList(foodResponseList)
                .calculatorTotalResponse(calculatorTotalResponse)
                .build();
    }
}
