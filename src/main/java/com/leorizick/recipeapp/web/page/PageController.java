package com.leorizick.recipeapp.web.page;

import com.leorizick.recipeapp.dto.recipe.CommentCrudResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequiredArgsConstructor
public class PageController {
    @GetMapping(value = "/api/page/privacy-policy")
    public String showPrivacyPolicy() {
        return "POLITICA DE PRIVACIDADE";
    }

    @GetMapping(value = "/api/page/delete-account")
    public String showDeleteAccount() {
        return "ExcluirConta";
    }
}
