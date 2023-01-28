package com.leorizick.recipeapp.services.api.service.account.password;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.leorizick.recipeapp.dto.account.password.PasswordChangeRequest;
import com.leorizick.recipeapp.services.api.service.email.EmailSender;
import com.leorizick.recipeapp.services.domain.service.account.AccountCrud;
import com.leorizick.recipeapp.services.domain.service.account.CredentialCrud;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import com.leorizick.recipeapp.services.domain.service.properties.JwtProperties;
import com.leorizick.recipeapp.services.exceptions.AccountTypeNotAllowed;
import com.leorizick.recipeapp.services.exceptions.EmailException;
import com.leorizick.recipeapp.services.exceptions.NotFoundException;
import com.leorizick.recipeapp.services.exceptions.PasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ChangePasswordApiService {

    private final AccountCrud accountCrud;
    private final CredentialCrud credentialCrud;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationContext authenticationContext;
    private final JwtProperties jwtProperties;

    @Transactional
    public void changePasswordWithRecoveryCode(PasswordChangeRequest passwordChangeRequest) {
        var credential = credentialCrud.findCredentialByEmail(passwordChangeRequest.getEmail());

        if(!passwordEncoder.matches(passwordChangeRequest.getRecoveryCode(), credential.getRecoverCode())) {
            throw new PasswordException("Codigo invalido", "Codigo de recuperação não consta para o e-mail informado");
        }

        if(ChronoUnit.MINUTES.between(credential.getUpdatedAt(), LocalDateTime.now()) > 30){
            throw new PasswordException("Codigo expirado", "O codigo de recuperação esta expirado");
        }
        else{
            credential.setRecoverCode(null);
            credential.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
            credentialCrud.save(credential);
        }
    }

    @Transactional
    public void changePasswordWithCurrentPassword(PasswordChangeRequest passwordChangeRequest, Long id) {
        if(!id.equals(authenticationContext.getAccountId())){
            throw new AccountTypeNotAllowed("Somente o usuario pode alterar sua senha!");
        }
        var account = accountCrud.findById(id);
        var credential = account.getCredentials().get(0);

        if(passwordEncoder.matches(passwordChangeRequest.getCurrentPassword(), credential.getPassword())) {
            credential.setRecoverCode(null);
            credential.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
            credentialCrud.save(credential);
        }
        else{
            throw new PasswordException("Senha invalida", "Para recuperação de senha, faça logout e procure a aba Esqueci minha senha");
        }
    }

}
