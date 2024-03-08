package com.leorizick.recipeapp.services.api.service.account.password;

import com.leorizick.recipeapp.services.api.service.email.EmailSender;
import com.leorizick.recipeapp.services.domain.service.account.AccountCrud;
import com.leorizick.recipeapp.services.domain.service.account.CredentialCrud;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import com.leorizick.recipeapp.services.exceptions.EmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ForgotPasswordApiService {

    private final AccountCrud accountCrud;
    private final CredentialCrud credentialCrud;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationContext authenticationContext;
    @Transactional
    public void sendEmailCode(String email) {
        var credential = credentialCrud.findCredentialByEmail(email);

        var code = codeGenerator();
        credential.setRecoverCode(passwordEncoder.encode(code));
        credentialCrud.save(credential);

        try {
            emailSender.sendCodeEmail(email, code);
        }catch (MessagingException messagingException){
            throw new EmailException();
        }catch (UnsupportedEncodingException unsupportedEncodingException){
            throw new EmailException();
        }
    }

    public String codeGenerator(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 4;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

}
