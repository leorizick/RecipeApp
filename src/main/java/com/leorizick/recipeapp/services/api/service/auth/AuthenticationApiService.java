package com.leorizick.recipeapp.services.api.service.auth;

import com.leorizick.recipeapp.dto.auth.AuthenticationRequest;
import com.leorizick.recipeapp.dto.auth.AuthenticationResponse;
import com.leorizick.recipeapp.services.domain.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationApiService {

    private final AuthenticationService authenticationDomainService;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        return authenticationDomainService.authenticate(authenticationRequest);
    }

}
