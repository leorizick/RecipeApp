package com.leorizick.recipeapp.dto.recipe;


import com.leorizick.recipeapp.dto.account.AccountSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentCrudResponse {

    private Long id;
    private LocalDateTime date;
    private AccountSummaryResponse author;
    private Long recipe;
    private Integer authorRating;
    private String body;
    private boolean enabled;

}
