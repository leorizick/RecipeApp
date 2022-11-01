package com.leorizick.recipeapp.services.domain.service.auth;

import com.leorizick.recipeapp.dto.auth.AccountContextDetails;
import com.leorizick.recipeapp.dto.auth.AuthenticationRequest;
import com.leorizick.recipeapp.dto.auth.AuthenticationResponse;
import com.leorizick.recipeapp.services.domain.service.config.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        var accountCredentials = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        var authentication = authenticationManager.authenticate(accountCredentials);
        var user = (AccountContextDetails) authentication.getPrincipal();
        var token = jwtAuthenticationFilter.tokenGenerate(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
