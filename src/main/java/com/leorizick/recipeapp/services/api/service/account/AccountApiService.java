package com.leorizick.recipeapp.services.api.service.account;

import com.leorizick.recipeapp.dto.account.AccountAdminCreationRequest;
import com.leorizick.recipeapp.dto.account.AccountCreationRequest;
import com.leorizick.recipeapp.dto.account.AccountCreationResponse;
import com.leorizick.recipeapp.dto.account.AccountSummaryResponse;
import com.leorizick.recipeapp.entities.account.Account;
import com.leorizick.recipeapp.entities.account.Credential;
import com.leorizick.recipeapp.services.domain.service.account.AccountCrud;
import com.leorizick.recipeapp.services.domain.service.account.CredentialCrud;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import com.leorizick.recipeapp.services.exceptions.AccountTypeNotAllowed;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountApiService {

    private final AccountCrud accountCrud;
    private final CredentialCrud credentialCrud;
    private final ModelMapper modelMapper;

    private final AuthenticationContext authenticationContext;

    @Transactional
    public AccountCreationResponse createAccount(AccountCreationRequest accountCreationRequest){
        Account account = modelMapper.map(accountCreationRequest, Account.class);
        Account newAccount = accountCrud.save(account);

        Credential credential = modelMapper.map(accountCreationRequest, Credential.class);
        credential.setAccount(newAccount);
        credentialCrud.save(credential);

        AccountCreationResponse accountCreationResponse = modelMapper.map(credential, AccountCreationResponse.class);
        return accountCreationResponse;
    }

    @Transactional
    public AccountCreationResponse createAdminAccount(AccountAdminCreationRequest accountAdminCreationRequest){
        Account account = modelMapper.map(accountAdminCreationRequest, Account.class);
        Account newAccount = accountCrud.save(account);

        Credential credential = modelMapper.map(accountAdminCreationRequest, Credential.class);
        credential.setAccount(newAccount);
        credentialCrud.save(credential);

        AccountCreationResponse accountCreationResponse = modelMapper.map(credential, AccountCreationResponse.class);
        return accountCreationResponse;
    }

    public AccountSummaryResponse getAuthenticated() {
        Account account = authenticationContext.getAccount();

        return modelMapper.map(account, AccountSummaryResponse.class);
    }
}
