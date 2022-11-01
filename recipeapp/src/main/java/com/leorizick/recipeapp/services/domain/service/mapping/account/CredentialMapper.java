package com.leorizick.recipeapp.services.domain.service.mapping.account;

import com.leorizick.recipeapp.dto.account.AccountAdminCreationRequest;
import com.leorizick.recipeapp.dto.account.AccountCreationRequest;
import com.leorizick.recipeapp.dto.profile.ProfileName;
import com.leorizick.recipeapp.entities.account.Credential;
import com.leorizick.recipeapp.entities.account.Profile;
import com.leorizick.recipeapp.services.domain.service.account.ProfileFinderByName;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class CredentialMapper {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ProfileFinderByName profileFinderByName;

    @PostConstruct
    public void configure() {
        createFromAccountCreationRequest();
        createFromAccountAdminCreationRequest();
    }

    private void createFromAccountCreationRequest() {
        modelMapper.createTypeMap(AccountCreationRequest.class, Credential.class).setConverter(context -> {
            var src = context.getSource();
            return createFromCommonsAccountCreationDTO(src);
        });
    }

    private void createFromAccountAdminCreationRequest() {
        modelMapper.createTypeMap(AccountAdminCreationRequest.class, Credential.class).setConverter(context -> {
            var src = context.getSource();
            var commonAccount = createFromCommonsAccountCreationDTO(src);
            commonAccount.getProfiles().add(profileFinderByName.findByName(ProfileName.ADMIN));

            return commonAccount;
        });
    }

    private Credential createFromCommonsAccountCreationDTO(AccountCreationRequest account){
        Set<Profile> profiles = new HashSet<>();
        Profile profile = profileFinderByName.findByName(ProfileName.USER);
        profiles.add(profile);

        return Credential.builder()
                .email(account.getEmail())
                .password(passwordEncoder.encode(account.getPassword()))
                .enabled(true)
                .profiles(profiles)
                .build();
    }

}
