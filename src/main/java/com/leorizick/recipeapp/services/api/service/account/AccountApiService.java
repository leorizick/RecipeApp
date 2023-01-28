package com.leorizick.recipeapp.services.api.service.account;

import com.leorizick.recipeapp.dto.account.AccountAdminCreationRequest;
import com.leorizick.recipeapp.dto.account.AccountCreationRequest;
import com.leorizick.recipeapp.dto.account.AccountCreationResponse;
import com.leorizick.recipeapp.dto.account.AccountSummaryResponse;
import com.leorizick.recipeapp.entities.account.Account;
import com.leorizick.recipeapp.entities.account.Credential;
import com.leorizick.recipeapp.services.api.service.email.EmailSender;
import com.leorizick.recipeapp.services.domain.service.account.AccountCrud;
import com.leorizick.recipeapp.services.domain.service.account.CredentialCrud;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import com.leorizick.recipeapp.services.exceptions.AccountTypeNotAllowed;
import com.leorizick.recipeapp.services.exceptions.EmailException;
import com.leorizick.recipeapp.services.exceptions.RestErrorDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountApiService {

    private final AccountCrud accountCrud;
    private final CredentialCrud credentialCrud;
    private final ModelMapper modelMapper;
    @Autowired
    private EmailSender emailSender;

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
    public AccountCreationResponse updateAccount(Long id, AccountCreationRequest accountCreationRequest){
        if(!Objects.equals(id, authenticationContext.getAccountId())) {
            throw new AccountTypeNotAllowed("Only account owner can do this");
        }
            Account account = accountCrud.findById(id);
            account.setName(accountCreationRequest.getName());
            account.setUsername(accountCreationRequest.getUsername());
            account.setBirth(accountCreationRequest.getBirth());
            accountCrud.save(account);


            Credential credential = account.getCredentials().get(account.getCredentials().size() -1);
            credential.setEmail(accountCreationRequest.getEmail());
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

    public Page<AccountSummaryResponse> findAllAccountsByUsername(String username, Pageable pageable) {
        Page<Account> listAccount = accountCrud.findAllAccountsByUsername(username, pageable);
        return  listAccount.map(account -> modelMapper.map(account, AccountSummaryResponse.class));

    }
}
