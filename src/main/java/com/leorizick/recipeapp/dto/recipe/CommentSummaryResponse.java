package com.leorizick.recipeapp.dto.recipe;

import com.leorizick.recipeapp.dto.account.AccountSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentSummaryResponse {

    private Long id;
    private AccountSummaryResponse author;
    private String body;

}
