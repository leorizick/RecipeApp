package com.leorizick.recipeapp.web.account.password;

import com.leorizick.recipeapp.dto.account.AccountCreationResponse;
import com.leorizick.recipeapp.services.api.service.account.password.ForgotPasswordApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ForgotPasswordController {
    private final ForgotPasswordApiService forgotPasswordApiService;

    @PostMapping(value = "/api/account/forgot/password/{email}")
    public ResponseEntity<AccountCreationResponse> sendEmailCode(@PathVariable String email) {
        forgotPasswordApiService.sendEmailCode(email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
