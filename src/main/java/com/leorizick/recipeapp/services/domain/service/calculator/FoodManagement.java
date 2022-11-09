package com.leorizick.recipeapp.services.domain.service.calculator;

import com.leorizick.recipeapp.entities.calculator.Food;
import com.leorizick.recipeapp.repositories.calculator.FoodRepository;
import com.leorizick.recipeapp.services.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodManagement {

    private final FoodRepository foodRepository;

    public Food findById(Long id){
        return foodRepository.findById(id).orElseThrow(() -> new NotFoundException("Food not found, Id:" + id));
    }
}
