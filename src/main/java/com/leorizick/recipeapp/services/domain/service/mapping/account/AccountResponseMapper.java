package com.leorizick.recipeapp.services.domain.service.mapping.account;

import com.leorizick.recipeapp.dto.account.AccountCreationResponse;
import com.leorizick.recipeapp.entities.account.Credential;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class AccountResponseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    public void configure() {
        createFromCredential();
    }

    private void createFromCredential() {
        modelMapper.createTypeMap(Credential.class, AccountCreationResponse.class).setConverter(context -> {
            var src = context.getSource();
            var account = src.getAccount();

            Set<String> profiles = src.getProfiles()
                    .stream()
                    .map(x -> x.getName()).collect(Collectors.toSet());

            return AccountCreationResponse.builder()
                    .id(account.getId())
                    .email(src.getEmail())
                    .name(account.getName())
                    .username(account.getUsername())
                    .birth(account.getBirth())
                    .enabled(account.isEnabled())
                    .profiles(profiles)
                    .build();
        });
    }

}
