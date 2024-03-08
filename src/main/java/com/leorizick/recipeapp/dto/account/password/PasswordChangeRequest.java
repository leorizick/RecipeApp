package com.leorizick.recipeapp.dto.account.password;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordChangeRequest {
    private String email;
    private String currentPassword;
    private String newPassword;
    private String recoveryCode;

}
