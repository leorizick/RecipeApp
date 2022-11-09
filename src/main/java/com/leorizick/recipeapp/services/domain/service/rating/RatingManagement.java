package com.leorizick.recipeapp.services.domain.service.rating;

import com.leorizick.recipeapp.entities.rating.Rating;
import com.leorizick.recipeapp.repositories.rating.RatingRepository;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RatingManagement {

    private final RatingRepository ratingRepository;
    private final AuthenticationContext authenticationContext;

    @Transactional
    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Transactional
    public void deleteByRecipeId(Long recipeId, Long accountId) {
        ratingRepository.deleteByRecipeAndAccountId(recipeId, accountId);
    }

    public Integer getRatingCount(Long recipeId) {
        var value = ratingRepository.ratesCount(recipeId);
        if (value == null) value = 0;
        return value;
    }

    public Integer getRatingTotal(Long recipeId){
        var ratingSum = ratingRepository.ratingSum(recipeId);
        var ratingCount = getRatingCount(recipeId);
        var ratingTotal = 0;
        if (ratingSum != null && ratingSum > 0){
            ratingTotal = ratingSum / ratingCount;
        }
        return ratingTotal;
    }

}
