package com.leorizick.recipeapp.web.rating;


import com.leorizick.recipeapp.dto.rating.RatingDTO;
import com.leorizick.recipeapp.services.api.service.rating.RatingApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RatingController {

    private final RatingApiService ratingApiService;

    @PreAuthorize("hasAuthority('RECIPE_RATE')")
    @PostMapping(value = "/api/recipe/{id}/rate")
    public ResponseEntity<?> create(@PathVariable Long id, @RequestBody RatingDTO ratingDTO) {
        ratingApiService.create(id, ratingDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
