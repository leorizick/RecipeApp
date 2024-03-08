package com.leorizick.recipeapp.dto.recipe;

import com.leorizick.recipeapp.dto.account.AccountSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentSummaryResponse {

    private Long id;
    private AccountSummaryResponse author;
    private String body;
    private LocalDateTime date;
    private Integer authorRating;

}
