package com.leorizick.recipeapp.web.account;

import com.leorizick.recipeapp.dto.account.AccountAdminCreationRequest;
import com.leorizick.recipeapp.dto.account.AccountCreationRequest;
import com.leorizick.recipeapp.dto.account.AccountCreationResponse;
import com.leorizick.recipeapp.dto.account.AccountSummaryResponse;
import com.leorizick.recipeapp.services.api.service.account.AccountApiService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequiredArgsConstructor
public class AccountCreationController {
    private final AccountApiService accountApiService;

    @PostMapping(value = "/api/account/create")
    public ResponseEntity<AccountCreationResponse> createAccount(@RequestBody AccountCreationRequest accountCreationRequest) {
        AccountCreationResponse accountCreationResponse = accountApiService.createAccount(accountCreationRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountCreationResponse);
    }

    @PreAuthorize("hasAuthority('CREATE_ADMIN_ACCOUNT')")
    @PostMapping(value = "/api/admin/account/create")
    public ResponseEntity<AccountCreationResponse> createAdminAccount(@RequestBody AccountAdminCreationRequest accountAdminCreationRequest) {
        AccountCreationResponse accountCreationResponse = accountApiService.createAdminAccount(accountAdminCreationRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountCreationResponse);
    }

    @GetMapping(value = "/api/account/authenticated")
    public ResponseEntity<AccountSummaryResponse> getAuthenticated(){
        AccountSummaryResponse accountSummaryResponse = accountApiService.getAuthenticated();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountSummaryResponse);
    }

}
