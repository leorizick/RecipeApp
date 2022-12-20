package com.leorizick.recipeapp.services.api.service.calculator;

import com.leorizick.recipeapp.dto.calculator.CalculatorFinalResponse;
import com.leorizick.recipeapp.dto.calculator.FoodCreationRequest;
import com.leorizick.recipeapp.dto.calculator.FoodResponse;
import com.leorizick.recipeapp.entities.calculator.Food;
import com.leorizick.recipeapp.services.domain.service.calculator.CalorieCalculatorManagement;
import com.leorizick.recipeapp.services.domain.service.calculator.FoodManagement;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalorieCalculatorApiService {

    private final CalorieCalculatorManagement calorieCalculatorManagement;
    private final FoodManagement foodManagement;
    private final ModelMapper modelMapper;

    public CalculatorFinalResponse create(List<FoodCreationRequest> foodCreationRequest) {
        var listFood = foodCreationRequest.stream()
                .map(food -> modelMapper.map(food, Food.class)).collect(Collectors.toList());

        return calorieCalculatorManagement.multiplyFoodContent(listFood);
    }

    public Page<Food> findAll(Pageable pageable) {
        return foodManagement.findAll(pageable);
    }
}
