package com.leorizick.recipeapp.services.domain.service.mapping.account;


import com.leorizick.recipeapp.dto.account.AccountSummaryResponse;
import com.leorizick.recipeapp.entities.account.Account;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class AccountSummaryResponseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    private void configure() {
        createFromAccount();
    }

    private void createFromAccount() {
        modelMapper.createTypeMap(Account.class, AccountSummaryResponse.class).setConverter(mappingContext -> {
            var src = mappingContext.getSource();

            return AccountSummaryResponse.builder()
                    .id(src.getId())
                    .name(src.getName())
                    .username(src.getUsername())
                    .build();
        });
    }
}
