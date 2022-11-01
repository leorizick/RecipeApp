package com.leorizick.recipeapp.services.domain.service.mapping.account;


import com.leorizick.recipeapp.dto.account.AccountAdminCreationRequest;
import com.leorizick.recipeapp.dto.account.AccountCreationRequest;
import com.leorizick.recipeapp.entities.account.Account;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class AccountMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void configure() {
        createFromAccountCreationRequest();
        createFromAccountAdminRequest();
    }

    private void createFromAccountCreationRequest() {
        modelMapper.createTypeMap(AccountCreationRequest.class, Account.class).setConverter(context -> {
            var src = context.getSource();
            return createFromCommonAccountCreationRequestDTO(src);
        });
    }

    private void createFromAccountAdminRequest() {
        modelMapper.createTypeMap(AccountAdminCreationRequest.class, Account.class).setConverter(context -> {
            var src = context.getSource();
            return createFromCommonAccountCreationRequestDTO(src);
        });
    }

    private Account createFromCommonAccountCreationRequestDTO(AccountCreationRequest account) {

        return Account.builder()
                .name(account.getName())
                .username(account.getUsername())
                .birth(account.getBirth())
                .enabled(true)
                .build();
    }
}
