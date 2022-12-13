package com.leorizick.recipeapp.dto.recipe;

import com.leorizick.recipeapp.dto.account.AccountSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RecipeSummaryResponse {
    private Long id;
    private String name;
    private boolean enabled;
    private String category;
    private AccountSummaryResponse author;
    private Long likesCount;
    private Integer ratesCount;
    private Integer rating;
    private boolean isLiked;
    private String coverName;
}
