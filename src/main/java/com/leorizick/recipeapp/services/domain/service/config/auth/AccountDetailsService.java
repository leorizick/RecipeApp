package com.leorizick.recipeapp.services.domain.service.config.auth;

import com.leorizick.recipeapp.dto.auth.AccountContextDetails;
import com.leorizick.recipeapp.entities.account.Profile;
import com.leorizick.recipeapp.repositories.CredentialRepository;
import com.leorizick.recipeapp.services.domain.service.account.RoleFinderByProfile;
import com.leorizick.recipeapp.services.domain.service.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {

    private final CredentialRepository credentialRepository;
    private final JwtProperties jwtProperties;

    private final RoleFinderByProfile roleFinderByProfile;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var credential = credentialRepository.findByEmailOrAccountUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        var profiles = credential.getProfiles().stream()
                .filter(Profile::isEnabled)
                .map(Profile::getName)
                .collect(Collectors.toSet());

        var roles = roleFinderByProfile.findAllDistinctByProfileList(profiles);

        var issuedAt = LocalDateTime.now();
        return AccountContextDetails.builder()
                .id(credential.getAccount().getId())
                .credentialId(credential.getId())
                .username(credential.getAccount().getUsername())
                .email(credential.getEmail())
                .password(credential.getPassword())
                .profiles(profiles)
                .roles(roles)
                .accountEnabled(credential.getAccount().isEnabled())
                .credentialEnabled(credential.isEnabled())
                .issuedAt(issuedAt)
                .expireAt(issuedAt.plusSeconds(jwtProperties.getExpirationTime()))
                .build();
    }
}
