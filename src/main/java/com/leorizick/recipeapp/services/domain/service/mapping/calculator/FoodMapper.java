package com.leorizick.recipeapp.services.domain.service.mapping.calculator;

import com.leorizick.recipeapp.dto.calculator.FoodCreationRequest;
import com.leorizick.recipeapp.dto.rating.RatingDTO;
import com.leorizick.recipeapp.entities.calculator.Food;
import com.leorizick.recipeapp.entities.rating.Rating;
import com.leorizick.recipeapp.repositories.calculator.FoodRepository;
import com.leorizick.recipeapp.services.domain.service.calculator.CalorieCalculatorManagement;
import com.leorizick.recipeapp.services.domain.service.calculator.FoodManagement;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class FoodMapper {

    private final ModelMapper modelMapper;
    private final FoodManagement foodManagement;

    @PostConstruct
    public void configure() {
        createFoodFromFoodCreationRequest();
    }

    private void createFoodFromFoodCreationRequest() {
        modelMapper.createTypeMap(FoodCreationRequest.class, Food.class).setConverter(context -> {
            var src = context.getSource();
            Food food = foodManagement.findById(src.getFoodId());
            food.setMultiplier(src.getMultiplier());
            return food;
        });
    }
}

