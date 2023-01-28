package com.leorizick.recipeapp.services.api.service.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Data
@RequiredArgsConstructor
@Builder
@Service
public class EmailSender {

    private final JavaMailSender javaMailSender;

    public void sendWelcomeEmail (String email) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("horadorangoapp@gmail.com", "Hora Do Rango");
        helper.setTo(email);

        String subject = "Seja bem vindo ao Hora do Rango!";
        String body = "<p> Ola! </p>" +
                "<p> Esperamos que goste das nossas receitas </p>" +
                "<p> Bom rango!! </p>";

        helper.setSubject(subject);
        helper.setText(body, true);

        javaMailSender.send(message);

    }

    public void sendCodeEmail (String email, String code) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("horadorangoapp@gmail.com", "Hora Do Rango");
        helper.setTo(email);

        String subject = "Codigo para recuperação de senha!";
        String body =
                "<p> Segue o seu codigo para recuperação de senha </p>" +
                "<p>Atente-se a combinação de letras maiusculas e minusculas!</p>" +
                "<p>O codigo ficara disponivel por apenas 30 minutos!</p>" +
                "<p><font size=\"36\" face=\"Verdana\">" + code + "</font></p>" +
                "<p>Desconsidere este e-mail caso voce saiba sua senha, ou não tenha pedido para recuperar a mesma</p>";

        helper.setSubject(subject);
        helper.setText(body, true);

        javaMailSender.send(message);

    }
}
