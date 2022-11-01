package com.leorizick.recipeapp.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCreationRequest {
    private String name;
    private String username;
    private LocalDate birth;
    private String password;
    private String email;

}
