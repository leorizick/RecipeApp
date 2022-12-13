package com.leorizick.recipeapp.services.domain.service.file;

import com.leorizick.recipeapp.entities.file.AccountImg;
import com.leorizick.recipeapp.entities.file.RecipeImg;
import com.leorizick.recipeapp.repositories.file.AccountImgRepository;
import com.leorizick.recipeapp.repositories.file.RecipeImgRepository;
import com.leorizick.recipeapp.services.domain.service.config.auth.AuthenticationContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileManagement {

    private final AuthenticationContext authenticationContext;
    private final AccountImgRepository accountImgRepository;
    private final RecipeImgRepository recipeImgRepository;

    public RecipeImg saveRecipeImg(RecipeImg recipeImg){
        if(recipeImg.isCover()){
            recipeImgRepository.deleteByRecipeAndAccountId(recipeImg.getRecipe().getId());

        }
        return recipeImgRepository.save(recipeImg);
    }

    public RecipeImg getRecipeCover(Long recipeId){
        return recipeImgRepository.getRecipeCoverImg(recipeId);
    }

    public List<RecipeImg> getRecipeImages(Long recipeId){
        return recipeImgRepository.findAllByRecipeId(recipeId);
    }

    public String getAccountImage(Long id) {
        var img = accountImgRepository.getAccountImage(id);
        if(img == null){
            return null;
        }
        return img.getName();
    }
    public AccountImg saveAccountImg(AccountImg accountImg){
        accountImgRepository.deleteByRecipeAndAccountId(accountImg.getAccount().getId());
        return accountImgRepository.save(accountImg);
    }
}
