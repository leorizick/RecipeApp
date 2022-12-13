package com.leorizick.recipeapp.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AccountSummaryResponse {
    private Long id;
    private String name;
    private String username;
    private String accountImageName;
}
