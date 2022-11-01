package com.leorizick.recipeapp.services.domain.service.config.auth;

import com.leorizick.recipeapp.dto.auth.AccountContextDetails;
import com.leorizick.recipeapp.entities.account.Account;
import com.leorizick.recipeapp.repositories.AccountRepository;
import com.leorizick.recipeapp.services.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationContext {
    private static final String ACCOUNT_NOT_FOUND_MSG = "Account %s not found";

    private final AccountRepository accountRepository;

    public boolean isAuthenticated() {
        var authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        return authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

    public Long getAccountId() {
        return getAuthenticated().getId();
    }

    public AccountContextDetails getAuthenticated() {
        return (AccountContextDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public Account getAccount() {
        return accountRepository.findById(getAccountId())
                .orElseThrow(() -> new NotFoundException(String.format(ACCOUNT_NOT_FOUND_MSG, getAccountId())));
    }

}
