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

        BigDecimal totalCalories = new BigDecimal(0);

        BigDecimal totalWeight = new BigDecimal(0);

        BigDecimal totalCarbohydrate = new BigDecimal(0);

        BigDecimal totalProtein = new BigDecimal(0);

        BigDecimal totalFat = new BigDecimal(0);

        CalculatorTotalResponse calculatorTotalResponse = CalculatorTotalResponse.builder()
                .totalCalories(totalCalories)
                .totalCarbohydrate(totalCarbohydrate)
                .totalFat(totalFat)
                .totalProtein(totalProtein)
                .totalWeight(totalWeight)
                .build();

        for (Food food : foods) {
            var multiplier = food.getMultiplier();

            FoodResponse foodResponse = FoodResponse.builder()
                    .name(food.getName())
                    .calories( food.getCalories().multiply(multiplier))
                    .carbohydrate(food.getCarbohydrate().multiply(multiplier))
                    .protein(food.getProtein().multiply(multiplier))
                    .weight(food.getWeight().multiply(multiplier))
                    .fat(food.getFat().multiply(multiplier))
                    .build();

            foodResponseList.add(foodResponse);

            totalCalories = totalCalories.add(foodResponse.getCalories());
            totalWeight = totalWeight.add(foodResponse.getWeight());
            totalCarbohydrate = totalCarbohydrate.add(foodResponse.getCarbohydrate());
            totalProtein = totalProtein.add(foodResponse.getProtein());
            totalFat = totalFat.add(foodResponse.getFat());

            calculatorTotalResponse.getTotalCalories().add(foodResponse.getCalories());
            totalWeight = totalWeight.add(foodResponse.getWeight());
            totalCarbohydrate = totalCarbohydrate.add(foodResponse.getCarbohydrate());
            totalProtein = totalProtein.add(foodResponse.getProtein());
            totalFat = totalFat.add(foodResponse.getFat());
        }



        return CalculatorFinalResponse.builder()
                .foodList(foodResponseList)
                .calculatorTotalResponse(calculatorTotalResponse)
                .build();
    }
}
