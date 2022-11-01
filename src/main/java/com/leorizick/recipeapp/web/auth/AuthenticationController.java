package com.leorizick.recipeapp.web.auth;

import com.leorizick.recipeapp.dto.auth.AuthenticationRequest;
import com.leorizick.recipeapp.dto.auth.AuthenticationResponse;
import com.leorizick.recipeapp.services.api.service.auth.AuthenticationApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationApiService authenticationApiService;

    @PostMapping("/api/v1/auth/token")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        var response = authenticationApiService.authenticate(authenticationRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

}
