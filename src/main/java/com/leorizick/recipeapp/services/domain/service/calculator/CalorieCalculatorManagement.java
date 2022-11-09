package com.leorizick.recipeapp.services.domain.service.calculator;

import com.leorizick.recipeapp.dto.calculator.CalculatorFinalResponse;
import com.leorizick.recipeapp.dto.calculator.CalculatorTotalResponse;
import com.leorizick.recipeapp.dto.calculator.FoodResponse;
import com.leorizick.recipeapp.entities.calculator.Food;
import com.leorizick.recipeapp.repositories.calculator.FoodRepository;
import com.leorizick.recipeapp.services.exceptions.NotFoundException;
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

        for (Food food : foods) {
            var name = food.getName();
            var calories = food.getCalories();
            var carbohydrate = food.getCarbohydrate();
            var protein = food.getProtein();
            var weight = food.getWeight();
            var fat = food.getFat();
            var multiplier = food.getMultiplier();

            FoodResponse foodResponse = FoodResponse.builder()
                    .name(name)
                    .calories(calories.multiply(multiplier))
                    .carbohydrate(carbohydrate.multiply(multiplier))
                    .protein(protein.multiply(multiplier))
                    .weight(weight.multiply(multiplier))
                    .fat(fat.multiply(multiplier))
                    .build();

            foodResponseList.add(foodResponse);

            totalCalories = totalCalories.add(foodResponse.getCalories());
            totalWeight = totalWeight.add(foodResponse.getWeight());
            totalCarbohydrate = totalCarbohydrate.add(foodResponse.getCarbohydrate());
            totalProtein = totalProtein.add(foodResponse.getProtein());
            totalFat = totalFat.add(foodResponse.getFat());
        }
        CalculatorTotalResponse calculatorTotalResponse = CalculatorTotalResponse.builder()
                .totalCalories(totalCalories)
                .totalCarbohydrate(totalCarbohydrate)
                .totalFat(totalFat)
                .totalProtein(totalProtein)
                .totalWeight(totalWeight)
                .build();

        return CalculatorFinalResponse.builder()
                .foodList(foodResponseList)
                .calculatorTotalResponse(calculatorTotalResponse)
                .build();
    }
}
