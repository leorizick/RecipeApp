package com.leorizick.recipeapp.dto.recipe;


import com.leorizick.recipeapp.dto.account.AccountSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeCrudResponse {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private String description;
    private List<String> ingredients;
    private boolean enabled;
    private List<RecipeStepSummaryResponse> step;
    private List<CommentSummaryResponse> comment;
    private String category;
    private AccountSummaryResponse author;
    private Long likesCount;
    private boolean isLiked;

}
