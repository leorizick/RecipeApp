package com.leorizick.recipeapp.web.account.password;

import com.leorizick.recipeapp.dto.account.AccountCreationResponse;
import com.leorizick.recipeapp.dto.account.password.PasswordChangeRequest;
import com.leorizick.recipeapp.services.api.service.account.password.ChangePasswordApiService;
import com.leorizick.recipeapp.services.api.service.account.password.ForgotPasswordApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChangePasswordController {
    private final ChangePasswordApiService changePasswordApiService;

    @PostMapping(value = "/api/account/change/password/{id}")
    public ResponseEntity<AccountCreationResponse> changePasswordWithCurrentPassword(@PathVariable Long id, @RequestBody PasswordChangeRequest passwordChangeRequest) {
        changePasswordApiService.changePasswordWithCurrentPassword(passwordChangeRequest, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping(value = "/api/account/change/password/code")
    public ResponseEntity<AccountCreationResponse> changePasswordWithRecoveryCode( @RequestBody PasswordChangeRequest passwordChangeRequest) {
        changePasswordApiService.changePasswordWithRecoveryCode(passwordChangeRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }



}
